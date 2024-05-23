import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;
const accessToken = localStorage.getItem("accessToken");
const FollowDelete = async (targetEmail, requestId) => {
  try {
    const response = await axios.post(
      `${API_BASE_URL}/members/unfollow/${targetEmail}`,
      { requestId },
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );
    console.log(response.data);
    return response.data;
  } catch (error) {
    return null;
  }
};

export default FollowDelete;
