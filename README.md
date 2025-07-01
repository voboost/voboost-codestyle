# Voboost Code Style Configuration

This repository contains unified code formatting rules for all Voboost projects.

## Contents

- `.editorconfig` - Core formatting rules for ktlint and IDE
- `versions.properties` - Tool versions for consistency
- `integration-example.gradle.kts` - Example integration configuration
- `memory-bank/` - Complete project documentation
- `ktlint_rules/` - Directory for custom rules (future use)

## Supported Technologies

- **Kotlin** (.kt, .kts files)
- **Android XML** (layouts, manifests)
- **Gradle** (.gradle, .gradle.kts)
- **YAML/JSON** configuration files
- **Markdown** documentation

## Core Rules

- **Indentation**: 4 spaces for Kotlin/Java/XML, 2 spaces for YAML/JSON/Markdown
- **Line length**: maximum 120 characters
- **Line endings**: LF (Unix-style)
- **Encoding**: UTF-8
- **Trailing comma**: disabled (conservative approach)

## Project Integration

### 1. Create symlink to central configuration

First, create a symbolic link to the central `.editorconfig` file in your project root:

```bash
# From your project directory
ln -s ../voboost-codestyle/.editorconfig ./.editorconfig
```

### 2. Adding ktlint to build.gradle.kts (root)

```kotlin
plugins {
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
}

ktlint {
    version.set("1.0.1")

    // Create symlink to central .editorconfig (ktlint looks for it in project root)
    // ln -s ../voboost-codestyle/.editorconfig ./.editorconfig

    // Additional settings
    android.set(true)
    ignoreFailures.set(false)
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
}
```

### 2. Apply to all modules

For multi-module projects, add to root `build.gradle.kts`:

```kotlin
subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("1.0.1")
        android.set(true)
        ignoreFailures.set(false)
    }
}
```

### 3. Available Gradle tasks

After integration, the following tasks become available:

```bash
# Check code style compliance
./gradlew ktlintCheck

# Automatic code formatting
./gradlew ktlintFormat

# Check specific module
./gradlew :app:ktlintCheck
./gradlew :library:ktlintFormat
```

## IDE Setup

### Android Studio / IntelliJ IDEA

1. Open **Settings** → **Editor** → **Code Style**
2. Click the gear icon next to **Scheme** → **Import Scheme** → **EditorConfig**
3. Select the `.editorconfig` file from this repository
4. Apply the settings

### VS Code

Install the **EditorConfig for VS Code** extension. Settings will be applied automatically when `.editorconfig` file is present in the project.

## CI/CD Integration

### GitHub Actions

```yaml
name: Code Style Check

on: [push, pull_request]

jobs:
  ktlint:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v4
    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'
    - name: Run ktlint
      run: ./gradlew ktlintCheck
```

## Projects using this configuration

- [voboost](../voboost)
- [voboost-config](../voboost-config)
- [voboost-config-demo](../voboost-config-demo)

## Updating rules

When rules need to be changed:

1. Update `.editorconfig` in this repository
2. Test changes on one of the projects
3. Run `ktlintFormat` in all projects to apply new rules
4. Commit the changes

## Useful links

- [ktlint Documentation](https://pinterest.github.io/ktlint/)
- [EditorConfig Specification](https://editorconfig.org/)
- [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- [Android Code Style Guide](https://developer.android.com/kotlin/style-guide)

