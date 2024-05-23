import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const handleGetPost = async () => {
  const accessToken = localStorage.getItem("accessToken");

  try {
    const response = await axios.get(
      `${API_BASE_URL}/postbox/list`,
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${accessToken}`
        }
      }
    );
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export default handleGetPost;