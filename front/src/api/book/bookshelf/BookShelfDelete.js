import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const deleteBookShelf = async (bookshelfId) => {
    const accessToken = localStorage.getItem("accessToken");
  
    try {
      const response = await axios.delete(`${API_BASE_URL}/bookshelf/${bookshelfId}`, {
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${accessToken}`
        },
        withCredentials: true
      });
      console.log("Delete response:", response);
      return response;
    } catch (error) {
      console.error("Error deleting bookshelf entry:", error);
    }
};

export default deleteBookShelf;
