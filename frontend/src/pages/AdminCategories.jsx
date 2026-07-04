import { useEffect, useState } from "react";
import api from "../api/axios";

const AdminCategories = () => {
  const [categories, setCategories] = useState([]);
  const [name, setName] = useState("");

  const load = async () => {
    const res = await api.get("/categories");
    setCategories(res.data);
  };

  useEffect(() => {
    load();
  }, []);

  const createCategory = async () => {
    await api.post("/categories", { name });
    setName("");
    load();
  };

  return (
    <div className="section">
      <h2>Category Management</h2>
      <div className="card" style={{ marginBottom: "1rem" }}>
        <input
          placeholder="Category Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <button className="btn-primary" onClick={createCategory}>
          Add Category
        </button>
      </div>
      {categories.map((category) => (
        <div className="card" key={category.id}>
          {category.name}
        </div>
      ))}
    </div>
  );
};

export default AdminCategories;
