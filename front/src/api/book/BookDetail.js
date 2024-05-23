import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const BookDetail = async (bookId, page = 0, size = 8) => {

  if (!bookId) {
    throw new Error("bookId가 유효하지 않습니다.");
  }

  const accessToken = localStorage.getItem("accessToken");
  
  try {
    const response = await axios.get(`${API_BASE_URL}/books/${bookId}`,
    {
      withCredentials: true,
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${accessToken}`
      },
      params: {
        page: page,
        size: size
      }
    });
    return response.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export default BookDetail;