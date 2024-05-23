import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const sendCode = async (email) => {
  const requestBody = {
    email: email
  }

  try {
    const response = await axios.post(
      `${API_BASE_URL}/members/send-certification`,
      requestBody,
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    console.log(response);
    // alert('받은 편지함을 확인해주세요');
    return response;
  } catch (error) {
    console.error(error);
  }
}

export default sendCode;