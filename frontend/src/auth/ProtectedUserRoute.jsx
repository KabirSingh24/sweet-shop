import { Navigate } from "react-router-dom";
import { getUserRole } from "../utils/jwt"; // your helper to get role from token

export default function ProtectedUserRoute({ children }) {
  const token = localStorage.getItem("token");
  const role = getUserRole(); // should return 'USER' or 'ADMIN'

  if (!token) {
    return <Navigate to="/auth" />;
  }

  if (role === "ADMIN") {
    return <Navigate to="/admin" />;
  }

  return children;
}
