@file:Suppress("PropertyName", "SpellCheckingInspection")


val kotlin_coroutines_version: String by project
val kotlin_coroutines_javafx_version: String by project
val tornadofx_version: String by project

val kluent_version: String by project
val junit_version: String by project
val mockk_version: String by project
val kotlin_coroutines_test_version: String by project

plugins {
    kotlin("jvm") version "1.3.71"
    idea
    eclipse
    java
    application
    id("org.openjfx.javafxplugin") version "0.0.8"
}

javafx {
    version = "14.0.1"
    modules("javafx.controls", "javafx.fxml")
}

application {
    mainClassName = "ProgramKt"
    applicationDefaultJvmArgs = listOf("-Dkotlinx.coroutines.debug")
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutines_version")
    implementation( "org.jetbrains.kotlinx:kotlinx-coroutines-javafx:$kotlin_coroutines_javafx_version")
    implementation( "no.tornado:tornadofx:$tornadofx_version")

    testImplementation("org.junit.jupiter:junit-jupiter:$junit_version")
    testImplementation("org.amshove.kluent:kluent:$kluent_version")
    testImplementation("io.mockk:mockk:$mockk_version")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlin_coroutines_test_version")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events( "passed", "skipped", "failed")
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}