import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const handleSaveCard = (cardId) => {
  const requestBody = {
    cardId: cardId
  }

  const accessToken = localStorage.getItem("accessToken");

  try {
    const response = axios.post(
      `${API_BASE_URL}/postbox/save`,
      requestBody,
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${accessToken}`
        }
      }
    )
    console.log(response);
    alert('카드를 저장했습니다.')
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export default handleSaveCard;