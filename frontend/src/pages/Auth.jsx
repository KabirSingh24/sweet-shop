import { useNavigate } from "react-router-dom";
import { loginUser, registerUser } from "../api/authApi";
import { isAdmin } from "../utils/jwt";
import MainLayout from "../layout/MainLayout";
import { useState } from "react";
import styles from "../styles/Auth.module.css";

export default function Auth() {
  const navigate = useNavigate();
  const [isLogin, setIsLogin] = useState(true);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const handleLogin = async () => {
  try {
    const res = await loginUser({ email, password });
    localStorage.setItem("token", res.token);

    if (isAdmin()) {
      navigate("/admin"); // redirect admin to admin page
    } else {
      navigate("/dashboard"); // regular user
    }
  } catch {
    setError("Invalid credentials");
  }
};


  const handleRegister = async () => {
    try {
      await registerUser({ email, password });
      setMessage("Registration successful. Please sign in.");
      setIsLogin(true);
      setEmail("");
      setPassword("");
    } catch {
      setError("Registration failed");
    }
  };

  return (
    <MainLayout variant="auth">
      <div className={styles.container}>
        <div className={styles.cardContainer}>
          <div className={styles.cardContainer_left}>
            <p className={styles.cardleft_heading}>
              {isLogin ? "Sign In" : "Sign Up"}
            </p>
            {error && <p style={{ color: "red" }}>{error}</p>}
            {message && <p style={{ color: "green" }}>{message}</p>}
            <div className={styles.inputContainers}>
              <input
                className={styles.inputField}
                placeholder="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
              <input
                className={styles.inputField}
                placeholder="Password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              <div
                className={styles.buttonWithOutline}
                onClick={isLogin ? handleLogin : handleRegister}
              >
                <p>{isLogin ? "Sign In" : "Sign Up"}</p>
              </div>
            </div>
          </div>

          <div className={styles.cardContainer_right}>
            <p>{isLogin ? "Don't have an account?" : "Already have an account?"}</p>
            <div
              className={styles.buttonWithOutline}
              onClick={() => {
                setError("");
                setMessage("");
                setIsLogin(!isLogin);
              }}
            >
              <p>{isLogin ? "Sign Up" : "Sign In"}</p>
            </div>
          </div>
        </div>
      </div>
    </MainLayout>
  );
}
