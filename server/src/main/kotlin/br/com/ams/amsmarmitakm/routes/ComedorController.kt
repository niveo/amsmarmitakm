package br.com.ams.amsmarmitakm.routes

import br.com.ams.amsmarmitakm.domain.entity.Comedor
import br.com.ams.amsmarmitakm.request.ComedorRequest
import br.com.ams.amsmarmitakm.request.toDomain
import br.com.ams.amsmarmitakm.services.ComedorService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.authenticate
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.comedorController() {

    val comedorService by inject<ComedorService>()

    install(RequestValidation) {
        validate<Comedor> { comedor ->
            if (comedor.nome.length < 5)
                ValidationResult.Invalid("A comedor nome should be greater than 5")
            if (comedor.nome.length > 25)
                ValidationResult.Invalid("A comedor nome should be less than 25")
            if (comedor.foto != null && comedor.foto!!.length > 25)
                ValidationResult.Invalid("A comedor foto should be less than 25")
            else ValidationResult.Valid
        }
    }

    routing {
        authenticate {
            post("/comedores") {
                val request = call.receive<ComedorRequest>()
                val id = comedorService.create(request.toDomain())!!
                call.respond(HttpStatusCode.Created, id)
            }
            get("/comedores/{id}") {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
                comedorService.read(id)?.let { comedor ->
                    call.respond(comedor.toResponse())
                } ?: call.respond(HttpStatusCode.NotFound)
            }
            get("/comedores") {
                comedorService.readAll()?.map { it.toResponse() }?.let { comedor ->
                    call.respond(comedor)
                } ?: call.respond(HttpStatusCode.NotFound)
            }
            put("/comedores/{id}") {
                val id = call.parameters["id"] ?: return@put call.respondText(
                    text = "Missing comedor id",
                    status = HttpStatusCode.BadRequest
                )
                val request = call.receive<ComedorRequest>()
                val update = comedorService.update(id, request.toDomain())
                call.respondText(
                    text = if (update == 1L) "Comedor update successfully" else "Comedor not found",
                    status = if (update == 1L) HttpStatusCode.OK else HttpStatusCode.NotFound
                )
            }
            delete("/comedores/{id}") {
                val id = call.parameters["id"] ?: return@delete call.respondText(
                    text = "Missing comedor id",
                    status = HttpStatusCode.BadRequest
                )
                val delete = comedorService.delete(id)
                call.respondText(
                    text = if (delete == 1L) "Comedor delete successfully" else "Comedor not found",
                    status = if (delete == 1L) HttpStatusCode.OK else HttpStatusCode.NotFound
                )
            }
        }
    }
}