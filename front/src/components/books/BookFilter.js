import React, { useState } from "react";

const genres = [
  { key: "action", label: "액션" },
  { key: "horror", label: "호러" },
  { key: "mystery", label: "미스터리" },
  { key: "fantasy", label: "판타지" },
  { key: "history", label: "역사" },
  { key: "romantic", label: "로맨스" },
  { key: "sf", label: "SF" },
  { key: "kr_long", label: "한국문학" },
  { key: "kr_short", label: "한국단편" },
  { key: "en_long", label: "영미문학" },
  { key: "en_short", label: "영미단편" },
  { key: "jp_long", label: "일본문학" },
  { key: "jp_short", label: "일본단편" },
  { key: "china", label: "중국문학" },
  { key: "spain", label: "스페인문학" },
  { key: "north", label: "북유럽문학" },
  { key: "latin", label: "라틴문학" },
  { key: "russia", label: "러시아문학" },
  { key: "east", label: "동유럽문학" },
  { key: "german", label: "독일문학" },
  { key: "france", label: "프랑스문학" }
];

const BookFilter = ({ onFilterChange }) => {
  const [selectedGenres, setSelectedGenres] = useState([]);

  const handleGenreChange = (event) => {
    const { value, checked } = event.target;
    const updatedGenres = checked
      ? [...selectedGenres, value]
      : selectedGenres.filter(genre => genre !== value);
    
    console.log(updatedGenres);
    setSelectedGenres(updatedGenres);
    onFilterChange(updatedGenres);
  };

  return (
    <div className="grid lg:grid-cols-7 sm:grid-cols-4 gap-4 p-4 bg-white">
      {genres.map((genre) => (
        <label key={genre.key} className="flex items-center space-x-2 cursor-pointer">
          <input
            type="checkbox"
            value={genre.key}
            onChange={handleGenreChange}
            checked={selectedGenres.includes(genre.key)}
            className="form-checkbox h-4 w-4 text-indigo-600 transition duration-150 ease-in-out"
          />
          <span>{genre.label}</span>
        </label>
      ))}
    </div>
  );
};

export default BookFilter;
