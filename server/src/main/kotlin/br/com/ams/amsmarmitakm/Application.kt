package br.com.ams.amsmarmitakm

import br.com.ams.amsmarmitakm.di.comedorModule
import br.com.ams.amsmarmitakm.plugins.configureHTTP
import br.com.ams.amsmarmitakm.plugins.configureRouting
import br.com.ams.amsmarmitakm.plugins.configureSecurity
import br.com.ams.amsmarmitakm.plugins.configureSerialization
import br.com.ams.amsmarmitakm.plugins.mongoConfigModule
import br.com.ams.amsmarmitakm.routes.comedorController
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.authenticate
import io.ktor.server.netty.EngineMain
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

//embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
//.start(wait = true)
fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureSecurity()

    install(Koin) {
        slf4jLogger()
        modules(
            mongoConfigModule(environment),
            comedorModule
        )
    }

    configureSerialization()
    configureHTTP()

    configureRouting()
    comedorController()

}