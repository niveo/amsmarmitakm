package br.com.ams.amsmarmitakm.plugins

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.ktor.server.application.ApplicationEnvironment
import io.ktor.server.application.ApplicationStopped
import io.ktor.server.config.tryGetString
import org.koin.dsl.module

fun mongoConfigModule(environment: ApplicationEnvironment) = module {
    single<MongoDatabase> {
        getDatabase(environment)
    }
}

private fun getDatabase(environment: ApplicationEnvironment): MongoDatabase {
    System.out.println("getDatabase")
    val user = environment.config.tryGetString("db.mongo.user")
    val password = environment.config.tryGetString("db.mongo.password")
    val host = environment.config.tryGetString("db.mongo.host") ?: "127.0.0.1"
    val port = environment.config.tryGetString("db.mongo.port") ?: "27017"
    val maxPoolSize = environment.config.tryGetString("db.mongo.maxPoolSize")?.toInt() ?: 20
    val databaseName = environment.config.tryGetString("db.mongo.database.name") ?: "myDatabase"

    val credentials =
        user?.let { userVal -> password?.let { passwordVal -> "$userVal:$passwordVal@" } }.orEmpty()
    val uri =
        "mongodb://$credentials$host:$port/marmitadb?maxPoolSize=$maxPoolSize&w=majority&replicaSet=rs0"

    val mongoClient = MongoClient.create(uri)
    val database = mongoClient.getDatabase(databaseName)

    environment.monitor.subscribe(ApplicationStopped) {
        mongoClient.close()
    }

    return database
}