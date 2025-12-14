import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import SweetForm from "./SweetForm";

const sweet = {
  name: "Chocolate",
  category: "Candy",
  price: 50,
  quantity: 10
};

test("renders form with initial values", () => {
  render(<SweetForm sweet={sweet} onClose={() => {}} onSubmit={() => {}} />);
  expect(screen.getByPlaceholderText("Name").value).toBe("Chocolate");
  expect(screen.getByPlaceholderText("Category").value).toBe("Candy");
  expect(screen.getByPlaceholderText("Price").value).toBe("50");       // string
  expect(screen.getByPlaceholderText("Quantity").value).toBe("10");    // string
});

test("SweetForm calls onSubmit with correct data", () => {
  const mockSubmit = jest.fn();
  render(<SweetForm onClose={() => {}} onSubmit={mockSubmit} />);

  fireEvent.change(screen.getByPlaceholderText("Name"), { target: { value: "Candy" } });
  fireEvent.change(screen.getByPlaceholderText("Category"), { target: { value: "Snack" } });
  fireEvent.change(screen.getByPlaceholderText("Price"), { target: { value: "20" } });
  fireEvent.change(screen.getByPlaceholderText("Quantity"), { target: { value: "5" } });

  fireEvent.click(screen.getByText("Add"));

  expect(mockSubmit).toHaveBeenCalledWith({
    name: "Candy",
    category: "SNACK",
    price: 20,       // converted in handleSubmit
    quantity: 5      // converted in handleSubmit
  });
});
