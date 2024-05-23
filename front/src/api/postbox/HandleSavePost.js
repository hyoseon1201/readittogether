import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const handleSavePost = async (cardId) => {
  const requestBody = {
    cardId: cardId
  }

  const accessToken = localStorage.getItem("accessToken");

  try {
    const response = await axios.post(
      `${API_BASE_URL}/postbox/save`,
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
    // alert('카드를 저장했습니다.')
    return response;
  } catch (error) {
    console.error(error);
    // alert('카드 저장에 실패했습니다.')
    throw error;
  }
}

export default handleSavePost;