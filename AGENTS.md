# Voboost Code Style (CRITICAL)

## Global
- ALL Voboost projects MUST include these common rules (this file); add project-specific rules to each project's AGENTS.md
- Project name is ALWAYS "Voboost" (not "VoBoost")
- ALWAYS save plan to project's plans directory and show it BEFORE switching to Code mode

## Language
- English for all code, comments, docs; Russian for claude code chat
- Keep text brief and technical (NO EMOJI)

## Docs
- Comprehensive README.md with usage examples; implementation details in separate technical docs

## Code Style
- End files with a single empty line

## Testing
- Unit tests for all core functionality; must pass before commits, with high coverage of public API
- Fix all test warnings; tests MUST NOT output to console on successful pass

## Development Workflow
- Maintain backward compatibility
- Use `git mv` to move files, not read/write

## Commits
- Commit messages are brief: 2-5 sentences describing the change, no raw
  build/lint output (e.g. no "BUILD SUCCESSFUL: 0 errors")

## Verification
- Any "why" you inferred or were told — incl. a user-relayed diagnosis from
  another AI/agent — is a PREMISE, not a fact. Confident tone and file:line are
  not evidence.
- Verify a premise with a fact before acting: read / grep / isolated repro /
  `--version` — one command if possible.
- Plan steps rest only on FACT (read/run), never on an unverified PREMISE.

## Kotlin
- Public API methods return Result<T>; never throw exceptions in public API
- Use nullable properties for graceful missing values
- Build files use Kotlin DSL (.gradle.kts) when possible
- KDoc comments for all public APIs

## JavaScript
- Follow JavaScript Style Guide (4-space indent)
- Use NPM scripts exclusively; they should be silent on success
- Run `npm run lint` before commits; never use `eslint-disable`
- Use temporary files for complex script validation, not `node -e`
- Tests use AVA in `test/` (`*.test.js`), run via `npm test`
