import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const handleCardDelete = (cardId) => {

  const accessToken = localStorage.getItem("accessToken");

  try {
    const response = axios.delete(
      `${API_BASE_URL}/card/delete`,
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${accessToken}`
        },
        data: { cardId }
      }
    )
    console.log(response);
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export default handleCardDelete;