import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;
const accessToken = localStorage.getItem("accessToken");

const SearchForm = async (q, page) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/search`, {
      params: {
        q,
        page,
      },
      withCredentials: true,
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${accessToken}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error("검색 에러 발생:", error);
    throw error;
  }
};

export default SearchForm;
