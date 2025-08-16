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
- **Java** (.java files)
- **Android XML** (layouts, manifests)
- **Gradle** (.gradle, .gradle.kts)
- **YAML/JSON** configuration files
- **Markdown** documentation

## Java Support

### Overview
voboost-codestyle provides comprehensive Java support alongside Kotlin, enabling:
- **Java Custom View Components**: Full-featured Android custom views with complete rendering logic
- **Kotlin Compose Wrappers**: Lightweight AndroidView integration for Compose compatibility
- **Unified Code Style**: Consistent formatting and validation across both languages
- **BEM Methodology**: Co-located test files following BEM naming conventions

### Architecture Patterns
- **Java Custom View**: Primary implementation with all business logic and rendering
- **Kotlin Compose Wrapper**: Minimal AndroidView bridge for Compose integration
- **Shared Resources**: Common theme values, data models, and test screenshots
- **Hybrid Development**: Seamless integration between Java components and Kotlin Compose UI

### BEM Structure for Java
- **Test files**: `*.test-unit.java`, `*.test-visual.java`
- **Location**: Co-located with component files in `src/main/java/`
- **Screenshots**: Shared screenshot directories with Kotlin tests
- **Naming**: Consistent BEM naming across Java and Kotlin files

### Java Code Style Tools
- **Checkstyle**: Code style validation with automotive-specific rules
- **Spotless**: Code formatting using Google Java Format (AOSP style)
- **Import Organization**: Consistent import ordering and unused import removal
- **Line Length**: 120 characters maximum for automotive display compatibility

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
apply(from = "../voboost-codestyle/checkstyle.gradle")  // For Java support
apply(from = "../voboost-codestyle/spotless.gradle")    // For Java support
```

For Java support, also add the Spotless plugin:

```kotlin
plugins {
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("com.diffplug.spotless") version "6.22.0"  // For Java support
}
```

The centralized configuration automatically handles:
- ktlint version management from `versions.properties`
- Checkstyle for Java code style validation
- Spotless for Java code formatting
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
# Kotlin tasks
./gradlew ktlintCheck        # Check Kotlin code style
./gradlew ktlintFormat       # Format Kotlin code
./gradlew formatKotlinCode   # Same as ktlintFormat (new)
./gradlew checkKotlinStyle   # Same as ktlintCheck (new)

# Java tasks
./gradlew checkJavaStyle     # Check Java code style using Checkstyle
./gradlew formatJavaCode     # Format Java code using Spotless
./gradlew checkJavaFormat    # Check Java code formatting using Spotless
./gradlew checkstyleMain     # Run Checkstyle on main source set
./gradlew checkstyleTest     # Run Checkstyle on test source set

# Universal tasks (both languages)
./gradlew formatCode         # Format all code (Kotlin and Java)
./gradlew checkCodeStyle     # Check all code style (Kotlin and Java)
./gradlew formatAllCode      # Same as formatCode
./gradlew checkAllFormat     # Check all formatting

# Module-specific tasks
./gradlew :app:ktlintCheck
./gradlew :library:ktlintFormat
./gradlew :app:checkJavaStyle
./gradlew :library:formatJavaCode
./gradlew :component:checkCodeStyle
```

### 5. Java-specific Configuration

#### Checkstyle Rules
The Java configuration includes automotive-specific rules:
- **Line length**: Maximum 120 characters for automotive displays
- **Cyclomatic complexity**: Maximum 10 for maintainability
- **Method length**: Maximum 150 lines
- **Parameter count**: Maximum 7 parameters
- **Import organization**: Specific order for Android projects
- **BEM test file support**: Special handling for `*.test-unit.java` files

#### Spotless Formatting
Java code is formatted using Google Java Format with AOSP style:
- **Indentation**: 4 spaces (AOSP style)
- **Import order**: `java`, `javax`, `android`, `androidx`, `com`, `org`
- **Line wrapping**: Automatic for long strings
- **Javadoc**: Preserved formatting
- **Custom rules**: No wildcard imports, consistent Android Log formatting

#### BEM Test File Handling
Special support for BEM methodology:
```java
// Main component
MyComponent.java

// Co-located test files
MyComponent.test-unit.java    // Unit tests
MyComponent.test-visual.java  // Visual regression tests
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

#### Complete Code Style Check (Kotlin + Java)
```yaml
name: Code Style Check

on: [push, pull_request]

jobs:
  code-style:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v4
    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'

    - name: Create symlink to .editorconfig
      run: ln -s ../voboost-codestyle/.editorconfig ./.editorconfig

    - name: Check Kotlin code style
      run: ./gradlew ktlintCheck

    - name: Check Java code style
      run: ./gradlew checkJavaStyle

    - name: Check Java code formatting
      run: ./gradlew checkJavaFormat

    # Alternative: Check all at once
    # - name: Check all code style
    #   run: ./gradlew checkCodeStyle
```

#### Separate Jobs for Kotlin and Java
```yaml
name: Code Style Check

on: [push, pull_request]

jobs:
  kotlin-style:
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

  java-style:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v4
    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'
    - name: Run Checkstyle
      run: ./gradlew checkJavaStyle
    - name: Check Java formatting
      run: ./gradlew checkJavaFormat
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

