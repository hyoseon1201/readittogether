import React, { useEffect, useContext } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { AuthContext } from "../../authentication/AuthContext";

const LoginCallBack = () => {
  const { setUserState } = useContext(AuthContext);
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const memberId = params.get("memberId");
    const nickname = params.get("nickname");
    const accessToken = params.get("accessToken");
    const profileImage = params.get("profileImage");

   
if (accessToken) {
  localStorage.setItem("accessToken", accessToken);
  localStorage.setItem("memberId", memberId);
  localStorage.setItem("nickname", nickname);
  // localStorage.setItem("refreshToken", refreshToken);
  localStorage.setItem("profileImage", profileImage);
  
  //리프레쉬제외하고 다 ㄱ
  setUserState((prevState) => ({
    ...prevState,
    status: "loggedIn",
    accessToken,
    memberId,
    nickname,
    // refreshToken,
    profileImage,
  }));

  navigate("/"); 
}

  }, [location, navigate, setUserState]);

  return null; // UI가 필요 없는 경우
};

export default LoginCallBack;