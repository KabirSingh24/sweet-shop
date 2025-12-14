export const getUserRole = () => {
  const token = localStorage.getItem("token");
  if (!token) return null;

  try {
    const payload = JSON.parse(atob(token.split(".")[1]));
    return payload.role || payload.authorities?.[0];
  } catch (err) {
    return null;
  }
};

export const isAdmin = () => {
  const role = getUserRole();
  return role === "ADMIN";
};
