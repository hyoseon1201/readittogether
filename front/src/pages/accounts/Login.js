import React, { useState, useEffect, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AuthContext } from "../../authentication/AuthContext";

import NormalLoginForm from "../../api/accounts/NormalLoginForm";
import KakaoLoginButton from "../../components/auth_login/KakaoLoginButton";

// 이미지
import mainImg from "../../assets/login/mainImg.png";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [saveEmail, setSaveEmail] = useState(false);
  const { setUserState } = useContext(AuthContext);

  const navigate = useNavigate();

  useEffect(() => {
    // 페이지 로드 시 저장된 아이디가 있으면 불러오기
    const savedEmail = localStorage.getItem("savedEmail");
    if (savedEmail) {
      setEmail(savedEmail);
      setSaveEmail(true);
    }
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault(); // 새로고침 방지
    try {
      const response = await NormalLoginForm(email, password);

      const memberId = response.data.memberId;
      const accessToken = response.data.accessToken;
      const nickname = response.data.nickname;
      const profileImage = response.data.profileImage;
      localStorage.setItem("memberId", memberId);
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("nickname", nickname);
      localStorage.setItem("profileImage", profileImage);
      localStorage.setItem("myEmail", email);

      if (saveEmail) {
        localStorage.setItem("savedEmail", email);
      } else {
        localStorage.removeItem("savedEmail");
      }

      setUserState({ status: "loggedIn" });

      navigate(`/${memberId}`);
    } catch (error) {
      console.log("로그인 실패:", error); // 로그인 실패 시
      alert("계정을 재확인 해주세요");

      // 로그인 실패 처리 작성해야댐
    }
  };

  return (
    <div className="flex items-center justify-center h-screen">
      <img
        className="absolute w-full h-full object-cover"
        src={mainImg}
        alt="메인 이미지"
      />

      {/* 로그인 창 */}
      <div className="relative p-8 bg-black bg-opacity-50 rounded shadow-md max-w-sm w-full">
        <h1 className="text-3xl customFont text-white font-bold text-center mb-6">
          로 그 인
        </h1>

        <form className="space-y-6" onSubmit={handleSubmit}>
          <input
            type="text"
            value={email}
            placeholder="이메일"
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-1 focus:ring-sky-500 bg-white text-gray-700"
            required
            onChange={(e) => setEmail(e.target.value)}
          />
          <input
            type="password"
            placeholder="비밀번호"
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-1 focus:ring-sky-500 bg-white text-gray-700"
            required
            onChange={(e) => setPassword(e.target.value)}
          />
          <input
            type="checkbox"
            checked={saveEmail}
            onChange={(e) => setSaveEmail(e.target.checked)}
            className="cursor-pointer"
          />
          <label
            htmlFor="saveEmail"
            className="text-white customFont ml-2 cursor-pointer"
            onClick={() => setSaveEmail(!saveEmail)}
          >
            아이디 저장
          </label>
          <button className="w-full text-xl customFont focus:outline-none text-white bg-sky-600 hover:bg-sky-500 focus:ring-4 focus:ring-sky-300 font-medium rounded-lg py-2.5 ">
            로그인
          </button>
          <div className="text-white text-center">
            <Link to="/signup" className="text-sm customFont hover:underline hover:text-sky-300">
              아이디가 없으신가요?
            </Link>
            <br />
            <Link
              to="/find-password"
              className="text-white text-sm customFont hover:underline hover:text-sky-300"
            >
              비밀번호를 잊으셨나요?
            </Link>


          </div>

          <div className="flex items-center justify-center space-x-2 select-none">
            <hr className="w-28 bg-white h-0.5 border-none" />
            <span className="text-white">OR</span>
            <hr className="w-28 bg-white h-0.5 border-none" />
          </div>
          <div className="flex justify-center">
            <KakaoLoginButton />
          </div>

        </form>
      </div>
    </div>
  );
};

export default Login;
