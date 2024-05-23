import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

import DeleteComment from "../../api/book/comment/DeleteComment";
import UpdateCommentModal from "./UpdatecommentModal";

import img from "../../assets/profile/man.png";

const CommentCard = ({ bookId, comment, refreshComments }) => {
  const [showModal, setShowModal] = useState(false);
  const handleEditClick = () => setShowModal(true);
  const handleCancel = () => setShowModal(false);
  const memberId = localStorage.getItem("memberId");

  const handleDeleteSubmit = async () => {
    try {
      await DeleteComment(comment.commentId);
      refreshComments(); // 여기서 함수를 호출
      alert("평가를 삭제하였습니다.")
      refreshComments(); // 여기서 함수를 호출
    } catch (error) {
      console.log(error);
    }
  };

  const isOwner = memberId === comment.memberId.toString();

  return (
    <div className="flex min-w-[270px] max-h-[350px]">
      <div className="flex flex-col justify-between p-2 bg-sky-100 shadow-lg rounded-lg m-2 w-72 h-80 mb-40">
        <div className="flex justify-between items-start w-full">
          <Link to={`/${comment.memberId}`} className="text-sm font-semibold">
            <div className="flex items-center">
              <img
                className="w-6 h-6 rounded-full mr-2 ml-2"
                src={comment.profileImage}
                alt="프로필"
              />
              <span className="text-sm customFont">{comment.nickname}</span>
            </div>
          </Link>
        </div>
        <div className="p-2 m-2 customFont bg-white h-full rounded-lg overflow-y-auto">
          {comment.comment}
        </div>
        <div className="flex justify-between items-end w-full">
          <div className="ml-2">
            <div className="flex items-center justify-between w-9 h-6 rounded-lg p-1 bg-white">
              <div className={`smallstar text-sky-400`}>&#9733;</div>
              <span className="text-xs font-bold">{comment.rating * 0.5}</span>
            </div>
          </div>
          {isOwner && (
            <div className="pr-2">
              <button
                onClick={handleEditClick}
                className="text-xs font-bold bg-white hover:bg-sky-200 p-1"
              >
                수정
              </button>
              {showModal && (
                <UpdateCommentModal
                  onClose={handleCancel}
                  refreshComments={refreshComments}
                  bookId={bookId}
                  commentId={comment.commentId}
                  initialComment={comment.comment}
                  initialRating={comment.rating}
                />
              )}
              <button
                onClick={handleDeleteSubmit}
                className="text-xs font-bold bg-white hover:bg-sky-200 p-1 ml-2"
              >
                삭제
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

const Comments = ({
  bookId,
  refreshComments,
  commentList,
}) => {
  return (
    <div className="px-44">
      <h1 className="text-3xl font-bold text-left mb-10 ml-8">
        코멘트 <span className="text-sky-500">+ {commentList.length}</span>
      </h1>
      <div className="flex flex-wrap m-2 mb-10">
        {commentList.map((comment, index) => (
          <div key={`comment-${index}`} className="p-2 w-1/4 min-w-[w-72]">
            <CommentCard
              key={comment.id}
              bookId={bookId}
              comment={comment}
              refreshComments={refreshComments}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Comments;
