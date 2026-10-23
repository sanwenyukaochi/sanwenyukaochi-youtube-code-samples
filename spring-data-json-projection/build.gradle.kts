import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.spotless.java.GoogleJavaFormatStep

plugins {
    id ("java")
    id ("org.springframework.boot") version "4.1.1"
    id ("io.spring.dependency-management") version "1.1.7"
    id ("com.diffplug.spotless") version "8.10.4"
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

tasks.named<Test>("test") {
    useJUnitPlatform()
}

pluginManager.withPlugin("com.diffplug.spotless") {
    extensions.configure<SpotlessExtension> {
        encoding("UTF-8")

        java {
            target("**/*.java")
            forbidWildcardImports()
            forbidModuleImports()
            googleJavaFormat(GoogleJavaFormatStep.defaultVersion())
                .aosp()
                .reflowLongStrings(false)
                .formatJavadoc(true)
                .reorderImports(true)
            importOrder()
            removeUnusedImports()
            formatAnnotations()
            trimTrailingWhitespace()
            endWithNewline()
            toggleOffOn()
        }
    }
}