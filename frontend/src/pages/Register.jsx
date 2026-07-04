import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

const Register = () => {
  const [form, setForm] = useState({ name: "", email: "", password: "" });
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    await api.post("/auth/register", form);
    navigate("/login");
  };

  return (
    <div className="form-card">
      <h2>Create account</h2>
      <form onSubmit={handleSubmit}>
        <input
          placeholder="Name"
          value={form.name}
          onChange={(e) => setForm({ ...form, name: e.target.value })}
        />
        <input
          placeholder="Email"
          value={form.email}
          onChange={(e) => setForm({ ...form, email: e.target.value })}
        />
        <input
          type="password"
          placeholder="Password"
          value={form.password}
          onChange={(e) => setForm({ ...form, password: e.target.value })}
        />
        <button className="btn-primary" type="submit">
          Register
        </button>
      </form>
    </div>
  );
};

export default Register;
