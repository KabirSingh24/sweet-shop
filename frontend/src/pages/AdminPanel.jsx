import { useEffect, useState } from "react";
import MainLayout from "../layout/MainLayout";
import { fetchSweets, deleteSweet, restockSweet, addSweet, updateSweet } from "../api/sweetApi";
import SweetForm from "../components/SweetForm";
import styles from "../styles/Admin.module.css";

export default function AdminPanel() {
  const [sweets, setSweets] = useState([]);
  const [quantity, setQuantity] = useState(1);
  const [showForm, setShowForm] = useState(false);
  const [editSweet, setEditSweet] = useState(null);

  useEffect(() => {
    loadSweets();
  }, []);

  const loadSweets = async () => {
    const data = await fetchSweets();
    setSweets(data);
  };

  const handleDelete = async (id) => {
    await deleteSweet(id);
    loadSweets();
  };

  const handleRestock = async (id) => {
    await restockSweet(id, quantity);
    loadSweets();
    setQuantity(1);
  };

  const handleAddOrUpdate = async (data) => {
    if (editSweet) {
      await updateSweet(editSweet.id, data);
      setEditSweet(null);
    } else {
      await addSweet(data);
    }

    // Hide form after adding/updating
    setShowForm(false);
    loadSweets();
  };

  const handleEditClick = (sweet) => {
    setEditSweet(sweet);
    setShowForm(true);
  };

  return (
    <MainLayout variant="app">
      <div className={styles.container}>
        <h2>Admin Panel</h2>

        <button onClick={() => {
          setEditSweet(null); // ensure new sweet form
          setShowForm(true);
        }}>Add Sweet</button>

        {showForm && (
          <SweetForm
            sweet={editSweet}
            onClose={() => {
              setShowForm(false);
              setEditSweet(null);
            }}
            onSubmit={handleAddOrUpdate}
          />
        )}

        {sweets.map((sweet) => (
          <div key={sweet.id} className={styles.row}>
            <span>{sweet.name} — Stock: {sweet.quantity}</span>

            <input
              type="number"
              min="1"
              value={quantity}
              onChange={(e) => setQuantity(+e.target.value)}
            />

            <button onClick={() => handleRestock(sweet.id)}>Restock</button>
            <button onClick={() => handleEditClick(sweet)}>Edit</button>
            <button onClick={() => handleDelete(sweet.id)}>Delete</button>
          </div>
        ))}
      </div>
    </MainLayout>
  );
}
