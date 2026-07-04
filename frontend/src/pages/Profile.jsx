import { useAuth } from "../context/AuthContext";

const Profile = () => {
  const { user } = useAuth();

  return (
    <div className="section">
      <h2>Profile</h2>
      <div className="card">
        <p>
          <strong>Name:</strong> {user?.name}
        </p>
        <p>
          <strong>Email:</strong> {user?.email}
        </p>
        <p>
          <strong>Role:</strong> {user?.role}
        </p>
      </div>
    </div>
  );
};

export default Profile;
