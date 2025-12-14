import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import Auth from "./Auth";

// Mock react-router-dom useNavigate
const mockNavigate = jest.fn();
jest.mock("react-router-dom", () => ({
  useNavigate: () => mockNavigate,
}));

// Mock API calls
jest.mock("../api/authApi", () => ({
  loginUser: jest.fn(() => Promise.resolve({ token: "abc123" })),
  registerUser: jest.fn(() => Promise.resolve({})),
}));

// Mock isAdmin
jest.mock("../utils/jwt", () => ({
  isAdmin: jest.fn(() => false),
}));

test("renders login form by default", () => {
  render(<Auth />);
  expect(screen.getByPlaceholderText("Email")).toBeInTheDocument();
  expect(screen.getByPlaceholderText("Password")).toBeInTheDocument();
  expect(screen.getByText("Sign In")).toBeInTheDocument();
});

test("toggles to registration form", () => {
  render(<Auth />);
  fireEvent.click(screen.getByText("Sign Up"));
  expect(screen.getByText("Sign Up")).toBeInTheDocument();
  expect(screen.getByText("Already have an account?")).toBeInTheDocument();
});

test("calls loginUser on sign in click", async () => {
  const { loginUser } = require("../api/authApi");
  render(<Auth />);
  fireEvent.change(screen.getByPlaceholderText("Email"), { target: { value: "test@test.com" } });
  fireEvent.change(screen.getByPlaceholderText("Password"), { target: { value: "123456" } });

  fireEvent.click(screen.getByText("Sign In"));
  expect(loginUser).toHaveBeenCalledWith({ email: "test@test.com", password: "123456" });
});

test("calls registerUser on sign up click", async () => {
  const { registerUser } = require("../api/authApi");
  render(<Auth />);
  fireEvent.click(screen.getByText("Sign Up")); // switch to register
  fireEvent.change(screen.getByPlaceholderText("Email"), { target: { value: "new@test.com" } });
  fireEvent.change(screen.getByPlaceholderText("Password"), { target: { value: "abcdef" } });

  fireEvent.click(screen.getByText("Sign Up"));
  expect(registerUser).toHaveBeenCalledWith({ email: "new@test.com", password: "abcdef" });
});
