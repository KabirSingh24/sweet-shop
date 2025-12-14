import { Routes, Route, Navigate } from "react-router-dom";
import Auth from "./pages/Auth";
import Dashboard from "./pages/Dashboard";
import AdminPanel from "./pages/AdminPanel";
import ProtectedRoute from "./auth/ProtectedRoute"; // admin-only
import ProtectedUserRoute from "./auth/ProtectedUserRoute"; // user-only

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/auth" />} />
      <Route path="/auth" element={<Auth />} />

      {/* User-only dashboard */}
      <Route
        path="/dashboard"
        element={
          <ProtectedUserRoute>
            <Dashboard />
          </ProtectedUserRoute>
        }
      />

      {/* Admin-only panel */}
      <Route
        path="/admin"
        element={
          <ProtectedRoute adminOnly>
            <AdminPanel />
          </ProtectedRoute>
        }
      />

      <Route path="*" element={<Navigate to="/auth" />} />
    </Routes>
  );
}
