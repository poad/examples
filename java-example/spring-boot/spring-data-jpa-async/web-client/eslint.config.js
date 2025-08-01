// @ts-check

import jest from 'eslint-plugin-jest';
import _import from 'eslint-plugin-import';
import stylistic from '@stylistic/eslint-plugin';
import nextPlugin from '@next/eslint-plugin-next';
import { fixupPluginRules } from '@eslint/compat';
import tseslint from 'typescript-eslint';

import { includeIgnoreFile } from '@eslint/compat';
import path from "node:path";
import { fileURLToPath } from "node:url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const gitignorePath = path.resolve(__dirname, ".gitignore");

export default tseslint.config(
  includeIgnoreFile(gitignorePath),
  {
    ignores: ['.next', '**/*.d.ts', '**/*.js'],
  },
  ...tseslint.configs.strict,
  ...tseslint.configs.stylistic,
  {
    plugins: {
      jest,
      import: fixupPluginRules(_import),
      '@next/next': nextPlugin,
      '@stylistic': stylistic,
      '@stylistic/ts': stylistic,
    },
    // @ts-ignore
    rules: {
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

      '@stylistic/semi': ['error', 'always'],
      // '@stylistic/indent': ['error', 2],
      '@stylistic/comma-dangle': ['error', 'always-multiline'],
      '@stylistic/arrow-parens': ['error', 'always'],
      '@stylistic/quotes': ['error', 'single'],
    },
  },
);
