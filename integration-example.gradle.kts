// Example ktlint integration in build.gradle.kts (root project file)

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
}

// Apply ktlint to all subprojects
allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("1.0.1")

        // Create symlink to central .editorconfig (ktlint looks for it in project root)
        // ln -s ../voboost-codestyle/.editorconfig ./.editorconfig

        // Android project settings
        android.set(true)

        // Do not ignore formatting errors
        ignoreFailures.set(false)

        // Reporter configuration
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        }

        // File filters for exclusions (if needed)
        filter {
            exclude("**/generated/**")
            exclude("**/build/**")
        }
    }
}

// Additional convenience tasks
tasks.register("formatCode") {
    group = "formatting"
    description = "Formats all Kotlin code using ktlint"
    dependsOn("ktlintFormat")
}

tasks.register("checkCodeStyle") {
    group = "verification"
    description = "Checks code style using ktlint"
    dependsOn("ktlintCheck")
}

// Integration with check task
tasks.named("check") {
    dependsOn("ktlintCheck")
}