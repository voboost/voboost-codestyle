# Voboost Code Style Configuration

This repository contains unified code formatting rules for all Voboost projects.

## Contents

- `.editorconfig` - Core formatting rules for ktlint and IDE
- `.clinerules` - Global project intelligence rules for all Voboost projects
- `codestyle.gradle` - Centralized ktlint configuration script
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

## Global Project Rules

This repository provides centralized project intelligence rules through `.clinerules` that apply to all Voboost projects:

- **Language Policy**: All code, comments, and documentation must be in English
- **Naming Convention**: Project name is always "Voboost" (not "VoBoost")
- **Code Style**: All files must end with blank line, no emoji allowed
- **Architecture Patterns**: Result<T> for error handling, nullable properties for graceful degradation
- **Documentation Standards**: Memory bank for project knowledge, KDoc for public APIs

### Project-specific rules

Each Voboost project should reference the global rules and add only project-specific additions:

```bash
# In each project's .clinerules file
## Global Rules (CRITICAL)
- This project follows ALL common rules from ../voboost-codestyle/.clinerules
- The rules below are PROJECT-SPECIFIC additions to the global rules
- NEVER duplicate global rules here - they are inherited automatically
```

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

// Apply Voboost code style configuration
apply(from = "../voboost-codestyle/codestyle.gradle")
```

The centralized configuration automatically handles:
- ktlint version management from `versions.properties`
- Android project settings
- Reporter configuration
- File exclusion filters
- Integration with check task
- Additional convenience tasks (`formatCode`, `checkCodeStyle`)

### 3. Multi-module projects

For multi-module projects, add to root `build.gradle.kts`:

```kotlin
subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(from = "../voboost-codestyle/codestyle.gradle")
}
```

This applies the centralized configuration to all submodules automatically.

### 4. Available Gradle tasks

After integration, the following tasks become available:

```bash
# Check code style compliance
./gradlew ktlintCheck

# Automatic code formatting
./gradlew ktlintFormat

# Additional convenience tasks (provided by centralized config)
./gradlew formatCode      # Same as ktlintFormat
./gradlew checkCodeStyle  # Same as ktlintCheck

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

