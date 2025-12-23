// Example integration in build.gradle.kts (root project file)
// This example shows how to integrate voboost-codestyle for Kotlin and Java support

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("com.diffplug.spotless") version "6.22.0"
    id("checkstyle")
}

// Apply voboost-codestyle configuration to all subprojects
allprojects {
    // Apply plugins
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "checkstyle")

    // Apply centralized configuration scripts
    apply(from = "../voboost-codestyle/codestyle.gradle")

    // Alternative: Apply individual scripts for more control
    // apply(from = "../voboost-codestyle/checkstyle.gradle")  // Java style validation
    // apply(from = "../voboost-codestyle/spotless.gradle")    // Java formatting
}

// Manual configuration example (if not using centralized scripts)
subprojects {
    // Load versions from voboost-codestyle
    val codeStyleVersions = java.util.Properties().apply {
        file("../voboost-codestyle/versions.properties").inputStream().use { load(it) }
    }

    // Configure ktlint for Kotlin
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set(codeStyleVersions.getProperty("ktlint.version"))

        // Create symlink to central .editorconfig (ktlint looks for it in project root)
        // ln -s ../voboost-codestyle/.editorconfig ./.editorconfig

        // Android project settings
        android.set(true)

        // Do not ignore formatting errors
        ignoreFailures.set(false)

        // Reporter configuration
        reporters {
            reporter(
                org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN
            )
            reporter(
                org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE
            )
        }

        // File filters for exclusions
        filter {
            exclude("**/generated/**")
            exclude("**/build/**")
        }
    }

    // Configure Checkstyle for Java
    configure<CheckstyleExtension> {
        toolVersion = codeStyleVersions.getProperty("checkstyle.version")
        configFile = file("../voboost-codestyle/checkstyle.xml")
        isIgnoreFailures = false
        isShowViolations = true
    }

    // Configure Spotless for Java formatting
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        java {
            target("src/**/*.java")
            targetExclude("**/generated/**", "**/build/**")

            googleJavaFormat(
                codeStyleVersions.getProperty("google.java.format.version")
            ).aosp().reflowLongStrings().skipJavadocFormatting()

            removeUnusedImports()
            importOrder("java", "javax", "android", "androidx", "com", "org", "")
            trimTrailingWhitespace()
            endWithNewline()
        }

        kotlin {
            target("src/**/*.kt")
            targetExclude("**/generated/**", "**/build/**")

            ktlint(codeStyleVersions.getProperty("ktlint.version"))
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}

// Convenience tasks for the root project
tasks.register("formatAllCode") {
    group = "formatting"
    description = "Formats all code (Kotlin and Java) in all subprojects"
    dependsOn(subprojects.map { "${it.path}:formatCode" })
}

tasks.register("checkAllCodeStyle") {
    group = "verification"
    description = "Checks all code style (Kotlin and Java) in all subprojects"
    dependsOn(subprojects.map { "${it.path}:checkCodeStyle" })
}

// Legacy tasks for backward compatibility
tasks.register("formatCode") {
    group = "formatting"
    description = "Formats all code using ktlint and Spotless"
    dependsOn("formatAllCode")
}

tasks.register("checkCodeStyle") {
    group = "verification"
    description = "Checks code style using ktlint and Checkstyle"
    dependsOn("checkAllCodeStyle")
}

// Integration with check task
tasks.named("check") {
    dependsOn("checkAllCodeStyle")
}

