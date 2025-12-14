module.exports = {
  testEnvironment: "jsdom",
  setupFilesAfterEnv: ["<rootDir>/jest.setup.js", "@testing-library/jest-dom"],
  moduleNameMapper: {
    "\\.module\\.css$": "<rootDir>/src/__mocks__/styleMock.js",
    "\\.(css|less|sass|scss)$": "identity-obj-proxy",
    "\\.(jpg|jpeg|png|gif|svg)$": "<rootDir>/src/__mocks__/fileMock.js"
  },
  transform: {
    "^.+\\.[tj]sx?$": "babel-jest"
  }
};
