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
            },
        },
        rules: {
            'no-console': 'error',
        },
    },
    // Allow console only in logger files
    {
        files: ['**/logger.js', '**/logger.mjs', '**/Logger.js', '**/Logger.mjs'],
        rules: {
            'no-console': 'off',
        },
    },
];
