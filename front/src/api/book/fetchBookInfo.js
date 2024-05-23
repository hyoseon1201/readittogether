import BookDetail from "./BookDetail";

const fetchBookInfo = async (bookId) => {
  try {
    const response = await BookDetail(bookId);
    const { title, cover, author, info, pubDate, publisher, page, rating, reviewerCnt, isbn, genres, commentListResponseDtos } = response.data;
    return {
      title,
      cover,
      author,
      info,
      pubDate,
      publisher,
      page,
      rating,
      reviewerCnt,
      isbn,
      genres,
      commentListResponseDtos
    };
  } catch (error) {
    throw new Error("도서 정보를 가져오는 데 실패했습니다.");
  }
}

export default fetchBookInfo;