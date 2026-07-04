import { Link, NavLink } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const Navbar = () => {
  const { user, logout } = useAuth();

  return (
    <nav className="navbar">
      <div className="nav-brand">
        <Link to="/">NovaCart</Link>
      </div>
      <div className="nav-links">
        <NavLink to="/">Home</NavLink>
        <NavLink to="/shop">Shop</NavLink>
        <NavLink to="/categories">Categories</NavLink>
        <NavLink to="/wishlist">Wishlist</NavLink>
        <NavLink to="/cart">Cart</NavLink>
        {user?.role === "ADMIN" && <NavLink to="/admin">Admin</NavLink>}
      </div>
      <div className="nav-actions">
        {user ? (
          <>
            <span>Hello, {user.name}</span>
            <button onClick={logout}>Logout</button>
          </>
        ) : (
          <>
            <Link to="/login">Login</Link>
            <Link to="/register">Register</Link>
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
