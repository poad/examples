// @ts-check

import jest from 'eslint-plugin-jest';
// @ts-expect-error ignore
import _import from 'eslint-plugin-import';
import nextPlugin from '@next/eslint-plugin-next';
import { fixupPluginRules } from '@eslint/compat';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import js from '@eslint/js';
import { FlatCompat } from '@eslint/eslintrc';
import tseslint from 'typescript-eslint';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const compat = new FlatCompat({
  baseDirectory: __dirname,
  recommendedConfig: js.configs.recommended,
  allConfig: js.configs.all,
});

export default tseslint.config(
  {
    ignores: ['**/*.d.ts', '**/*.js'],
  },
  ...compat.extends(
    'plugin:@next/next/recommended',
    'plugin:@typescript-eslint/recommended',
  ),
  {
    plugins: {
      jest,
      import: fixupPluginRules(_import),
      '@next/next': nextPlugin,
    },
    rules: {
      '@typescript-eslint/indent': ['error', 2],
      'spaced-comment': [
        'error',
        'always',
        {
          markers: ['/ <reference'],
        },
      ],
      'max-len': [
        'error',
        {
          code: 200,
        },
      ],
      ...nextPlugin.configs.recommended.rules,
      ...nextPlugin.configs['core-web-vitals'].rules,
      '@next/next/no-img-element': ['off'],
      'prefer-promise-reject-errors': ['off'],
      'react/jsx-filename-extension': ['off'],
      'react/prop-types': ['off'],
      'import/extensions': ['off'],
      'jsx-a11y/anchor-is-valid': ['off'],
      'no-return-assign': ['off'],
      'react/display-name': ['off'],
      'react/react-in-jsx-scope': ['off'],

      '@next/next/no-duplicate-head': 'off',
      '@next/next/no-page-custom-font': 'off',
    },
  },
);
