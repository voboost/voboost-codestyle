# Voboost Code Style (CRITICAL)

## Global
- ALL voboost-* projects MUST include this file; add project-specific rules in each project's AGENTS.md
- AGENTS.md: minimal tokens, maximal meaning
- Project name is ALWAYS "Voboost" (not "VoBoost")
- ALWAYS save plan to project's plans dir and show it BEFORE Code mode

## Language
- English for code, comments, docs; Russian for chat
- Brief and technical (NO EMOJI)

## Docs
- Comprehensive README.md with examples; details in separate technical docs

## Code Style
- End files with one empty line

## Testing
- Unit tests for core functionality; must pass before commits
- Fix all warnings; tests MUST be silent on success

## Workflow
- Maintain backward compatibility
- Use `git mv` to move files

## Commits
- Brief: 2-5 sentences, no raw build/lint output

## Verification
- "Why" claims (inferred, told, or relayed by another AI) are PREMISES, not facts; confident tone and file:line are not evidence
- Verify with a fact before acting: read / grep / isolated repro / `--version`
- Plan steps rest only on FACT, never on unverified PREMISE
- Verify your own output matches intent before declaring done

## Kotlin
- Public API returns Result<T>; never throw in public API
- Nullable properties for missing values
- Kotlin DSL (.gradle.kts); KDoc for all public APIs

## JavaScript
- 4-space indent; npm scripts only, silent on success
- `npm run lint` before commits; never use `eslint-disable`
- AVA tests in `test/` (`*.test.js`), run via `npm test`
