import Navbar from "../components/Navbar";
import React from "react";

export default function MainLayout({ children, variant = "app", setSweets }) {
  return (
    <>
      <Navbar variant={variant} setSweets={setSweets} />
      <main>{children}</main>
    </>
  );
}
