import styles from "../styles/SweetCard.module.css";

export default function SweetCard({ sweet, onPurchase, onEdit }) {
  return (
    <div className={`${styles.card} ${styles.user}`}>
      <h3>{sweet.name}</h3>
      <p>Category: {sweet.category}</p>
      <p>Price: ₹{sweet.price}</p>
      <p>Stock: {sweet.quantity}</p>

      <div className={styles.userButtons}>
        <button
          disabled={sweet.quantity === 0}
          onClick={() => onPurchase(sweet.id)}
          className={`${styles.button} ${styles.purchase}`}
        >
          {sweet.quantity === 0 ? "Out of Stock" : "Purchase"}
        </button>
        <button
          onClick={() => onEdit(sweet)}
          className={`${styles.button} ${styles.update}`}
        >
          Update
        </button>
      </div>
    </div>
  );
}
