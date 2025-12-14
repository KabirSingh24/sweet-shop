import { render, screen, fireEvent } from "@testing-library/react";
import Auth from "./Auth";
import { BrowserRouter } from "react-router-dom"; // wrap with Router

test("renders login form by default", () => {
  render(
    <BrowserRouter>
      <Auth />
    </BrowserRouter>
  );
  expect(screen.getByPlaceholderText("Email")).toBeInTheDocument();
  expect(screen.getByPlaceholderText("Password")).toBeInTheDocument();
});

test("toggles to registration form", () => {
  render(
    <BrowserRouter>
      <Auth />
    </BrowserRouter>
  );
  fireEvent.click(screen.getByText("Sign Up"));
  expect(screen.getByText("Sign Up")).toBeInTheDocument();
});
