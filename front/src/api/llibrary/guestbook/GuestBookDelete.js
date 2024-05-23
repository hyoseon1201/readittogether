import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;
const accessToken = localStorage.getItem("accessToken");

const GuestBookDelete = async (postId) => {
  try {
    const response = await axios.delete(`${API_BASE_URL}/guestbook/${postId}`, {
      withCredentials: true,
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error(error);
  }
};

export default GuestBookDelete;
