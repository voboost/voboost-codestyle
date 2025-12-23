import js from '@eslint/js';
import prettier from 'eslint-config-prettier';

export default [
    js.configs.recommended,
    prettier,
    {
        languageOptions: {
            ecmaVersion: 2022,
            sourceType: 'module',
            globals: {
                console: 'readonly',
                Java: 'readonly',
                module: 'readonly',
                setTimeout: 'readonly',
                clearTimeout: 'readonly',
                setInterval: 'readonly',
                clearInterval: 'readonly',
                process: 'readonly',
                rpc: 'readonly',
            },
        },
        rules: {
            'no-console': 'error',

            // temporary disabled
            // 'eqeqeq': ['error', 'always'],  // Enforce === and !==
            // 'camelcase': ['error', { properties: 'never' }],  // Enforce camelCase for functions
        },
    },
    // Allow console only in build files and test files
    {
        files: ['**/build-*.mjs', '**/*.test.js', '**/*.test.mjs'],
        rules: {
            'no-console': 'off',
        },
    },
];
