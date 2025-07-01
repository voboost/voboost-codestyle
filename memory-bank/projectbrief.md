# Voboost Code Style - Project Brief

## Project Overview
Central repository for unified code style configuration across all Voboost projects.

## Core Purpose
- Provide single source of truth for code formatting rules
- Ensure consistent code style across all Voboost projects
- Simplify integration of code style tools in new projects

## Key Components
- `.editorconfig` - Core formatting rules for ktlint and IDEs
- `versions.properties` - Tool versions for consistency
- `integration-example.gradle.kts` - Example integration configuration
- `ktlint_rules/` - Directory for custom rules (future use)

## Target Projects
- voboost
- voboost-config
- voboost-config-demo
- Future Voboost projects

## Technical Stack
- **ktlint** 1.0.1 - Primary code formatter and linter
- **ktlint-gradle** 12.1.0 - Gradle plugin for ktlint integration
- **EditorConfig** - IDE integration for consistent formatting

## Integration Pattern
Projects use symbolic links to central `.editorconfig` file to ensure single source of truth.

## Success Criteria
- All Voboost projects follow identical code style
- Automated enforcement through ktlint
- Easy integration for new projects
- IDE support for real-time formatting