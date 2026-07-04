import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

const Checkout = () => {
  const [cart, setCart] = useState([]);
  const [address, setAddress] = useState("");
  const [paymentMethod, setPaymentMethod] = useState("CARD");
  const navigate = useNavigate();

  useEffect(() => {
    api.get("/cart").then((res) => setCart(res.data));
  }, []);

  const total =
    cart.reduce((sum, item) => sum + item.quantity * item.product.price, 0) + 5;

  const handlePlaceOrder = async () => {
    await api.post(
      `/orders/place?address=${encodeURIComponent(address)}&paymentMethod=${paymentMethod}`,
    );
    navigate("/orders");
  };

  return (
    <div className="section">
      <h2>Checkout</h2>
      <div className="card-grid">
        <div className="card">
          <h3>Shipping Address</h3>
          <textarea
            value={address}
            onChange={(e) => setAddress(e.target.value)}
          />
          <select
            value={paymentMethod}
            onChange={(e) => setPaymentMethod(e.target.value)}
          >
            <option value="CARD">Card</option>
            <option value="COD">COD</option>
          </select>
          <button className="btn-primary" onClick={handlePlaceOrder}>
            Place Order
          </button>
        </div>
        <div className="card">
          <h3>Summary</h3>
          {cart.map((item) => (
            <p key={item.id}>
              {item.product.name} x {item.quantity}
            </p>
          ))}
          <p>Delivery: $5</p>
          <p>Total: ${total.toFixed(2)}</p>
        </div>
      </div>
    </div>
  );
};

export default Checkout;
