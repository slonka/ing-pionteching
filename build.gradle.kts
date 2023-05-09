plugins {
    application
    id("java")
    id("org.graalvm.buildtools.native") version "0.9.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("net.slonka.greencode.Main")
}

graalvmNative {
    binaries.all {
        resources.autodetect()
    }
    toolchainDetection.set(false)
}

tasks.test {
    useJUnitPlatform()
}