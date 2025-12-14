import { render, screen, fireEvent } from "@testing-library/react";
import SweetCard from "./SweetCard";

const sweet = {
  id: 1,
  name: "Chocolate",
  category: "Candy",
  price: 50,
  quantity: 10
};

test("renders sweet details correctly", () => {
  render(<SweetCard sweet={sweet} onPurchase={() => {}} onEdit={() => {}} />);
  expect(screen.getByText(/Chocolate/i)).toBeInTheDocument();
  expect(screen.getByText(/Category: Candy/i)).toBeInTheDocument();
  expect(screen.getByText(/Price: ₹50/i)).toBeInTheDocument();
  expect(screen.getByText(/Stock: 10/i)).toBeInTheDocument();
});

test("disables purchase button when out of stock", () => {
  const sweetOut = { ...sweet, quantity: 0 };
  render(<SweetCard sweet={sweetOut} onPurchase={() => {}} onEdit={() => {}} />);
  expect(screen.getByText(/Out of Stock/i)).toBeDisabled();
});
