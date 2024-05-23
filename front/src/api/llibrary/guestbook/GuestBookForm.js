import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;
const accessToken = localStorage.getItem("accessToken")
const GuestBookForm = async (toMemberId, content) => {
  try {
    const response = await axios.post(
      `${API_BASE_URL}/guestbook/${toMemberId}`,
      { content },
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${accessToken}`
        },
      }
    );
    console.log(response.data);
    return response.data.data;
  } catch (error) {
    console.error(error);
  }
};

export default GuestBookForm;
