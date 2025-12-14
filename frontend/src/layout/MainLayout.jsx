import Navbar from "../components/Navbar";

export default function MainLayout({ children, variant = "app", setSweets }) {
  return (
    <>
      <Navbar variant={variant} setSweets={setSweets} />
      <main>{children}</main>
    </>
  );
}
