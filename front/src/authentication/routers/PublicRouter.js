import { useContext } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { AuthContext } from "../AuthContext";

const PublicRoute = () => {
  const { userState } = useContext(AuthContext);
  const memberId = localStorage.getItem("memberId");

  return userState.status === "loggedOut" ? <Outlet /> : <Navigate to={`/${memberId}`} />;
};

export default PublicRoute;
