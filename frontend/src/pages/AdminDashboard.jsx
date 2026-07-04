const AdminDashboard = () => {
  return (
    <div className="section">
      <h2>Admin Dashboard</h2>
      <div className="card-grid">
        <div className="card">
          <h3>Users</h3>
          <p>120</p>
        </div>
        <div className="card">
          <h3>Products</h3>
          <p>48</p>
        </div>
        <div className="card">
          <h3>Orders</h3>
          <p>310</p>
        </div>
        <div className="card">
          <h3>Revenue</h3>
          <p>$24,500</p>
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;
