import axios from "axios";
const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const DeleteComment = (commentId) => {
  const accessToken = localStorage.getItem("accessToken");

  try {
    const response = axios.delete(`${API_BASE_URL}/books/comment/${commentId}`, {
      withCredentials: true,
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${accessToken}`,
      },
    });
    console.log("댓글 삭제 성공 ㅋ", response);
    return response.data;
  } catch (error) {
    console.error(error);
  }
};

export default DeleteComment;
