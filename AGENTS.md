# Voboost Code Style (CRITICAL)

## Global
- ALL Voboost projects MUST include common rules from voboost-codestyle/AGENTS.md (this file)
- Project-specific rules should be added to individual project AGENTS.md files
- Project name is ALWAYS "Voboost" (not "VoBoost")

## Language
- Use English for all code, comments and documentation
- Keep all text brief and technical (NO EMOJI)

## Docs
- Comprehensive README.md with usage examples
- Implementation details in separate technical docs

## Code Style
- End files with a single empty line

## Testing
- Unit tests for all core functionality, must pass before any commits
- Aim for high coverage of public API

## Development Workflow
- Memory bank MUST be updated when discovering new patterns
- All project information MUST be stored in memory bank, not in separate files
- All changes should maintain backward compatibility

## Kotlin
- All public API methods return Result<T> for consistent error handling
- Never throw exceptions in public API
- Use nullable properties for graceful missing value handling
- All build files use Kotlin DSL (.gradle.kts) when possible
- KDoc comments for all public APIs
