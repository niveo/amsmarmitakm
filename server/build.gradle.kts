plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.serialization)
    application
}

group = "br.com.ams.amsmarmitakm"
version = "1.0.0"
application {
    mainClass.set("br.com.ams.amsmarmitakm.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

val mongo_version: String = "4.9.0"
val ktor_version  = "2.3.11"
val koin_version = "3.5.6"

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)

    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation(libs.kotlin.test)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)

    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")

    implementation("io.ktor:ktor-server-request-validation:$ktor_version")


    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-serialization-jackson-jvm")

    implementation ("io.insert-koin:koin-ktor:$koin_version")
    implementation ("io.insert-koin:koin-logger-slf4j:$koin_version")

    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.10.1")

    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-config-yaml")
}