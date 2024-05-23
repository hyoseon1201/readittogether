import React, { useState, useEffect } from "react";
// import axios from "axios";
import Card from "../Card";
import handleCardList from "../../../api/card/HandleCardList";

const Received = ({ onCardOpen, onCardClose, onCloseFirst }) => {
  const [page, setPage] = useState(1); // 기본 페이지 1
  const [limit] = useState(4); // 페이지당 아이템 수
  const [currentPageDataReceived, setCurrentPageDataReceived] = useState([]); // 현재 페이지에 표시될 데이터 구분
  const [selectedItem, setSelectedItem] = useState(null); // 개별 카드 선택
  const [showPagination, setShowPagination] = useState(true); // 페이지 보이기/숨기기
  const memberId = parseInt(localStorage.getItem("memberId"), 10); // 자료형 변경

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await handleCardList(page - 1, limit); // 페이지 값을 전달하여 데이터 불러오기
        setCurrentPageDataReceived(response.receivedCards.content); // 현재 페이지 데이터 설정
        console.log("현재 페이지: ", page - 1);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData(); // 페이지가 변경될 때마다 데이터 다시 불러오기
  }, [page, limit]); // 페이지 또는 limit

  // 페이지 이동
  const goToPrev = () => {
    setPage(page - 1);
  };

  const goToNext = () => {
    setPage(page + 1);
  };

  const openCard = (item) => {
    setSelectedItem(item);
    setShowPagination(false);
    if (typeof onCardOpen === "function") {
      onCardOpen(); // onCardOpen 함수가 존재하고 함수일 때만 호출
    }
  };

  const closeCard = () => {
    setSelectedItem(null);
    setShowPagination(true);
    if (typeof onCardClose === "function") {
      onCardClose(); // 닫을 때도 똑같이
    }
  };
  const handleDeleteAndUpdate = (deletedCardId) => {
    setCurrentPageDataReceived((prevData) =>
      prevData.filter((card) => card.cardId !== deletedCardId)
    );
  };

  return (
    <div>
      <h1 className="text-xl font-bold leading-6 text-sky-900 text-center my-5">
        받은 카드
      </h1>
      {selectedItem ? (
        <Card
          item={selectedItem}
          onClose={closeCard}
          onDelete={handleDeleteAndUpdate}
          onCloseFirst={onCloseFirst}
          deleteEnable={true}
        />
      ) : (
        <>
          <div className="grid grid-cols-2 gap-4">
            {currentPageDataReceived.map(
              (item, index) =>
                // item.from_Member !== item.to_Member && ( // 받은 카드인지 확인
                item.from_Member !== memberId && ( // 받은 카드인지 확인
                  <div
                    key={index}
                    className="text-center cursor-pointer"
                    onClick={() => openCard(item)}
                  >
                    <img
                      src={item.cover}
                      alt={item.title}
                      className="mx-auto mb-5 w-[300px] h-[435px]"
                    />
                  </div>
                )
            )}
          </div>
          {showPagination && (
            <div className="flex justify-center items-center mt-4">
              <button
                className="bg-white border border-sky-300 hover:bg-sky-300 hover:text-white rounded-lg text-sky-500 text-xs py-2 px-4  transition-colors duration-300"
                onClick={goToPrev}
                disabled={page === 1}
              >
                이전
              </button>
              <span className="mx-6 font-bold">{page}</span>
              <button
                className="bg-white border border-sky-300 hover:bg-sky-300 hover:text-white rounded-lg text-sky-500 text-xs py-2 px-4  transition-colors duration-300"
                onClick={goToNext}
                disabled={currentPageDataReceived.length < limit}
              >
                다음
              </button>
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default Received;
