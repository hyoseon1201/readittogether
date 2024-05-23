import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const checkCode = async (email, code) => {
  const requestBody = {
    email: email,
    code: code
  }

  try {
    const response = await axios.post(
      `${API_BASE_URL}/members/pass-certification`,
      requestBody,
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    console.log(response);
    return response.data;
  } catch (error) {
    console.error(error.response.data);
    throw error;
  }
}

export default checkCode;