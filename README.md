# Voboost Code Style Configuration

Centralized code formatting rules for all Voboost projects.

## Contents

| File | Description |
|------|-------------|
| `.editorconfig` | Core formatting rules for ktlint, IDE, and JavaScript |
| `AGENTS.md` | Global project intelligence rules for AI assistants |
| `config-prettier.mjs` | Shared Prettier configuration for JavaScript |
| `config-eslint.mjs` | Shared ESLint configuration for JavaScript |
| `codestyle.gradle` | Centralized ktlint + Spotless + Checkstyle configuration |
| `checkstyle.gradle` | Checkstyle configuration for Java validation |
| `spotless.gradle` | Spotless configuration for Java formatting |
| `checkstyle.xml` | Checkstyle rules definition |
| `versions.properties` | Tool versions for consistency |
| `integration-example.gradle.kts` | Example integration configuration |

## Supported Technologies

- **Kotlin** (.kt, .kts files)
- **Java** (.java files)
- **JavaScript/Node.js** (.js, .mjs files)
- **Android XML** (layouts, manifests)
- **Gradle** (.gradle, .gradle.kts)
- **YAML/JSON** configuration files
- **Markdown** documentation

## Core Rules

| Language | Indent | Line Length | Line Endings |
|----------|--------|-------------|--------------|
| Kotlin/Java/XML | 4 spaces | 100 chars | LF |
| JavaScript | 4 spaces | 100 chars | LF |
| YAML/JSON/Markdown | 2 spaces | - | LF |

All files use UTF-8 encoding and end with a newline. Trailing comma is disabled.

## Kotlin Code Style

### ktlint Configuration

- **Version**: 1.0.1
- **Android mode**: enabled
- **Trailing comma**: disabled

### Disabled Rules

Rules disabled for Compose and BEM compatibility:

| Rule | Reason |
|------|--------|
| `function-naming` | Allow Compose PascalCase functions |
| `filename` | Allow BEM naming like `Component.test-unit.kt` |
| `class-naming` | Allow BEM file naming patterns |

### Import Order

```
*, java.**, javax.**, kotlin.**, ^
```

## Java Code Style

### Architecture Patterns

- **Java Custom View**: Primary implementation with all business logic and rendering
- **Kotlin Compose Wrapper**: Minimal AndroidView bridge for Compose integration
- **Hybrid Development**: Seamless integration between Java components and Kotlin Compose UI

### Checkstyle Rules

| Rule | Value |
|------|-------|
| Line length | 100 characters |
| Cyclomatic complexity | Maximum 10 |
| Method length | Maximum 150 lines |
| Parameter count | Maximum 7 |

### Spotless Formatting

Java code is formatted using Google Java Format with AOSP style:

- **Indentation**: 4 spaces (AOSP style)
- **Import order**: `java`, `javax`, `android`, `androidx`, `com`, `org`
- **Line wrapping**: Automatic for long strings
- **Javadoc**: Preserved formatting
- **Custom rules**: No wildcard imports

## JavaScript Code Style

### Prettier Configuration

| Option | Value |
|--------|-------|
| `printWidth` | 100 |
| `tabWidth` | 4 |
| `semi` | true |
| `singleQuote` | true |
| `trailingComma` | es5 |

### ESLint Rules

- `no-console`: error (except in build files and tests)
- ECMAScript 2022 syntax
- Module source type

## BEM Methodology

### File Naming Convention

```
MyComponent.kt           # Main component
MyComponent.test-unit.kt # Unit tests (co-located)
MyComponent.test-visual.kt # Visual regression tests
```

### Java BEM Support

```java
MyComponent.java           // Main component
MyComponent.test-unit.java // Unit tests
MyComponent.test-visual.java // Visual regression tests
```

### Test File Handling

- Test files are co-located with component files in `src/main/java/`
- Screenshots are shared between Kotlin and Java tests
- Checkstyle excludes `*.test-*.java` from main validation

## Project Integration

### 1. Create symlink to .editorconfig

```bash
ln -s ../voboost-codestyle/.editorconfig .editorconfig
```

### 2. Gradle Configuration

Add to `build.gradle.kts`:

```kotlin
plugins {
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("com.diffplug.spotless") version "6.25.0"
}

apply(from = "../voboost-codestyle/codestyle.gradle")
```

For multi-module projects:

```kotlin
subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(from = "../voboost-codestyle/codestyle.gradle")
}
```

### 3. JavaScript Projects

Add to `package.json`:

```json
{
    "devDependencies": {
        "@eslint/js": "^9.39.1",
        "eslint": "^9.39.1",
        "eslint-config-prettier": "^10.0.1",
        "globals": "^16.0.0",
        "prettier": "^3.4.2"
    },
    "scripts": {
        "lint": "eslint src --fix --config ../voboost-codestyle/config-eslint.mjs && prettier --write 'src/**/*.js' --config ../voboost-codestyle/config-prettier.mjs"
    }
}
```

## Available Gradle Tasks

### Kotlin

| Task | Description |
|------|-------------|
| `ktlintCheck` | Check Kotlin code style |
| `ktlintFormat` | Format Kotlin code |

### Java

| Task | Description |
|------|-------------|
| `checkJavaStyle` | Check Java code style using Checkstyle |
| `formatJavaCode` | Format Java code using Spotless |
| `checkJavaFormat` | Check Java code formatting |

### Universal

| Task | Description |
|------|-------------|
| `formatCode` | Format all code (Kotlin and Java) |
| `checkCodeStyle` | Check all code style |

## Global Project Rules

Reference `AGENTS.md` for AI assistant rules. Each project should include in their `AGENTS.md`:

```markdown
## Global
- This project follows ALL common rules from ../voboost-codestyle/AGENTS.md
```

## Projects Using This Configuration

- [voboost](../voboost)
- [voboost-config](../voboost-config)
- [voboost-config-demo](../voboost-config-demo)
- [voboost-script](../voboost-script)
- [voboost-stubs](../voboost-stubs)

## Useful Links

- [ktlint Documentation](https://pinterest.github.io/ktlint/)
- [EditorConfig Specification](https://editorconfig.org/)
- [Google Java Format](https://github.com/google/google-java-format)
- [Checkstyle](https://checkstyle.org/)
