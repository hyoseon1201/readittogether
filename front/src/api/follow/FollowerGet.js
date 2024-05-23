import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;
const accessToken = localStorage.getItem("accessToken");
const FollowerGet = async (email) => {
  try {
    const response = await axios.get(
      `${API_BASE_URL}/members/follower/${email}`,
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error(error);
  }
};

export default FollowerGet;
