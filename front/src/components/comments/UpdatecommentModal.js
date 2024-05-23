import React, { useState, useRef, useEffect } from "react";

import UpdateComentForm from "../../api/book/comment/UpdateCommentForm";

const UpdateCommentModal = ({
  onClose,
  bookId,
  commentId,
  initialComment,
  initialRating,
  refreshComments
}) => {
  const [comment, setComment] = useState(initialComment);
  const [rating, setRating] = useState(initialRating / 2);
  const [hover, setHover] = useState(0);
  const modalRef = useRef();

  const handleSubmit = async (e) => {
    e.preventDefault(); // 기본 제출 동작 방지

    try {
      await UpdateComentForm(bookId, commentId, comment, rating * 2);
      alert("댓글이 수정되었습니다.");
      onClose(); // 모달 닫기
      refreshComments();
    } catch (error) {
      console.error("댓글 수정 에러:", error);
      alert("댓글 수정에 실패했습니다.");
    }
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (modalRef.current && !modalRef.current.contains(event.target)) {
        onClose(); // 모달 바깥 클릭 시 onClose 함수 실행
      }
    };

    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside); // 클린업 함수에서 이벤트 리스너 제거
    };
  }, []);

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
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center ">
      <div ref={modalRef} className="bg-white p-5 rounded w-[300px] h-[320px]">
        <form onSubmit={handleSubmit}>
          <div className="flex items-center justify-between my-2">
            <div className="flex itemx-center">
              <p className="text-gray-500 font-semibold text-sm mx-1 mt-1">
                평점
              </p>
              <div className="flex items-center">
                {[...Array(5)].map((_, index) => {
                  const ratingValue = index + 1;
                  return (
                    <div
                      key={index}
                      className={`small-star cursor-pointer ${
                        ratingValue <= (hover || rating)
                          ? "text-sky-400"
                          : "text-gray-400"
                      }`}
                      onMouseEnter={() => handleMouseEnter(ratingValue)}
                      onMouseLeave={handleMouseLeave}
                      onClick={() => handleClick(ratingValue)}
                    >
                      &#9733;
                    </div>
                  );
                })}
              </div>
            </div>
            <button
              type="submit"
              className="bg-white border border-sky-300 hover:bg-sky-300 hover:text-white rounded-lg text-sky-500 text-xs py-2 px-4 transition-colors duration-300"
            >
              등록
            </button>
          </div>
          <div className="bg-sky-200 rounded-lg p-2">
            <textarea
              className="customFont w-full h-[200px] p-2 border rounded resize-none text-xs text-gray-600"
              value={comment}
              onChange={(e) => setComment(e.target.value)}
              maxLength={120}
            ></textarea>
          </div>
        </form>
      </div>
    </div>
  );
};

export default UpdateCommentModal;
