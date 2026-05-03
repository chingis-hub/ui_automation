plugins {
    kotlin("jvm") version "2.3.0"
}

repositories {
    mavenCentral()
    // repo gives:
    // basic IntelliJ Platform libraries, APIs for interacting with the IDE, and driver/starter dependencies
    maven(url = "https://cache-redirector.jetbrains.com/intellij-dependencies")
    // stable IntelliJ versions
    maven(url = "https://www.jetbrains.com/intellij-repository/releases")
    // latest versions
    maven(url = "https://www.jetbrains.com/intellij-repository/snapshots")
    // libraries from the TeamCity ecosystem
    maven(url = "https://download.jetbrains.com/teamcity-repository")
    // libraries from the Grazie Platform (AI suggestions, text analysis)
    maven(url = "https://cache-redirector.jetbrains.com/packages.jetbrains.team/maven/p/grazi/grazie-platform-public")
}

kotlin {
    jvmToolchain(25)
}

dependencies {
    // IDE Starter / Driver stack
    testImplementation("com.jetbrains.intellij.tools:ide-starter-squashed:LATEST-EAP-SNAPSHOT")
    testImplementation("com.jetbrains.intellij.tools:ide-starter-junit5:LATEST-EAP-SNAPSHOT")
    testImplementation("com.jetbrains.intellij.tools:ide-metrics-collector:LATEST-EAP-SNAPSHOT")
    testImplementation("com.jetbrains.intellij.tools:ide-metrics-collector-starter:LATEST-EAP-SNAPSHOT")
    testImplementation("com.jetbrains.intellij.tools:ide-performance-testing-commands:LATEST-EAP-SNAPSHOT")
    testImplementation("com.jetbrains.intellij.tools:ide-starter-driver:LATEST-EAP-SNAPSHOT")
    testImplementation("com.jetbrains.intellij.driver:driver-client:LATEST-EAP-SNAPSHOT")
    testImplementation("com.jetbrains.intellij.driver:driver-sdk:LATEST-EAP-SNAPSHOT")
    testImplementation("com.jetbrains.intellij.driver:driver-model:LATEST-EAP-SNAPSHOT")

    // JUnit 5 via BOM — version alignment for libraries
    val junitBom = platform("org.junit:junit-bom:5.12.2")
    testImplementation(junitBom)
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Other utilities used by tests
    testImplementation("org.kodein.di:kodein-di-jvm:7.20.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.10.1")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")

    // Libraries for validation and working with FUS (Feature Usage Statistics) events
    testImplementation("com.jetbrains.fus.reporting:ap-validation:76")
    testImplementation("com.jetbrains.fus.reporting:model:76")
}

tasks.test {
    // Enable JUnit 5 platform so JUnit Jupiter tests are discovered and executed
    useJUnitPlatform()

    testLogging {
        events("passed", "skipped", "failed", "standardOut", "standardError")
    }
}