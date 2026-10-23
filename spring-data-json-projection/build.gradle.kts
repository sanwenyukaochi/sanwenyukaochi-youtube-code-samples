plugins {
    id ("java")
    id ("org.springframework.boot") version "4.1.1"
    id ("io.spring.dependency-management") version "1.1.7"
}

group = "io.github.sanwenyukaochi"
version = "0.0.1-SNAPSHOT"
description = "spring-data-json-projection"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(27)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.springframework.boot:spring-boot-starter")
    implementation ("org.springframework.boot:spring-boot-starter-webmvc")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly ("org.junit.platform:junit-platform-launcher")
    implementation ("org.springframework.data:spring-data-commons")
    implementation ("com.jayway.jsonpath:json-path")
}

tasks.named("test") {
    useJUnitPlatform()
}
