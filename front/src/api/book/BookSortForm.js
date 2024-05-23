import axios from 'axios';

const BookSortForm = async (sortOption, orderOption) => {
  const API_URL = process.env.REACT_APP_API_BASE_URL; // 환경 변수에서 API 기본 URL을 가져옴

  const formData = new FormData();
  formData.append('sort', sortOption); // sortOption을 FormData에 추가
  formData.append('order', orderOption); // orderOption을 FormData에 추가

  try {
    // axios.post를 사용하여 FormData와 함께 POST 요청을 보냄
    const response = await axios.post(`${API_URL}/members/book-sort`, formData, {
      // withCredentials: true, 
      // 쿠키 등의 인증 정보를 요청과 함께 보내기 위한 옵션
    });

    return response.data; // 서버로부터 받은 정렬된 책 목록 반환
  } catch (error) {
    console.error('책 목록을 가져오는데 실패했습니다:', error);
    return []; // 오류 발생 시 빈 배열 반환
  }
};

export default BookSortForm;