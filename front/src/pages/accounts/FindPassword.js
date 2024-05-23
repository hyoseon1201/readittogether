import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import FindPWForm from "../../api/accounts/FindPWForm";

// 이미지 경로를 확인하고 적절히 수정해주세요.
import mainImg from "../../assets/login/mainImg.png";

const FindPassword = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault(); // 새로고침 방지
    try {
      const response = await FindPWForm(name, email);
      console.log(response); // 임시 비밀번호 발급 성공 시 응답
      alert("임시 비밀번호가 발급되었습니다. 이메일을 확인해주세요.");
      navigate("/login");
    } catch (error) {
      console.log("임시 비밀번호 발급 실패:", error);
      alert("임시 비밀번호 발급에 실패했습니다. 입력하신 정보를 확인해주세요.");
    }
  };

  return (
    <div className="flex items-center justify-center h-screen">
      <img
        className="absolute w-full h-full object-cover"
        src={mainImg}
        alt="메인 이미지"
      />

      {/* 임시 비밀번호 발급 창 */}
      <div className="relative p-8 bg-black bg-opacity-70 rounded shadow-md max-w-sm w-full">
        <h1 className="text-2xl text-white font-bold text-center mb-6">
          임시 비밀번호 발급
        </h1>

        <form className="space-y-6" onSubmit={handleSubmit}>
          <input
            type="text"
            value={name}
            placeholder="이름"
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-1 focus:ring-sky-500 bg-white text-gray-700"
            required
            onChange={(e) => setName(e.target.value)}
          />
          <input
            type="email"
            value={email}
            placeholder="이메일"
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-1 focus:ring-sky-500 bg-white text-gray-700"
            required
            onChange={(e) => setEmail(e.target.value)}
          />
          <button className="w-full focus:outline-none text-white bg-sky-600 hover:bg-sky-500 focus:ring-4 focus:ring-sky-300 font-medium rounded-lg py-2.5 ">
            임시 비밀번호 발급
          </button>
          <div className="text-white text-center">
            <Link to="/login" className="text-sm hover:underline">
              로그인 페이지로 돌아가기
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default FindPassword;
