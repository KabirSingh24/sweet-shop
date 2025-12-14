// import { Link, useNavigate } from "react-router-dom";
// import styles from "../styles/Navbar.module.css";
// import logo from "../assets/logo.jpeg";

// export default function Navbar({ variant }) {
//   const navigate = useNavigate();

//   return (
//     <nav className={styles.navbar}>
//       <div className={styles.left} onClick={() => navigate("/dashboard")}>
//         <img src={logo} alt="Sweet Shop Logo" className={styles.logo} />
//         <h2>Sweet Shop</h2>
//       </div>

//       <div className={styles.right}>
//         {variant === "app" && <Link to="/dashboard">Dashboard</Link>}

//         {localStorage.getItem("token") ? (
//           <button
//             onClick={() => {
//               localStorage.removeItem("token");
//               navigate("/auth");
//             }}
//           >
//             Logout
//           </button>
//         ) : (
//           <Link to="/auth">Login</Link>
//         )}
//       </div>
//     </nav>
//   );
// }

import { Link, useNavigate } from "react-router-dom";
import styles from "../styles/Navbar.module.css";
import logo from "../assets/logo.jpeg";
import { getUserRole } from "../utils/jwt"; // helper to get role from token

export default function Navbar({ variant }) {
  const navigate = useNavigate();
  const role = getUserRole(); // "USER" or "ADMIN"

  return (
    <nav className={styles.navbar}>
      <div
        className={styles.left}
        onClick={() => {
          if (role === "ADMIN") {
            navigate("/admin");
          } else {
            navigate("/dashboard");
          }
        }}
      >
        <img src={logo} alt="Sweet Shop Logo" className={styles.logo} />
        <h2>Sweet Shop</h2>
      </div>

      <div className={styles.right}>
        {variant === "app" && role !== "ADMIN" && <Link to="/dashboard">Dashboard</Link>}

        {localStorage.getItem("token") ? (
          <button
            onClick={() => {
              localStorage.removeItem("token");
              navigate("/auth");
            }}
          >
            Logout
          </button>
        ) : (
          <Link to="/auth">Login</Link>
        )}
      </div>
    </nav>
  );
}
