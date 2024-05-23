import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import BookDetail from "../../api/book/BookDetail";

import AddShelf from "../../components/button/AddShelf";
import CreateCard from "../../components/button/CreateCard";
import CreateComments from "../../components/comments/CreateComment";
import Comments from "../../components/comments/Comments";

const DetailBook = () => {
  const { bookId } = useParams();
  const [commentList, setCommentList] = useState([]);
  const [bookInfo, setBookInfo] = useState({
    title: "",
    cover: "",
    author: "",
    info: "",
    pubDate: "",
    publisher: "",
    page: "",
    rating: "",
    reviewerCnt: "",
    isbn: "",
    genres: [],
    commentListResponseDtos: [],
  });

  const fetchBookInfo = async () => {
    try {
      const response = await BookDetail(bookId); // BookDetail 함수를 통해 도서 정보를 가져옴
      setBookInfo(response.data); // 가져온 도서 정보를 상태에 설정
      setCommentList(response.data.commentListResponseDtos);
    } catch (error) {
      console.error("책 정보를 가져오는 데 실패했습니다:", error);
    }
  };
  useEffect(() => {
    console.log(commentList)
  }, [commentList])

  useEffect(() => {
    fetchBookInfo(); // useEffect에서 한 번만 호출하도록 설정
  }, [bookId]);

  // 데이터의 장르명 표시 바꾸기
  const mapGenreToDisplayName = (genre) => {
    const genreMapping = {
      action: "액션",
      horror: "호러",
      mystery: "미스터리",
      fantasy: "판타지",
      history: "역사",
      romantic: "로맨스",
      sf: "SF",
      kr_long: "한국문학",
      kr_short: "한국단편",
      en_short: "영미단편",
      en_long: "영미문학",
      jp_short: "일본단편",
      jp_long: "일본문학",
      china: "중국문학",
      spain: "스페인문학",
      north: "북유럽문학",
      latin: "라틴문학",
      russia: "러시아문학",
      east: "동유럽문학",
      german: "독일문학",
      france: "프랑스문학",
    };

    return genreMapping[genre] || genre; // 해당하는 표시 이름이 없으면 기본적으로는 장르를 그대로 반환
  };

  // 장르는 배열 형태이므로 map 사용하고 장르가 2개 이상일 경우 ', '로 나눔
  const genreDisplayNames = bookInfo.genres
    .map((genre) => mapGenreToDisplayName(genre))
    .join(", ");

  return (
    <div>
      <div className="flex flex-wrap justify-center px-44 py-20">
        {/* 책표지 */}
        <img
          className="w-[400px] h-[580px] rounded-lg"
          src={bookInfo.cover}
          alt=""
        />
        <div className="flex-col text-gray-600 font-bold pl-10 ">
          <div className="flex justify-between">
            <div>
              {/* 책 제목 */}
              <h1 className="text-3xl w-full font-bold mb-4">
                {bookInfo.title}
              </h1>
              {/* 작가명 · 장르 */}
              <p>
                {bookInfo.author} · {genreDisplayNames}
              </p>
              {/* 출판사 · 출판일 */}
              <p>
                {bookInfo.publisher} · {bookInfo.pubDate}
              </p>
              {/* 페이지 수 */}
              <p>
                {bookInfo.page}쪽 · 평점 {bookInfo.rating}점
              </p>
            </div>
            <div className="flex-col">
              <div className="mb-2">
                {/* 책장 추가 버튼 + 모달 */}
                <AddShelf bookId={bookId} />
              </div>
              <div className="mb-2">
                {/* 카드 작성 버튼 + 모달 */}
                <CreateCard
                  bookId={bookId}
                  cover={bookInfo.cover}
                  title={bookInfo.title}
                  author={bookInfo.author}
                  publisher={bookInfo.publisher}
                />
              </div>
            </div>
          </div>

          <p className="text-2xl mt-10 mb-2">책 소개</p>
          <p className="max-w-2xl text-gray-700 text-sm w-[500px] h-[180px] overflow-y-auto">
            {bookInfo.info.split("<br/>").map((line, index) => (
              <React.Fragment key={index}>
                {line}
                <br />
              </React.Fragment>
            ))}
          </p>

          <CreateComments bookId={bookId} refreshComments={fetchBookInfo} />
        </div>
      </div>
      <Comments
        bookId={bookId}
        refreshComments={fetchBookInfo}
        commentList={commentList}
      />
    </div>
  );
};

export default DetailBook;
