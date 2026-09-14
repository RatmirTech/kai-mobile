plugins {
    kotlin("jvm") version "2.0.20"
    application
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

sourceSets {
    main {
        kotlin.srcDirs("Unit1", "src/main/kotlin")
    }
}

application {
    mainClass.set(project.findProperty("mainClass") as String? ?: "MainKt")
}

tasks.named<JavaExec>("run") {
    mainClass.set(project.findProperty("mainClass") as String? ?: "MainKt")
}
