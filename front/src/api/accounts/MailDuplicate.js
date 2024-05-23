import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const checkEmailDuplicate = async (email) => {
  try {
    const response = await axios.post(
    `${API_BASE_URL}/members/email`, 
    {email: email},
    {
      headers: {
        'Content-Type': 'application/json'
      }
    });
    console.log('이메일 중복검사')
    console.log(response.data)
    return response.data;
  } catch (error) {
    console.error(error.response.data.message)
    return false; // 에러가 발생했을 경우 중복으로 처리
  };
}

export { checkEmailDuplicate };