import { useEffect, useState } from "react";
import api from "../api/axios";

const Cart = () => {
  const [items, setItems] = useState([]);

  const loadCart = async () => {
    const res = await api.get("/cart");
    setItems(res.data);
  };

  useEffect(() => {
    loadCart();
  }, []);

  const updateQuantity = async (itemId, quantity) => {
    await api.put(`/cart/update/${itemId}?quantity=${quantity}`);
    loadCart();
  };

  const removeItem = async (itemId) => {
    await api.delete(`/cart/remove/${itemId}`);
    loadCart();
  };

  const total = items.reduce(
    (sum, item) => sum + item.quantity * item.product.price,
    0,
  );

  return (
    <div className="section">
      <h2>Your Cart</h2>
      {items.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <div className="card-grid">
          {items.map((item) => (
            <div className="card" key={item.id}>
              <h3>{item.product.name}</h3>
              <p>${item.product.price}</p>
              <input
                type="number"
                value={item.quantity}
                onChange={(e) =>
                  updateQuantity(item.id, Number(e.target.value))
                }
              />
              <button
                className="btn-primary"
                onClick={() => removeItem(item.id)}
              >
                Remove
              </button>
            </div>
          ))}
          <div className="card">
            <h3>Cart Total</h3>
            <p>${total.toFixed(2)}</p>
          </div>
        </div>
      )}
    </div>
  );
};

export default Cart;
