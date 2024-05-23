import React, { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import handleCardDetail from "../../api/card/HandleCardDetail";
import handleCardDelete from "../../api/card/HandleCardDelete";

import diaryButton from "../../assets/library/diaryButton.png";
import deleteButton from "../../assets/library/deleteButton.png";

const Card = ({ item, onClose, onDelete, onCloseFirst, deleteEnable }) => {
  const [cardDetail, setCardDetail] = useState(""); // 형식 제발 null로 하지말고

  const handleDelete = (e) => {
    e.preventDefault(e);
    try {
      handleCardDelete(item.cardId);
      alert("카드가 삭제되었습니다.");
      onClose();
      onDelete(item.cardId);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    const fetchCardDetail = async () => {
      try {
        const response = await handleCardDetail(item.cardId);
        setCardDetail(response);
      } catch (error) {
        console.error(error);
      }
    };

    fetchCardDetail();

    // Clean-up 함수를 사용하여 컴포넌트가 unmount 될 때 이펙트 정리
    return () => {
      setCardDetail(null); // 컴포넌트가 unmount 될 때 데이터 초기화
    };
  }, [item.cardId]);

  return (
    <div>
      <div className="grid grid-cols-2 gap-1">
        {/* 표지 이미지 */}
        {cardDetail.cover && (
          <Link to={`/detail-book/${cardDetail.bookId}`}>
            <img
              src={cardDetail.cover}
              alt=""
              className="col-span-1 cursor-pointer mx-auto w-[280px] h-[380px] object-cover rounded-lg"
            />
          </Link>
        )}
        {/* 책 정보 */}
        <div className="col-span-1">
          <p className="text-2xl font-bold mb-1">{cardDetail.title}</p>
          <p className="mb-6 font-semibold">{cardDetail.author}</p>
          <Link onClick={onCloseFirst} to={`/${cardDetail.fromId}`}>
            <div className="flex items-center mt-2 mb-5 cursor-pointer">
              <img
                className="w-6 h-6 mr-2 rounded-full"
                src={cardDetail.profile}
                alt="프로필 이미지"
              />
              <span className="customFont">{cardDetail.nickname}</span>
            </div>
          </Link>
          <div className="bg-blue-100 py-3 rounded-lg overflow-auto h-[250px] mr-12">
            <p className="mx-3 customFont">{cardDetail.comment}</p>
          </div>
        </div>
      </div>
      {/* 뒤로가기 버튼(실제로는 컴포넌트 닫기) */}
      <div className="flex pt-5 justify-between">
        <button onClick={onClose} className="mx-3">
          <img className="w-5 h-5" src={diaryButton} alt="뒤로가기" />
        </button>
        {deleteEnable && (
          <button onClick={handleDelete} className="mx-3">
            <img className="w-5 h-5" src={deleteButton} alt="삭제하기" />
          </button>
        )}
      </div>
    </div>
  );
};

export default Card;
