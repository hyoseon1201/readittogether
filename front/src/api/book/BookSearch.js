// import axios from "axios";

// const BookSearch = async (searchTerm) => {
//   const API_URL = process.env.REACT_APP_API_BASE_URL;
  
//   try {
//     const response = await axios.get(`${API_URL}/books/search`, {
//       params: {
//         q: searchTerm
//       }
//     });

//     return response.data; // 검색된 책 목록 반환
//   } catch (error) {
//     console.error('책 검색에 실패했습니다:', error);
//     return []; // 오류 발생 시 빈 배열 반환
//   }
// };

// export default BookSearch;