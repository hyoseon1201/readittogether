import React from "react";
import { getSessionState } from "../../api/accounts/authService";

import kakaoLoginButton from "../../assets/login/kakao.png"

const KakaoLoginButton = () => {
  const KAKAO_CLIENT_ID = "b501c56371fa45075470f62c6972768e";
  const redirectUri = encodeURIComponent(
    "https://j10d206.p.ssafy.io/login/oauth2/code/kakao"
  );

  const handleKakaoLogin = async () => {
    try {

      const state = encodeURIComponent(
        JSON.stringify({ provider: "kakao" })
      );
      const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_CLIENT_ID}&redirect_uri=${redirectUri}&response_type=code&state=${state}`;
      window.location.href = kakaoURL;
    } catch (error) {
      console.error("Kakao Login Failure:", error);
    }
  };

  return (
    <button onClick={handleKakaoLogin}>
      <img
        src={kakaoLoginButton}
        alt="카카오 로그인 버튼"
      />
    </button>
  );
};

export default KakaoLoginButton;
