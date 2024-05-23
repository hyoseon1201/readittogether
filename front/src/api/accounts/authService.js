import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const handleOauthLogin = async (code, stateValue, provider) => {
  const formData = new FormData();
  formData.append("code", code);
  formData.append("state", stateValue);
  try {
    const response = await axios.post(
      `${API_BASE_URL}/oauth-login/${provider}-login`,
      formData,
      {
        withCredentials: true,
      }
    );
    console.log(`${provider} Login Success:`, response);
    return response.data;
    
  } catch (error) {
    console.error(`Error during ${provider} Login:`, error);
    throw error;
  }
};

const getSessionState = async () => {
  try {
    const response = await axios.post(
      `${API_BASE_URL}/oauth-login/session-state`,
      null,
      {
        withCredentials: true,
      }
    );
    console.log(response);
    return response.data.stateValue;
  } catch (error) {
    console.log("Error:", error);
    throw error;
  }
};

export { handleOauthLogin, getSessionState };
