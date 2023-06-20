plugins {
    application
    id("java")
    id("com.github.jk1.dependency-license-report") version "2.0"
}

group = "net.slonka.greencode"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.3"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    implementation("io.netty:netty-codec-http:4.1.92.Final")
    implementation("io.netty:netty-transport-native-epoll:4.1.92.Final")
    implementation("io.netty:netty-transport-native-kqueue:4.1.94.Final")
    implementation("io.netty.incubator:netty-incubator-transport-native-io_uring:0.0.21.Final")
    implementation("com.alibaba:fastjson:2.0.31")
}

application {
    mainClass.set("net.slonka.greencode.WebServer")
}

tasks.jar {
    manifest.attributes["Main-Class"] = application.mainClass
    val dependencies = configurations
            .runtimeClasspath
            .get()
            .map(::zipTree)
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<JavaExec> {
    systemProperty("fastjson.parser.safeMode", "true")
}

tasks.test {
    useJUnitPlatform()
}