package br.com.ams.amsmarmitakm.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.config.tryGetString
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Base64

fun Application.configureSecurity() {
    val jwtSecret = environment.config.tryGetString("jwt.secret")
    val jwtAudience = environment.config.tryGetString("jwt.audience")
    val jwtIsuer = environment.config.tryGetString("jwt.isuer")
    val jwtRealm = environment.config.tryGetString("jwt.realm")

    val passwordDefault = environment.config.tryGetString("PASSWORD")!!

    install(Authentication) {
        jwt {
            realm = jwtRealm!!
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIsuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
    routing {
        post("auth/login") {
            val pass = call.parameters["password"] ?: return@post call.respondText(
                text = "Missing password",
                status = HttpStatusCode.BadRequest
            )

            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(
                passwordDefault.toByteArray(StandardCharsets.UTF_8)
            )
            val passsh256 = Base64.getEncoder().encodeToString(hash)

            if (pass != passsh256) {
                return@post call.respondText(
                    text = "Password Unauthorized",
                    status = HttpStatusCode.Unauthorized
                )
            }

            val token = JWT.create()
                .withAudience(jwtAudience)
                .withIssuer(jwtIsuer)
                .withExpiresAt(Instant.now().plus(1L, ChronoUnit.DAYS))
                .sign(Algorithm.HMAC256(jwtSecret))

            call.respond(hashMapOf("access_token" to token))
        }
    }
}