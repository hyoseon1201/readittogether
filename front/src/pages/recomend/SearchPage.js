import React, { useState, useEffect } from "react";
import { useSearchParams, Link } from "react-router-dom";
import SearchForm from "../../api/book/SearchForm";

const SearchPage = () => {
  const [searchParams] = useSearchParams();
  const query = searchParams.get("query");
  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [bookInfo, setBookInfo] = useState([]);

  useEffect(() => {
    if (page === 1) setBookInfo([]);

    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await SearchForm(query, page);
        setBookInfo((prevBookInfo) => [
          ...prevBookInfo,
          ...response.data.books,
        ]);
      } catch (error) {
        console.error("Error fetching data:", error);
      } finally {
        setLoading(false);
      }
    };

    if (query) fetchData();
  }, [query, page]);

  useEffect(() => {
    // 새로운 쿼리가 시작되면 bookInfo 초기화 및 페이지를 1로 재설정
    setBookInfo([]);
  }, [query]);

  useEffect(() => {
    const handleScroll = () => {
      const scrollHeight = document.documentElement.scrollHeight;
      const scrollTop = document.documentElement.scrollTop;
      const clientHeight = document.documentElement.clientHeight;

      if (scrollTop + clientHeight >= scrollHeight && !loading) {
        setPage((prevPage) => prevPage + 1);
      }
    };

    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, [loading]); // 로딩 상태에 따라 핸들러 재등록

  return (
    <div className="pt-16">
      <div className="flex pl-20 mb-8 w-full bg-sky-100 font-semibold text-lg py-1">
        "{query}"의 검색결과{" "}
        {loading && <div className="text-md ml-2">Loading...</div>}
      </div>
      <div className="mx-200 flex">
        <div className="w-1/2">
          {bookInfo.length > 0 ? (
            bookInfo
              .filter((_, index) => index % 2 === 0)
              .map((result, index) => (
                <Link
                  to={`/detail-book/${result.bookId}`}
                  key={index}
                  className="flex items-center mb-6 pb-6 mx-20 border-b-2 border-gray-200"
                >
                  <img
                    className="w-[100px] h-[150px] mr-2 ml-10"
                    src={result.cover}
                    alt="책 표지"
                  />
                  <div>
                    <h3 className="font-bold text-xl overflow-hidden whitespace-nowrap text-ellipsis">
                      {result.title}
                    </h3>
                    <p className="text-sm font-semibold text-gray-500">
                      {result.pubDate.split("년")[0]} · {result.author}
                    </p>
                  </div>
                </Link>
              ))
          ) : (
            <div className="pl-20 w-full">
              "{query}"의 검색 결과가 없습니다.
            </div>
          )}
        </div>
        <div className="w-1/2">
          {bookInfo.length > 0 &&
            bookInfo
              .filter((_, index) => index % 2 !== 0)
              .map((result, index) => (
                <Link
                  to={`/detail-book/${result.bookId}`}
                  key={index}
                  className="flex items-center mb-6 pb-6 mx-20 border-b-2 border-gray-200"
                >
                  <img
                    className="w-[100px] h-[150px] mr-2 ml-10"
                    src={result.cover}
                    alt="책 표지"
                  />
                  <div>
                    <h3 className="font-bold text-xl overflow-hidden whitespace-nowrap text-ellipsis">
                      {result.title}
                    </h3>
                    <p className="text-sm font-semibold text-gray-500">
                      {result.pubDate.split("년")[0]} · {result.author}
                    </p>
                  </div>
                </Link>
              ))}
        </div>
      </div>
    </div>
  );
};

export default SearchPage;
