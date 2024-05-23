import React, { useState, useEffect, useRef } from "react";
import { Link } from "react-router-dom";
import "../../App.css";
import RecommendGet from "../../api/book/RecommendGet";
import left from "../../assets/book/left.png";
import right from "../../assets/book/right.png";

const RecommendBook = () => {
  const constraintsRef = useRef(null);
  const [loading, setLoading] = useState(true);
  const [indices, setIndices] = useState({});
  const [recommendList, setRecommendList] = useState({
    memberRecommendBooks: [],
    contentBaseGroupRecommendBooks: [],
    ageAndGenderGroupRecommendBooks: [],
    bestGroupRecommendBooks: [],
  });

  const keys = [
    "memberRecommendBooks",
    "contentBaseGroupRecommendBooks",
    "ageAndGenderGroupRecommendBooks",
    "bestGroupRecommendBooks",
  ];
  const header = [
    "개인별 추천 도서",
    "콘텐츠 기반 추천 도서",
    "연령 및 성별 그룹 추천 도서",
    "베스트셀러 순위",
  ];

  useEffect(() => {
    const fetchRecommendList = async () => {
      try {
        const response = await RecommendGet();
        setRecommendList({
          ageAndGenderGroupRecommendBooks:
            response.ageAndGenderGroupRecommendBooks || [],
          bestGroupRecommendBooks: response.bestGroupRecommendBooks || [],
          memberRecommendBooks: response.memberRecommendBooks || [],
          contentBaseGroupRecommendBooks:
            response.contentBaseGroupRecommendBooks || [],
        });
        setIndices(keys.reduce((acc, key) => ({ ...acc, [key]: 0 }), {}));
      } catch (error) {
        console.log(error);
      } finally {
        setLoading(false);
      }
    };
    fetchRecommendList();
  }, []);

  const moveRight = (key) => {
    setIndices((prevIndices) => {
      const newIndices = { ...prevIndices };
      const maxIndex = Math.max(0, recommendList[key].length - 5);
      newIndices[key] = Math.min(prevIndices[key] + 5, maxIndex);
      return newIndices;
    });
  };

  const moveLeft = (key) => {
    setIndices((prevIndices) => {
      const newIndices = { ...prevIndices };
      newIndices[key] = Math.max(prevIndices[key] - 5, 0);
      return newIndices;
    });
  };

  return (
    <div className="flex flex-col p-20 relative">
      {loading ? (
        <div>Loading...</div>
      ) : (
        <>
          {keys.map((key, i) => {
            const list = recommendList[key];
            return list.length > 0 ? (
              <div key={i}>
                <p className="mb-4 pl-4 text-2xl font-black">{header[i]}</p>
                <div className="flex items-center space-x-2 slider relative mb-8">
                  {indices[key] > 0 && (
                    <button
                      onClick={() => moveLeft(key)}
                      className="slides border-2 rounded-full absolute left-6 transform -translate-x-1/2 z-10"
                      style={{ top: "50%", transform: "translate(-50%, -50%)" }}
                    >
                      <img
                        className="w-10 rounded-full"
                        src={left}
                        alt="왼쪽"
                      />
                    </button>
                  )}
                  <div className="flex overflow-hidden" ref={constraintsRef}>
                    {recommendList[key].map((book, j) => (
                      <div
                        key={j}
                        className={`flex flex-col items-start mr-2 ${
                          j < indices[key] || j >= indices[key] + 5
                            ? "hidden"
                            : ""
                        }`}
                      >
                        <Link to={`/detail-book/${book.bookId}`}>
                          <div className="relative border-2">
                            <img
                              src={book.cover}
                              alt={`book-${book.bookId}`}
                              className="w-[300px] h-[400px] object-cover"
                            />
                            <div className="flex items-center justify-center w-[30px] absolute top-1 left-1 rounded-md bg-black opacity-70 p-1">
                              <p className="font-bold text-white opacity-100">
                                {indices[key] + j - indices[key] + 1}
                              </p>
                            </div>
                          </div>
                          <div className="text-sm font-black mt-2 text-left">
                            {book.title}
                          </div>
                        </Link>
                      </div>
                    ))}
                  </div>
                  {indices[key] < recommendList[key].length - 5 && (
                    <button
                      onClick={() => moveRight(key)}
                      className="slides border-2 rounded-full absolute right-6 transform translate-x-1/2"
                      style={{ top: "50%", transform: "translate(50%, -50%)" }}
                    >
                      <img
                        className="w-10 rounded-full"
                        src={right}
                        alt="오른쪽"
                      />
                    </button>
                  )}
                </div>
              </div>
            ) : null;
          })}
        </>
      )}
    </div>
  );
};

export default RecommendBook;
