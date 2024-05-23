import React, { useState, useEffect } from 'react';

const BookSort = ({ onChange }) => {
  const [selectedSortOption, setSelectedSortOption] = useState(0);
  const handleSortChange = async (e) => {
    const sortOption = e.target.value;
    setSelectedSortOption(sortOption);
    onChange(sortOption); // 선택된 정렬 옵션 값을 상위 컴포넌트로 전달
  };
  return (
    <div>
      <select value={selectedSortOption} onChange={handleSortChange} className="p-1 font-bold rounded-lg">
      <option value="0">등록순</option>
        <option value="1">평점순</option>
        <option value="2">제목순</option>
      </select>
    
    </div>
    );
  };

export default BookSort;
