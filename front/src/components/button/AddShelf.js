import React, { useState } from 'react';

import AddToShelf from '../../api/book/AddToShelf';

// AddShelf 컴포넌트 정의
const AddShelf = ({ bookId }) => {
  // 책장 추가 모달
  const [isAddShelfOpen, setIsAddShelfOpen] = useState(false);
  const openAddShelfModal = () => setIsAddShelfOpen(true);
  const closeAddShelfModal = () => setIsAddShelfOpen(false);

  // 책장에 넣는 로직 추가
  const handleAddToShelf = async (isRead) => {
    try {
      // 책장에 추가
      await AddToShelf(bookId, isRead);
      // 성공적으로 추가되면 모달을 닫음
      closeAddShelfModal();
    } catch (error) {
      console.log("책장에 추가하는 데 실패했습니다:", error);
    }
  };

  return (
    <div>
      <button
        className="w-28 bg-sky-300 hover:bg-sky-500 text-white text-xs font-bold py-2 rounded"
        onClick={openAddShelfModal}
      >
        내 책장 등록하기
      </button>
      {isAddShelfOpen && (
        <div className="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50" onClick={closeAddShelfModal}>
        <div className="modal p-4 bg-white rounded-lg" onClick={(e) => e.stopPropagation()}>
          {/* <span className="top-2 right-2 cursor-pointer" onClick={closeAddShelfModal}>
            &times;
          </span> */}
          <div className="flex justify-center">
            <button className="bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded flex items-center mr-4" onClick={() => handleAddToShelf(1)}>
              <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
              </svg>
              <div>
                <p className="font-bold">읽은책</p>
                <span>이 책을 읽으셨나요?</span>
              </div>
            </button>
            <button className="bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded flex items-center mr-4" onClick={() => handleAddToShelf(0)}>
              <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
              </svg>
              <div>
                <p className="font-bold">읽을책</p>
                <span>이 책을 아직 안 읽으셨나요?</span>
              </div>
            </button>
          </div>
        </div>
      </div>
      )}
    </div>
  );
};

export default AddShelf;
