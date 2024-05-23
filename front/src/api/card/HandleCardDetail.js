import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const handleCardDetail = async (cardId) => {
  const accessToken = localStorage.getItem("accessToken");

  try {
    const response = await axios.get(
      `${API_BASE_URL}/card/detail/${cardId}`,
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${accessToken}`
        }
      }
    )
    return response.data.data;
  } catch (error) {
    console.error(error);
  }
}

export default handleCardDetail;