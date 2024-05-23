import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const AddToShelf = async (bookId, isRead) => {
  const requestBody = {
    bookId: bookId,
    isRead: isRead
  }
  
  const accessToken = localStorage.getItem("accessToken");

  try {
    const response = await axios.post(
      `${API_BASE_URL}/bookshelf/upload`,
      requestBody,
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${accessToken}`
        },
      }
    )
    console.log(response);
    alert('등록되었습니다.')
    return response;
  } catch (error) {
    console.log(error);
    alert('이미 등록된 책입니다.');
  }
}

export default AddToShelf;