val logback_version: String by project
val ktor_version: String by project
val kotlin_version: String by project
val guice_version: String by project

plugins {
    application
    kotlin("jvm") version "1.3.21"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

group = "ktor-todo"
version = "0.0.1-SNAPSHOT"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Main-Class" to application.mainClassName
            )
        )
    }
}

repositories {
    mavenLocal()
    jcenter()
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("ch.qos.logback:logback-classic:$logback_version")
    compile("io.ktor:ktor-gson:$ktor_version")
    compile("com.google.inject:guice:$guice_version")
    compile("com.datastax.oss:java-driver-core:4.0.1")
    compile("com.datastax.oss:java-driver-query-builder:4.0.1")
    testCompile("io.ktor:ktor-server-tests:$ktor_version")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}