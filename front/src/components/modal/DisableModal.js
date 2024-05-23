import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import disable from "../../api/accounts/Disable";

const DisableModal = ({ onClose }) => {
  const navigate = useNavigate();
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleDisable = async (e) => {
    e.preventDefault();
    try {
      const response = await disable(password);
      alert("탈퇴가 완료되었습니다.");
      navigate(`/login`);
      return response;
    } catch (error) {
      setError("비밀번호가 일치하지 않습니다.");
      console.error(error);
      throw error;
    }
  };

  const handleChange = (e) => {
    setPassword(e.target.value);
    setError(""); // 에러 메시지 초기화
  };

  return (
    <div
      className="fixed inset-0 flex items-center justify-center z-50"
      onClick={onClose}
    >
      <div className="absolute inset-0 bg-gray-800 opacity-50"></div>
      <div
        className="flex items-center justify-center w-full h-full"
        onClick={(e) => e.stopPropagation()}
      >
        <form
          onSubmit={handleDisable}
          className="bg-white w-[400px] rounded-lg p-6 z-10 text-center"
        >
          <label htmlFor="password" className="text-gray-700 block mb-2">
            비밀번호 입력
          </label>
          <input
            type="password"
            value={password}
            onChange={handleChange}
            className="border border-gray-300 px-3 py-2 mb-4 w-full"
          />
          {error && <p className="text-red-500 mb-4">{error}</p>}
          <p className="text-sm text-gray-800 mb-10">
            한번 탈퇴한 계정은 복구할 수 없습니다.
          </p>
          <button
            type="submit"
            className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
          >
            탈퇴하기
          </button>
        </form>
      </div>
    </div>
  );
};

export default DisableModal;
