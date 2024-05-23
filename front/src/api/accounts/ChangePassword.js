import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const changePassword = async (oldPassword, newPassword) => {
  const requestBody = {
    oldPassword: oldPassword,
    newPassword: newPassword,
  };

  const accessToken = localStorage.getItem("accessToken");

  try {
    const response = await axios.put(
      `${API_BASE_URL}/members/password`,
      requestBody,
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );

    console.log(requestBody);
    // console.log(response.data);
    return response;
  } catch (error) {
    console.error("비밀번호 변경 실패", error);
    throw error;
  }
};

export default changePassword;
