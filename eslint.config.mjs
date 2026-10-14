// @ts-check
import tseslint from 'typescript-eslint';
import eslintConfigPrettier from 'eslint-config-prettier';

export default tseslint.config(
  {
    ignores: ['node_modules/', 'playwright-report/', 'test-results/', 'blob-report/'],
  },
  ...tseslint.configs.recommended,
  eslintConfigPrettier,
  {
    rules: {
      '@typescript-eslint/no-unused-vars': ['error', { argsIgnorePattern: '^_' }],
    },
  },
  {
    files: ['tests/ui/**/*.ts', 'tests/api/**/*.ts'],
    rules: {
      'no-restricted-imports': [
        'error',
        {
          patterns: [
            {
              group: ['**/banks/**', '**/contracts/**', '**/features/**', '**/support/**', '**/test-setup/**'],
              message: 'Los tests deben usar las fixtures públicas y no reintroducir capas legacy.',
            },
          ],
        },
      ],
    },
  },
);
