import React, { useEffect, useState } from "react";

import CommentForm from "../../api/book/comment/CommentForm";

const CreateComment = ({ bookId, refreshComments }) => {
  const [commentInput, setCommentInput] = useState("");
  const [rating, setRating] = useState(0);
  const [hover, setHover] = useState(0);

  const handleInput = (e) => {
    setCommentInput(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!commentInput.trim()) return; // 빈 댓글은 추가하지 않음

    try {
      // CommentForm API 호출 함수를 사용하여 서버로 데이터 전송
      CommentForm(bookId, commentInput, rating * 2);
      refreshComments();
      alert("평가를 완료하였습니다!");
      refreshComments();
    } catch (error) {
      console.error("댓글 생성 중 오류 발생:", error);
    }

    setCommentInput(""); // 입력 필드 초기화
    setRating(0); // 평점 초기화
  };

  const handleMouseEnter = (index) => {
    setHover(index);
  };

  const handleMouseLeave = () => {
    setHover(0);
  };

  const handleClick = (index) => {
    setRating(index);
  };

  return (
    <div className="mt-4 w-[500px]">
      <form
        onSubmit={rating === 0 ? (e) => e.preventDefault() : handleSubmit}
        className="flex flex-col gap-4"
      >
        <div className="flex mb-2">
          <h3 className="text-2xl mr-2">평점</h3>
          <div className="flex items-center">
            {[...Array(5)].map((_, index) => {
              const ratingValue = index + 1;
              return (
                <div
                  key={index}
                  className={`star ${
                    ratingValue <= (hover || rating)
                      ? "text-sky-400"
                      : "text-gray-400"
                  }`}
                  onClick={() => handleClick(ratingValue)}
                  onMouseEnter={() => handleMouseEnter(ratingValue)}
                  onMouseLeave={handleMouseLeave}
                >
                  &#9733;
                </div>
              );
            })}
          </div>
        </div>
        <hr className="border-t-2 border-sky-400" />
        <textarea
          className="customFont min-h-[100px] resize-none border rounded-md p-2 border-sky-400"
          placeholder="내용을 입력하세요..."
          value={commentInput}
          onChange={handleInput}
          maxLength={120}
        ></textarea>
        <button
          type="submit"
          className="self-end px-4 py-2 bg-sky-400 text-white rounded hover:bg-sky-500 disabled:opacity-50"
          disabled={rating === 0}
        >
          평가 하기
        </button>
      </form>
    </div>
  );
};

export default CreateComment;
