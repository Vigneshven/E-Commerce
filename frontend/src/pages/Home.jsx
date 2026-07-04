import { useEffect, useState } from "react";
import api from "../api/axios";

const Home = () => {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    const load = async () => {
      const [productsRes, categoriesRes] = await Promise.all([
        api.get("/products"),
        api.get("/categories"),
      ]);
      setProducts(productsRes.data.slice(0, 8));
      setCategories(categoriesRes.data);
    };
    load();
  }, []);

  return (
    <div className="home-page">
      <section className="hero">
        <div>
          <p className="eyebrow">Premium curated essentials</p>
          <h1>Discover the next era of shopping.</h1>
          <p>
            Modern fashion, electronics, and lifestyle products tailored for
            every moment.
          </p>
          <a className="btn-primary" href="/shop">
            Shop now
          </a>
        </div>
      </section>

      <section className="section">
        <h2>Featured Categories</h2>
        <div className="card-grid">
          {categories.map((category) => (
            <div className="card" key={category.id}>
              <h3>{category.name}</h3>
              <p>{category.description}</p>
            </div>
          ))}
        </div>
      </section>

      <section className="section">
        <h2>Trending Products</h2>
        <div className="card-grid">
          {products.map((product) => (
            <div className="card product-card" key={product.id}>
              <h3>{product.name}</h3>
              <p>{product.description}</p>
              <div className="price-row">
                <strong>${product.price}</strong>
                <span>{product.stock} in stock</span>
              </div>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
};

export default Home;
