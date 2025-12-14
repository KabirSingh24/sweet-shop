import { Navigate } from "react-router-dom";
import { isAdmin } from "../utils/jwt";

export default function ProtectedRoute({ children, adminOnly = false }) {
  const token = localStorage.getItem("token");

  if (!token) return <Navigate to="/auth" replace />;

  if (adminOnly && !isAdmin()) return <Navigate to="/dashboard" replace />;

  return children;
}
