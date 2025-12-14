import { useEffect, useState } from "react";
import MainLayout from "../layout/MainLayout";
import { fetchSweets, purchaseSweet, addSweet, updateSweet } from "../api/sweetApi";
import SweetCard from "../components/SweetCard";
import SweetForm from "../components/SweetForm";
import styles from "../styles/Dashboard.module.css";

export default function Dashboard() {
  const [sweets, setSweets] = useState([]);
  const [search, setSearch] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [editSweet, setEditSweet] = useState(null);

  useEffect(() => {
    loadSweets();
  }, []);

  const loadSweets = async () => {
    const data = await fetchSweets();
    setSweets(data);
  };

  const handlePurchase = async (id) => {
    await purchaseSweet(id, 1);
    loadSweets();
  };

  const handleAddOrUpdate = async (data) => {
    if (editSweet) {
      await updateSweet(editSweet.id, data);
      setEditSweet(null);
    } else {
      await addSweet(data);
    }
    setShowForm(false);
    loadSweets();
  };

  const handleEdit = (sweet) => {
    setEditSweet(sweet);
    setShowForm(true);
  };

  const filtered = sweets.filter((s) =>
    s.name.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <MainLayout>
      <div className={styles.container}>
        <h2>Welcome To SweetsShop</h2>

        <div className={styles.searchContainer}>
          <input
            className={styles.search}
            placeholder="Search sweets..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
          <button
            className={styles.addButton}
            onClick={() => setShowForm(true)}
          >
            + Add
          </button>
        </div>

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

        <div className={styles.grid}>
          {filtered.map((sweet) => (
            <SweetCard
              key={sweet.id}
              sweet={sweet}
              onPurchase={handlePurchase}
              onEdit={handleEdit}
            />
          ))}
        </div>
      </div>
    </MainLayout>
  );
}
