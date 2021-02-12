module.exports = {
  cache: false,
  transform: {
    '^.+\\.(js|jsx|ts|tsx)$$': '<rootDir>/node_modules/babel-jest',
    '.*\\.(vue|vuex)$': '<rootDir>/node_modules/vue-jest'
  },
  moduleNameMapper: {
    '^@/(.*)$': '<rootDir>/src/$1',
    '^~/(.*)$': '<rootDir>/src/$1',
    '^vue$': 'vue/dist/vue.common.js'
  },
  moduleFileExtensions: ['ts', 'tsx', 'js', 'vue', 'json'],
  collectCoverage: true,
  collectCoverageFrom: [
    '<rootDir>/components/**/*.vue',
    '<rootDir>/pages/**/*.vue'
  ],
  preset: '<rootDir>/node_modules/ts-jest'
}
