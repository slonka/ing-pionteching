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
    implementation("org.javassist:javassist:3.29.2-GA")
    implementation("com.alibaba:fastjson:2.0.28")
}

application {
    mainClass.set("net.slonka.greencode.WebServer")
}

tasks.jar {
    manifest.attributes["Main-Class"] = application.mainClass
    val dependencies = configurations
            .runtimeClasspath
            .get()
            .map(::zipTree) // OR .map { zipTree(it) }
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.test {
    useJUnitPlatform()
}