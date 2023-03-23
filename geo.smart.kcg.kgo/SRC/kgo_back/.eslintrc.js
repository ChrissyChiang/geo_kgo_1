module.exports = {
  root: true,
  env: {
    node: true
  },
  globals: {
    $: 'readonly',
    moment: 'readonly',
    // jQuery: 'readonly',
  },
  extends: [
    'plugin:vue/recommended',
    '@vue/prettier'
  ],
  parserOptions: {
    parser: 'babel-eslint'
  },
  rules: {
    'prettier/prettier': 'warn',
    'vue/no-unused-components': 'warn',
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-console': 'off',
    'vue/html-self-closing': 'off',
    'vue/no-parsing-error': 'off',
    'no-empty': 'warn',
    'no-unused-vars': 'warn',
    'vue/no-v-html': 'off',
    'vue/require-v-for-key': 'off'
  },
  overrides: [
    {
      files: [
        '**/__tests__/*.{j,t}s?(x)',
        '**/tests/unit/**/*.spec.{j,t}s?(x)'
      ],
      env: {
        jest: true
      }
    }
  ]
}
