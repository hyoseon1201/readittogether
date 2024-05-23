import React, { useState, useEffect, useRef } from "react";
import IntroForm from "../../api/llibrary/intro/IntroForm";

// 이미지
import logo from "../../assets/navbar/logo2.png";
import deleteButton from "../../assets/profile/delete.png";

const Intro = ({ onClose, onUpdate, introText }) => {
  const [text, setText] = useState(introText);
  const modalRef = useRef();

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      // API 호출
      await IntroForm(text);
      onUpdate(text);
      onClose(); // 모달 닫기
    } catch (error) {
      console.error("방명록 에러:", error);
    }
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (modalRef.current && !modalRef.current.contains(event.target)) {
        onClose(); // 모달 닫기
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [onClose]);

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
      <div className="bg-white p-5 rounded w-[400px] h-[350px]" ref={modalRef}>
        <div className="flex justify-between items-center text-gray-500 font-semibold text-sm font-serif italic">
          <div className="flex">
            <img className="w-5 mr-1" src={logo} alt="로고" />
            <p>Read-it Together</p>
          </div>
          <button onClick={onClose}>
            <img className="w-3" src={deleteButton} alt="X" />
          </button>
        </div>
        <hr className="mt-3 border-gray-300" />
        <form onSubmit={handleSubmit}>
          <div className="flex items-center justify-between my-2">
            <p className="text-gray-500 font-semibold text-sm font-serif italic leading-normal ml-2">
              Intro message
            </p>
            <button
              type="submit"
              className="bg-white border border-gray-300 hover:bg-gray-300 hover:text-white rounded-lg text-gray-500 text-xs py-2 px-4 transition-colors duration-300"
            >
              등록
            </button>
          </div>
          <div className="bg-gray-200 rounded-lg p-2">
            <textarea
              className="w-full h-[200px] p-2 border rounded resize-none text-lg customFont text-gray-600"
              value={text}
              onChange={(e) => setText(e.target.value)}
              maxLength={120}
            ></textarea>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Intro;
