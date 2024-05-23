import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const FindPWForm = async (name, email) => {
  try {
    // API 요청 보내기 (FormData 사용)
    const response = await axios.post(
      `${API_BASE_URL}/members/temporary-password`,
      { name, email },
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    // 요청 성공 시 응답 데이터 반환
    console.log("임시 비밀번호 발급 성공:", response.data);
    return response.data;
  } catch (error) {
    // 요청 실패 시 에러 처리
    console.error("임시 비밀번호 발급 실패:", error);
    throw error;
  }
};

export default FindPWForm;
