import { useEffect, useState } from "react";
import api from "../api/axios";

const Orders = () => {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    api.get("/orders").then((res) => setOrders(res.data));
  }, []);

  return (
    <div className="section">
      <h2>Your Orders</h2>
      {orders.length === 0 ? (
        <p>No orders yet.</p>
      ) : (
        orders.map((order) => (
          <div className="card" key={order.id} style={{ marginBottom: "1rem" }}>
            <h3>Order #{order.id}</h3>
            <p>Status: {order.status}</p>
            <p>Total: ${order.totalAmount}</p>
            <p>Address: {order.shippingAddress}</p>
          </div>
        ))
      )}
    </div>
  );
};

export default Orders;
