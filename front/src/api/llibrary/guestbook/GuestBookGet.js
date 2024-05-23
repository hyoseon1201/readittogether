import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;
const accessToken = localStorage.getItem("accessToken");
const GuestBookGet = async (postId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/guestbook/${postId}`, {
      withCredentials: true,
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
    console.log("받아라", response.data);
    return response
  } catch (error) {
    console.error(error);
  }
};

export default GuestBookGet;
