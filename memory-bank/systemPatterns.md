# System Patterns

## Architecture Overview
Central configuration repository with symlink-based distribution to consumer projects.

```
voboost-codestyle/
├── .editorconfig (master configuration)
├── versions.properties (tool versions)
├── integration-example.gradle.kts (template)
└── memory-bank/ (documentation)

Consumer Projects:
├── .editorconfig -> ../voboost-codestyle/.editorconfig (symlink)
├── build.gradle.kts (with ktlint plugin)
└── ktlint configuration
```

## Key Design Patterns

### Single Source of Truth
- Central `.editorconfig` file contains all formatting rules
- Projects use symlinks instead of copies
- Changes propagate automatically to all projects

### Tool Integration Pattern
```kotlin
// Standard ktlint integration
plugins {
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
}

ktlint {
    version.set("1.0.1")
    android.set(true)
    ignoreFailures.set(false)
    // ... configuration
}
```

### Symlink Distribution
```bash
# Create symlink to central config
ln -s ../voboost-codestyle/.editorconfig ./.editorconfig
```

## Component Relationships

### Core Components
- **`.editorconfig`**: Master formatting rules
- **`versions.properties`**: Tool version management
- **`integration-example.gradle.kts`**: Integration template

### Integration Points
- **ktlint**: Primary code formatter and linter
- **EditorConfig**: IDE integration
- **Gradle**: Build system integration
- **Git**: Version control integration

## Configuration Management
- Centralized version management in `versions.properties`
- Consistent tool versions across all projects
- Easy updates through central repository

## Error Handling
- Strict mode: `ignoreFailures.set(false)`
- Clear error reporting through multiple reporters
- Automatic fixing for correctable issues
- Manual intervention required for complex violations

## Scalability Patterns
- Easy addition of new projects through symlink pattern
- Custom rules can be added to `ktlint_rules/` directory
- Version updates managed centrally
- IDE integration works automatically

