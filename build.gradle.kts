plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.jar {
    archiveBaseName.set("git-change-finder")
    archiveVersion.set("1.0.0")
}

tasks.shadowJar {
    archiveBaseName.set("git-change-finder-fat")
    archiveVersion.set("1.0.0")
    archiveClassifier.set("")
    mergeServiceFiles()
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}