import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const handleSentCard = async (bookId, comment) => {
  try {
    const requestBody = {
      bookId: bookId,
      comment: comment
    }
    const accessToken = localStorage.getItem("accessToken");

    const response = await axios.post(
      `${API_BASE_URL}/card/create`,
      requestBody,
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${accessToken}`
        }
      }
    );
    console.log(response);
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export default handleSentCard;