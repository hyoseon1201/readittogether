import axios from "axios";
const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const CommentForm = async (bookId, comment, rating) => {
  const accessToken = localStorage.getItem("accessToken");

  try {
    const response = await axios.post(
      `${API_BASE_URL}/books/comment`,
      {
        bookId,
        comment,
        rating,
      },
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );
    console.log("작성 성공 책 번호:", bookId,"코멘트:", comment,"점수:", rating);
    return response.data;
  } catch (error) {
    if (error.response && error.response.status === 400) {
      alert("한 게시글에 하나의 평가만 작성할 수 있습니다.");
    } else {
      console.error(error);
      return null; // 또는 다른 에러 처리 로직을 넣을 수 있음
    }
  }
};

export default CommentForm;
