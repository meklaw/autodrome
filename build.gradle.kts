
plugins {
    java
    `maven-publish`
    id("io.freefair.lombok") version "6.6.2"
    id("org.springframework.boot") version "3.0.1"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.telegram:telegrambots:5.6.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-security:3.0.1")
    implementation("org.modelmapper:modelmapper:3.1.1")
    implementation("org.projectlombok:lombok:1.18.24")
    implementation("com.auth0:java-jwt:4.2.1")
    implementation("org.jetbrains:annotations:13.0")
    runtimeOnly("org.postgresql:postgresql:42.5.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.1")
}

group = "ru.meklaw"
version = "0.0.1-SNAPSHOT"
description = "autodrome"
java.sourceCompatibility = JavaVersion.VERSION_17

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
