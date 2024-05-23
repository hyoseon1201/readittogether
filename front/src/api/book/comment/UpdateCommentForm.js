import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;
const accessToken = localStorage.getItem("accessToken");
const UpdateComentForm = async (bookId, commentId, comment, rating) => {
  try {
    const response = await axios.patch(
      `${API_BASE_URL}/books/comment`,
      { bookId, commentId, comment, rating },
      {
        withCredentials: true,
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );
    console.log("댓글 삭제 성공 ㅋ", response);
    return response.data;
  } catch (error) {
    console.error(bookId, commentId, comment, rating);
  }
};

export default UpdateComentForm;
