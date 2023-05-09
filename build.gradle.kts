plugins {
    application
    id("java")
}

group = "net.slonka.greencode"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("io.netty:netty-codec-http:4.1.92.Final")
    implementation("io.netty:netty-transport-native-epoll:4.1.92.Final")
    implementation("io.netty:netty-transport-native-kqueue:4.1.92.Final")
    implementation("io.netty.incubator:netty-incubator-transport-native-io_uring:0.0.21.Final")
    implementation("com.dslplatform:dsl-json:1.10.0")
    implementation("org.javassist:javassist:3.29.2-GA")
}

application {
    mainClass.set("net.slonka.greencode.Main")
}

tasks.test {
    useJUnitPlatform()
}