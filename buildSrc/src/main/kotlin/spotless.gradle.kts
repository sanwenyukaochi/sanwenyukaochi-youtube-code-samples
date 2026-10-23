import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    id("com.diffplug.spotless")
}

configure<SpotlessExtension> {
    encoding("UTF-8")
    java {
        target("**/*.java")
        forbidWildcardImports()
        forbidModuleImports()
        googleJavaFormat()
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