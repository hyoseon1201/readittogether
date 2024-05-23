import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const changeImage = async (upload, nickname) => {
  const formData = new FormData();
  formData.append("upload", upload);
  formData.append("nickname", nickname);

  const accessToken = localStorage.getItem('accessToken');

  try {
    const response = await axios.put(
      `${API_BASE_URL}/members/profile`,
      formData,
      {
        withCredentials: true,
        headers: {
          'Content-Type': 'multipart/form-data',
          "Authorization": `Bearer ${accessToken}`
        },
      }
    )
    console.log(response);
    return response.data
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export { changeImage };