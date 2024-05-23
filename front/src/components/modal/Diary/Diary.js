import React, { useState } from "react";
import Received from "./Received";
import Sent from "./Sent";

import logo from "../../../assets/navbar/logo2.png";

// 카드 등록일 기준으로 정렬

const Diary = ({ onClose }) => {
  const handleCardOpen = () => {
    setShowButtons(false); // 카드가 열리면 버튼 숨김
  };

  const handleCardClose = () => {
    setShowButtons(true); // 카드가 닫히면 버튼 보이기
  };

  // Cannot access 'handleCardOpen' before initialization
  const [currentComponent, setCurrentComponent] = useState("received"); // 열었을 시 기본은 받은 카드
  const [showButtons, setShowButtons] = useState(true);

  const handleReceivedClick = () => {
    setCurrentComponent("received");
  };

  const handleSentClick = () => {
    setCurrentComponent("sent");
  };

  // 모달 내에서 버튼을 클릭해도 모달이 닫히지 않도록
  const handleButtonClick = (e) => {
    e.stopPropagation(); // 이벤트 버블링 방지
  };

  return (
    <div
      className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 overflow-y-auto h-full w-full z-50"
      onClick={onClose}
    >
      <div
        className="bg-white rounded-lg p-8 w-[850px] h-[555px] overflow-y-auto relative"
        onClick={handleButtonClick}
      >
        <div className="absolute top-7 text-gray-500 font-semibold text-sm font-serif italic leading-normal">
          <div className="flex">
            <img className="w-5 mr-1" src={logo} alt="로고" />
            <p>Read-it Together</p>
          </div>
        </div>
        {showButtons && (
          <div className="absolute top-5 right-10">
            <button
              className="bg-white border border-sky-300 hover:bg-sky-300 hover:text-white rounded-lg text-sky-500 text-xs px-4 py-2 transition-colors duration-300 mr-2"
              onClick={handleReceivedClick}
            >
              받은 카드
            </button>
            <button
              className="bg-white border border-sky-300 hover:bg-sky-300 hover:text-white rounded-lg text-sky-500 text-xs px-4 py-2 transition-colors duration-300"
              onClick={handleSentClick}
            >
              보낸 카드
            </button>
          </div>
        )}
        <div>
          {/* {currentComponent} */}
          {/* 삼항연산자 */}
          {currentComponent === "received" ? (
            <Received
              onCardOpen={handleCardOpen}
              onCardClose={handleCardClose}
              onCloseFirst={onClose}
            />
          ) : (
            <Sent
              onCardOpen={handleCardOpen}
              onCardClose={handleCardClose}
              onCloseFirst={onClose}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default Diary;
