import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const UpdateBook = async (bookId) => {
  const requestBody = {
    bookId: bookId,
  }

  const accessToken = localStorage.getItem("accessToken");
  
  try {
    const response = await axios.patch(
      `${API_BASE_URL}/bookshelf/update`,
      requestBody,
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${accessToken}`
        },
      }
    )
    alert('이동되었습니다')
    console.log(response);
    return response;
  } catch (error) {
    console.error(error);
  }
}

export default UpdateBook;