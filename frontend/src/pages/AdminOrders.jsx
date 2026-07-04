import { useEffect, useState } from "react";
import api from "../api/axios";

const AdminOrders = () => {
  const [orders, setOrders] = useState([]);

  const load = async () => {
    const res = await api.get("/orders/all");
    setOrders(res.data);
  };

  useEffect(() => {
    load();
  }, []);

  return (
    <div className="section">
      <h2>Order Management</h2>
      {orders.map((order) => (
        <div className="card" key={order.id} style={{ marginBottom: "1rem" }}>
          <h3>Order #{order.id}</h3>
          <p>Status: {order.status}</p>
          <p>Total: ${order.totalAmount}</p>
        </div>
      ))}
    </div>
  );
};

export default AdminOrders;
