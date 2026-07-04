import { useEffect, useState } from "react";
import api from "../api/axios";

const AdminProducts = () => {
  const [products, setProducts] = useState([]);
  const [name, setName] = useState("");
  const [price, setPrice] = useState(0);
  const [stock, setStock] = useState(0);

  const load = async () => {
    const res = await api.get("/products");
    setProducts(res.data);
  };

  useEffect(() => {
    load();
  }, []);

  const createProduct = async () => {
    await api.post("/products", { name, price, stock });
    setName("");
    setPrice(0);
    setStock(0);
    load();
  };

  return (
    <div className="section">
      <h2>Product Management</h2>
      <div className="card" style={{ marginBottom: "1rem" }}>
        <input
          placeholder="Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <input
          type="number"
          placeholder="Price"
          value={price}
          onChange={(e) => setPrice(e.target.value)}
        />
        <input
          type="number"
          placeholder="Stock"
          value={stock}
          onChange={(e) => setStock(e.target.value)}
        />
        <button className="btn-primary" onClick={createProduct}>
          Add Product
        </button>
      </div>
      {products.map((product) => (
        <div className="card" key={product.id}>
          {product.name} - ${product.price}
        </div>
      ))}
    </div>
  );
};

export default AdminProducts;
