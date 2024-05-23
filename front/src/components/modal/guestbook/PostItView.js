import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

import GuestBookGet from "../../../api/llibrary/guestbook/GuestBookGet";
import GuestBookDelete from "../../../api/llibrary/guestbook/GuestBookDelete";

// 이미지
import left from "../../../assets/book/left.png";
import right from "../../../assets/book/right.png";
import deleteButton from "../../../assets/library/deleteButton.png";

const PostItView = ({
  onClose,
  postId,
  moveLeft,
  moveRight,
  memberId,
  isMemberPage,
  refreshList,
}) => {
  const [fromMemberId, setFromMemberId] = useState("");
  const [nickname, setNickname] = useState("");
  const [profileImg, setProfileImg] = useState(null);
  const [content, setContent] = useState("");
  const [createdAt, setCreatedAt] = useState("");
  const [showDeleteButton, setShowDeleteButton] = useState(false);

  useEffect(() => {
    const getPostItContent = async () => {
      if (postId) {
        try {
          const response = await GuestBookGet(postId);
          setFromMemberId(response.data.data.fromMemberId);
          setProfileImg(response.data.data.profileImg);
          setNickname(response.data.data.nickname);
          setContent(response.data.data.content);
          setCreatedAt(response.data.data.createdAt);
          checkDeleteButtonVisibility();
        } catch (error) {
          console.log("방명록 데이터 가져오기 실패", error);
        }
      }
    };
    getPostItContent();
  }, [postId]);

  const checkDeleteButtonVisibility = () => {
    if (memberId === fromMemberId || memberId === isMemberPage) {
      setShowDeleteButton(true);
    } else {
      setShowDeleteButton(false);
    }
  };

  const handleDeleteClick = async () => {
    try {
      await GuestBookDelete(postId);
      console.log("포스트잇 띳다");
      refreshList();
    } catch (error) {
      console.log("띠지마라", error);
    }
  };

  return (
    <div
      className="fixed inset-0 flex items-center justify-center overflow-y-auto h-full w-full z-40"
      onClick={onClose}
    >
      <div
        className="relative p-5 border w-96 h-96 shadow-lg bg-yellow-200"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="flex flex-col justify-between">
          <div className="flex items-center justify-between mb-2">
            <Link onClick={onClose} to={`/${fromMemberId}`}>
              <div className="flex items-center ">
                <img
                  className="w-8 h-8 rounded-full mr-2"
                  src={profileImg}
                  alt="프로필사진"
                />
                <h3 className="customFont leading-6 text-gray-900">
                  {nickname}
                </h3>
              </div>
            </Link>
            {showDeleteButton && (
              <div
                className="flex items-center cursor-pointer"
                onClick={handleDeleteClick}
              >
                <img className="flex w-5 h-5" src={deleteButton} alt="삭제" />
              </div>
            )}
          </div>
          <p className="text-xl customFont">{content}</p>
          <div className="flex justify-between pb-2"></div>
        </div>
        <button onClick={moveLeft}>
          <img
            className="absolute left-[-50px] top-44  w-8 rounded-full "
            src={left}
            alt="왼쪽"
          />
        </button>
        <button onClick={moveRight}>
          <img
            className="absolute right-[-50px] top-44 w-8 rounded-full"
            src={right}
            alt="오른쪽"
          />
        </button>
        <div className="absolute right-0 bottom-0 p-2 text-xs">{createdAt}</div>
      </div>
    </div>
  );
};

export default PostItView;
