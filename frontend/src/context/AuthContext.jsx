import { createContext, useContext, useEffect, useState } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(localStorage.getItem("token"));

  useEffect(() => {
    if (token) {
      const storedUser = JSON.parse(localStorage.getItem("user") || "null");
      setUser(storedUser);
    }
  }, [token]);

  const login = (authData) => {
    localStorage.setItem("token", authData.token);
    localStorage.setItem(
      "user",
      JSON.stringify({
        name: authData.name,
        email: authData.email,
        role: authData.role,
      }),
    );
    setToken(authData.token);
    setUser({
      name: authData.name,
      email: authData.email,
      role: authData.role,
    });
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    setToken(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
