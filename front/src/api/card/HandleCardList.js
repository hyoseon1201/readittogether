import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const handleCardList = async (page = 0, size = 4) => {
  const accessToken = localStorage.getItem("accessToken");

  try {
    const response = await axios.get(
      `${API_BASE_URL}/card/list`,
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
      }
    );
    return response.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export default handleCardList;