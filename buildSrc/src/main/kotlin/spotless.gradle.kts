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

    yaml {
        target("**/*.yaml")
        trimTrailingWhitespace()
        endWithNewline()
        toggleOffOn()
    }

    kotlin {
        target("**/*.kt")
        ktlint()
        trimTrailingWhitespace()
        endWithNewline()
        toggleOffOn()
    }

    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint()
        trimTrailingWhitespace()
        endWithNewline()
        toggleOffOn()
    }

    gherkin {
        target("**/*.feature")
        gherkinUtils()
        trimTrailingWhitespace()
        endWithNewline()
        toggleOffOn()
    }

    toml {
        target("**/*.toml")
        versionCatalog()
            .stripQuotedKeys(true)
        trimTrailingWhitespace()
        endWithNewline()
        toggleOffOn()
    }

    json {
        target("**/*.json")
        targetExclude("**/.devcontainer/devcontainer.json")
        gson()
            .indentWithSpaces(4)
            .sortByKeys()
            .escapeHtml()
        trimTrailingWhitespace()
        endWithNewline()
        toggleOffOn()
    }
}