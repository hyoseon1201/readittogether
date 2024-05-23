import { useContext } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { AuthContext } from "../AuthContext";

const PrivateRoute = () => {
  const { userState } = useContext(AuthContext);

  return userState.status === "loggedIn" ? (
    <Outlet />
  ) : (
    <Navigate to="/login" />
  );
};

export default PrivateRoute;
