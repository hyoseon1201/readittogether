import axios from "axios";
import React, { createContext, useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;
const AuthContext = createContext();

const AuthProvider = ({ children }) => {
  const [userState, setUserState] = useState({ status: "loggedOut" });
  const accessToken = localStorage.getItem("accessToken");
  const locaion = useLocation();

  useEffect(() => {
    const verifyAccessToken = async () => {
      try {
        // 액세스 토큰으로 사용자 인증 시도
        const response = await axios.post(
          `${API_BASE_URL}/members/verify-access`,
          { accessToken: `Bearer ${accessToken}` },
          { withCredentials: true }
        );
        console.log("토큰 유효성 검사:",response.data.message);
        setUserState({ status: "loggedIn" });
      } catch (err) {
        // 액세스 토큰이 유효하지 않을 때(예: 만료된 경우)
        if (err.response && err.response.status === 403) {
          reissueAccessToken();
        } else {
          setUserState({ status: "loggedOut" });
        }
      }
    };

    const reissueAccessToken = async () => {
      try {
        // 리프레시 토큰으로 새 액세스 토큰 요청
        const response = await axios.post(
          `${API_BASE_URL}/members/reissue`,
          null,
          { withCredentials: true }
        );
        const newAccessToken = response.data.data.newAccessToken;
        // 새로 발급받은 액세스 토큰을 로컬 스토리지에 저장
        localStorage.setItem("accessToken", newAccessToken);
        setUserState({ status: "loggedIn" });
        console.log("토큰이 만료되어 재발급:", response.data)
      } catch (err) {
        console.error(err);
        // 리프레시 토큰으로도 인증 실패 시 로그아웃 처리
        setUserState({ status: "loggedOut" });
      }
    };

    if (accessToken) {
      verifyAccessToken();
    } else {
      reissueAccessToken();
    }
  }, [locaion, accessToken]);

  return (
    <AuthContext.Provider value={{ userState, setUserState }}>
      {children}
    </AuthContext.Provider>
  );
};

export { AuthContext, AuthProvider };
