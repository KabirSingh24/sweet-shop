import { useState } from "react";
import styles from "../styles/SweetForm.module.css";
import React from "react";

export default function SweetForm({ onClose, onSubmit, sweet }) {
  const [name, setName] = useState(sweet?.name || "");
  const [category, setCategory] = useState(sweet?.category || "");
  const [price, setPrice] = useState(sweet?.price || "");
  const [quantity, setQuantity] = useState(sweet?.quantity || 1);

  const handleSubmit = () => {
    if (!name.trim() || !category.trim() || !price || quantity < 1) {
      alert("Please fill in all fields correctly.");
      return;
    }

    onSubmit({
      name: name.trim(),
      category: category.trim().toUpperCase(),
      price,
      quantity,
    });
  };

  return (
    <div className={styles.modalBackdrop}>
      <div className={styles.modal}>
        <h2>{sweet ? "Update Sweet" : "Add Sweet"}</h2>

        <input
          placeholder="Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
        <input
          placeholder="Category"
          value={category}
          onChange={(e) => setCategory(e.target.value)}
        />
        <input
          type="number"
          placeholder="Price"
          value={price}
          onChange={(e) => setPrice(+e.target.value)}
        />
        <input
          type="number"
          placeholder="Quantity"
          value={quantity}
          min="1"
          onChange={(e) => setQuantity(+e.target.value)}
        />

        <div className={styles.modalButtons}>
          <button onClick={handleSubmit}>{sweet ? "Update" : "Add"}</button>
          <button onClick={onClose}>Cancel</button>
        </div>
      </div>
    </div>
  );
}
