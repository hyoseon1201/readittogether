import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const changeNickname = async (newNickname) => {
  const requestBody = {
    newNickname: newNickname
  }
  
  const accessToken = localStorage.getItem('accessToken');

  try {
    const response = await axios.put(
      `${API_BASE_URL}/members/nickname`,
      requestBody,
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${accessToken}`
        },
      }
    )
    console.log(requestBody);
    // console.log(response.data);
    return response;
  } catch (error) {
    console.error("닉네임 변경 실패", error);
    throw error;
  }
}

export default changeNickname;