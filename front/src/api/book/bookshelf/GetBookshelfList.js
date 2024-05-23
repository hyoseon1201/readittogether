import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const GetBookShelfList = async (toMemberId, page = 0, size = 10000, sort = 0, searchKeyword = null) => {
  // searchKeyword 기본값이 없으면 null(O) ''(X)
  const accessToken = localStorage.getItem("accessToken");

  try {
    const response = await axios.get(
      `${API_BASE_URL}/bookshelf/${toMemberId}`,
      {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${accessToken}`,
          // 캐시를 비활성화하기 위해 Cache-Control 헤더 추가(무한로딩 방지)
          // "Cache-Control": "no-cache"
        },
        params: {
          page: page,
          size: size,
          sort: sort,
          searchKeyword: searchKeyword
        }
      }
    );
    console.log(response.data.data);
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export default GetBookShelfList;