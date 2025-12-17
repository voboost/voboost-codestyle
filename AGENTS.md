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
- All warnings in tests must be fixed
- Test MUST NOT output anything to console in successful pass

## Development Workflow
- All changes should maintain backward compatibility
- Use `mv` to move files, not read/write operations

## Kotlin
- All public API methods return Result<T> for consistent error handling
- Never throw exceptions in public API
- Use nullable properties for graceful missing value handling
- All build files use Kotlin DSL (.gradle.kts) when possible
- KDoc comments for all public APIs

## JavaScript
- Follow JavaScript Style Guide (4-space indent)
- Use NPM scripts exclusively; they should be silent on success
- Run `npm run lint` before commits; never use `eslint-disable`
- Use temporary files for complex script validation, not `node -e`
- Tests use AVA, are located in `test/` (`*.test.js`), and run via `npm test`
