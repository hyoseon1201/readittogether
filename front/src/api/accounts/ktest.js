import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const handleKakaoGet = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/ktest`, {
      withCredentials: true,

      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    console.log(`소셜로긴 성공:`, response);
    return response.data;
  } catch (error) {
    console.error(`실패:`, error);
    throw error;
  }
};

export default handleKakaoGet;
