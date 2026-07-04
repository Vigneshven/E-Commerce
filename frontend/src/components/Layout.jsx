import Navbar from "./Navbar";
import { Outlet } from "react-router-dom";

const Layout = () => {
  return (
    <div>
      <Navbar />
      <main className="page-shell">
        <Outlet />
      </main>
    </div>
  );
};

export default Layout;
