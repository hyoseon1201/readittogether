import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;
const accessToken = localStorage.getItem("accessToken");
const GuestBookListGet = async (toMemberId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/guestbook/list/${toMemberId}`, {
      withCredentials: true,
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
    console.log("리스트 드가자", response.data.data.guestbookList);
    return response.data.data.guestbookList;
  } catch (error) {
    console.error("리스트 못줌", error);
  }
};

export default GuestBookListGet;
