import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
}

group = "fr.ayfri"
version = "1.0"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    implementation("com.serebit.strife", "strife-client-jvm", "0.4.0")
    implementation("com.serebit.logkat", "logkat", "0.6.0")
    implementation(kotlin("reflect"))
    implementation("commons-io", "commons-io", "2.8.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "14"
}

application.mainClass.set("MainKt")
