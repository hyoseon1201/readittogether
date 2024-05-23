import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const disable = async (password) => {
  const requestBody = {
    password: password,
  };

  const accessToken = localStorage.getItem("accessToken");

  try {
    const response = await axios.post(
      `${API_BASE_URL}/members/disable`,
      requestBody,
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );

    // 탈퇴 성공 시 로그아웃
    localStorage.removeItem(`memberId`);
    localStorage.removeItem(`accessToken`);
    localStorage.removeItem(`nickname`);
    localStorage.removeItem(`profileImage`);
    localStorage.removeItem(`myEmail`);

    console.log(requestBody);
    return response;
  } catch (error) {
    console.log(error);
    throw error;
  }
};

export default disable;
