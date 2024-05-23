import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const handleSignUp = async (name, email, password, nickname, birth, gender) => {
  const formData = new FormData();
  formData.append("name", name);
  formData.append("email", email);
  formData.append("password", password);
  formData.append("nickname", nickname);
  formData.append("birth", birth);
  formData.append("gender", gender);
  try {
    const response = await axios.post(
      `${API_BASE_URL}/members/signup`,
      formData
      , {
        headers: {
          'Content-Type': 'application/json'
        }
      }
      )
      console.log(response.data);

  } catch (error) {
    console.error(error);
    throw error;
  }
}

export { handleSignUp };