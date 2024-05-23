import React, { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import GetBookShelfList from "../../api/book/bookshelf/GetBookshelfList";

import BookFilter from "../../components/books/BookFilter";
import BookSort from "../../components/books/BookSort";
import Read from "../../components/books/Read";
import NotRead from "../../components/books/NotRead";
import updateBookshelf from "../../api/book/bookshelf/BookShelfupdate";
import deleteBookshelf from "../../api/book/bookshelf/BookShelfDelete";

const Bookshelf = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [bookshelfInfo, setBookshelfInfo] = useState([]);
  const [page, setPage] = useState(0);
  const [isLoading, setIsLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const [isRead, setIsRead] = useState(false);
  const memberId = location.pathname.split("/").pop();
  const [selectedGenres, setSelectedGenres] = useState([]);
  const [sortOption, setSortOption] = useState(0);

  const handleSortChange = (option) => {
    const sortOptionInt = parseInt(option, 10);
    setSortOption(sortOptionInt);
    setPage(0);
    setBookshelfInfo([]);
    setHasMore(true);
  };

  const fetchBookshelfData = async () => {
    console.log("잘되나0?;");
    if (isLoading || !hasMore) return;
    setIsLoading(true);
    try {
      console.log("잘되나?");
      const response = await GetBookShelfList(
        memberId,
        page,
        10000,
        sortOption
      );
      const newData = response.data.data;
      console.log("잘 패치됨");
      const nonDuplicateData = newData.filter(
        (newItem) =>
          !bookshelfInfo.some((item) => item.bookId === newItem.bookId)
      );
      if (nonDuplicateData.length === 0) {
        setHasMore(false);
      }
      setBookshelfInfo((prev) => [...prev, ...nonDuplicateData]);
      setPage((prevPage) => prevPage + 1);
    } catch (error) {
      console.error("Failed to fetch bookshelf data:", error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchBookshelfData();
  }, [memberId, page, isLoading, hasMore, sortOption]);

  const handleDeleteBookshelf = async (bookshelfId) => {
    try {
      await deleteBookshelf(bookshelfId);
      const updatedBookshelf = bookshelfInfo.filter(
        (book) => book.bookshelfId !== bookshelfId
      );
      setBookshelfInfo(updatedBookshelf);
      console.log("삭제요카.");
    } catch (error) {
      console.error(error);
    }
  };

  const handleUpdateBookshelf = async (bookId) => {
    try {
      const response = await updateBookshelf(bookId);
      console.log(response.data.data);
      if (response.data.data) {
        setBookshelfInfo((prevBooks) =>
          prevBooks.map((book) =>
            book.bookId === bookId ? { ...book, isRead: !book.isRead } : book
          )
        );
        console.log(response.data.message); // 응답 메시지 로깅
      }
    } catch (error) {
      console.error("Error updating bookshelf entry:", error);
    }
  };

  const handleFilterChange = (selectedKeys) => {
    setSelectedGenres(selectedKeys);
  };

  const filteredBooks = bookshelfInfo.filter(
    (book) =>
      selectedGenres.length === 0 ||
      book.genres.some((genre) => selectedGenres.includes(genre))
  );

  useEffect(() => {
    const handleScroll = () => {
      if (
        window.innerHeight + document.documentElement.scrollTop + 1 >=
        document.documentElement.scrollHeight
      ) {
        setIsLoading(true);
      }
    };

    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  const toggleRead = () => {
    setIsRead(!isRead);
  };

  const readOrNotText = isRead ? "읽은 책 목록" : "읽을 책 목록";

  return (
    <div className="p-20 min-w-[900px] overflow-auto">
      <div className="bg-rose-50 px-4 pb-10">
        <div className="flex items-center justify-center my-2">
          <BookFilter onFilterChange={handleFilterChange} />
        </div>
        <div className="flex justify-end mx-5 my-3">
          <BookSort onChange={handleSortChange} />
          <div className="ml-4">
            <label className="flex items-center cursor-pointer">
              <div className="relative">
                <input
                  type="checkbox"
                  className="sr-only"
                  checked={isRead}
                  onChange={toggleRead}
                />
                <div
                  className="block w-14 h-8 rounded-full"
                  style={{ backgroundColor: isRead ? "#4ade80" : "#60a5fa" }}
                ></div>
                <div
                  className="dot absolute left-1 top-1 bg-white w-6 h-6 rounded-full transition-transform duration-300 ease-in-out"
                  style={{
                    transform: isRead ? "translateX(100%)" : "translateX(0)",
                  }}
                ></div>
              </div>
              <div className="bg-white ml-4 mr-7 p-2 rounded-lg text-xs font-bold text-gray-900 ">
                {readOrNotText}
              </div>
            </label>
          </div>
        </div>
        <div className="flex my-3 justify-center">
          {isRead ? (
            <Read
              books={filteredBooks.filter((book) => book.isRead)}
              handleClickBook={(bookId) => navigate(`/detail-book/${bookId}`)}
              handleUpdateBookshelf={handleUpdateBookshelf}
              handleDeleteBookshelf={handleDeleteBookshelf}
            />
          ) : (
            <NotRead
              books={filteredBooks.filter((book) => !book.isRead)}
              handleClickBook={(bookId) => navigate(`/detail-book/${bookId}`)}
              handleUpdateBookshelf={handleUpdateBookshelf}
              handleDeleteBookshelf={handleDeleteBookshelf}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default Bookshelf;
