# Technical Context

## Technology Stack

### Primary Tools
- **ktlint 1.0.1**: Kotlin code formatter and linter
- **ktlint-gradle 12.1.0**: Gradle plugin for ktlint integration
- **EditorConfig**: Cross-editor configuration standard

### Supported Platforms
- **Android**: Full support with android.set(true)
- **Kotlin/JVM**: Standard Kotlin projects
- **Gradle**: Build system integration

### File Types Supported
- **Kotlin**: .kt, .kts files
- **Android XML**: layouts, manifests
- **Gradle**: .gradle, .gradle.kts files
- **YAML/JSON**: configuration files
- **Markdown**: documentation

## Development Environment

### Requirements
- **Gradle**: 7.0+ (minimum compatibility)
- **Kotlin**: 1.8.0+ (minimum compatibility)
- **Android Gradle Plugin**: 8.0.0+ (for Android projects)
- **Java**: 11+ (for ktlint execution)

### IDE Support
- **Android Studio**: Native EditorConfig support
- **IntelliJ IDEA**: Native EditorConfig support
- **VS Code**: EditorConfig extension required

## Configuration Standards

### Code Style Rules
```
# Core formatting
indent_style = space
indent_size = 4 (Kotlin/Java/XML)
indent_size = 2 (YAML/JSON/Markdown)
max_line_length = 120
end_of_line = lf
charset = utf-8
insert_final_newline = true

# Kotlin-specific
ij_kotlin_allow_trailing_comma = false
ij_kotlin_imports_layout = *,java.**,javax.**,kotlin.**,^
```

### Tool Configuration
```kotlin
ktlint {
    version.set("1.0.1")
    android.set(true)
    ignoreFailures.set(false)
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)
    }
    filter {
        exclude("**/generated/**")
        exclude("**/build/**")
    }
}
```

## Integration Patterns

### Symlink Architecture
- Uses Unix symbolic links for configuration sharing
- Ensures single source of truth
- Automatic propagation of changes

### Build Integration
- Gradle plugin integration
- CI/CD ready with `ktlintCheck` task
- Automatic formatting with `ktlintFormat` task

### Version Management
- Centralized in `versions.properties`
- Consistent across all projects
- Easy update mechanism

## Constraints and Limitations

### Technical Constraints
- Requires Unix-like filesystem for symlinks (macOS, Linux)
- ktlint cannot auto-correct all violations (enum naming, complex formatting)
- Gradle plugin version compatibility requirements

### Project Constraints
- All projects must be in same parent directory for relative symlinks
- Consistent directory structure required
- Manual fixes needed for some code style violations

## Performance Considerations
- ktlint runs during build process
- Incremental checking for faster builds
- Parallel execution support in Gradle
- Caching of ktlint results