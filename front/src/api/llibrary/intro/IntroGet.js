import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

// 방명록 조회 함수
const accessToken = localStorage.getItem("accessToken");
const IntroGet = async (memberId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/library/${memberId}`, {
      withCredentials: true,
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
    console.log(response.data.data);
    return response.data.data;
  } catch (error) {
    console.error("소개글을 불러오는 데 실패했습니다:", error);
  }
};

export default IntroGet;
