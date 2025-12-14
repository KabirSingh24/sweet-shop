import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import SweetForm from "./SweetForm";

const sweetData = {
  name: "Chocolate",
  category: "Candy",
  price: 50,
  quantity: 10,
};

test("renders form with initial values", () => {
  render(<SweetForm sweet={sweetData} onClose={() => {}} onSubmit={() => {}} />);
  
  expect(screen.getByPlaceholderText("Name").value).toBe("Chocolate");
  expect(screen.getByPlaceholderText("Category").value).toBe("Candy");
  expect(screen.getByPlaceholderText("Price").value).toBe(50);
  expect(screen.getByPlaceholderText("Quantity").value).toBe(10);
});

test("calls onSubmit with correct data when Add/Update clicked", () => {
  const handleSubmit = jest.fn();
  render(<SweetForm sweet={sweetData} onClose={() => {}} onSubmit={handleSubmit} />);

  fireEvent.change(screen.getByPlaceholderText("Name"), { target: { value: "Gum" } });
  fireEvent.change(screen.getByPlaceholderText("Category"), { target: { value: "Chicle" } });
  fireEvent.change(screen.getByPlaceholderText("Price"), { target: { value: 20 } });
  fireEvent.change(screen.getByPlaceholderText("Quantity"), { target: { value: 5 } });

  fireEvent.click(screen.getByText("Update")); // sweet exists, so button text is "Update"

  expect(handleSubmit).toHaveBeenCalledWith({
    name: "Gum",
    category: "CHICLE", // category is uppercased
    price: 20,
    quantity: 5,
  });
});

test("calls onClose when Cancel button is clicked", () => {
  const handleClose = jest.fn();
  render(<SweetForm onClose={handleClose} onSubmit={() => {}} />);
  
  fireEvent.click(screen.getByText("Cancel"));
  expect(handleClose).toHaveBeenCalled();
});
