import { useEffect, useState } from "react";
import api from "../api/axios";

const Shop = () => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    api.get("/products").then((res) => setProducts(res.data));
  }, []);

  return (
    <div className="section">
      <h2>Shop All Products</h2>
      <div className="card-grid">
        {products.map((product) => (
          <div className="card product-card" key={product.id}>
            <h3>{product.name}</h3>
            <p>{product.description}</p>
            <div className="price-row">
              <strong>${product.price}</strong>
              <span>{product.stock} available</span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Shop;
