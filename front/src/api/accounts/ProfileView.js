import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const profileView = async () => {
  const accessToken = localStorage.getItem("accessToken");

  try {
    const response = await axios.get(`${API_BASE_URL}/members/profile`, {
      withCredentials: true,
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${accessToken}`,
      },
    });
    return response;
  } catch (error) {
    console.log(error);
    throw error;
  }
};

export default profileView;
