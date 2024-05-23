import React, { useEffect, useState } from 'react';
import handleSentCard from '../../api/card/HandleSendCard';

const CreateCard = ({ bookId, cover, title, author, publisher }) => {
  const [comment, setComment] = useState('');
  const [isValid, setIsValid] = useState(false);
  
  // 카드 작성 모달
  const [isCreateCardOpen, setIsCreateCardOpen] = useState(false);
  const openCreateCardModal = () => setIsCreateCardOpen(true);
  const closeCreateCardModal = () => {
    setIsCreateCardOpen(false);
    setComment(''); // 모달이 닫히면 입력 내용 초기화
  };

  useEffect(() => {
    if (comment.length >= 10) {
      setIsValid(true);
    } else {
      setIsValid(false);
    }
  }, [comment])
  
  const handleSubmit = async () => {
    try {
      await handleSentCard(bookId, comment);
      alert('카드를 보냈습니다.');
      closeCreateCardModal();
    } catch (error) {
      console.error('카드 작성에 실패했습니다', error);
    }
  };

  return (
    <div>
      <button
        className="w-28 bg-sky-300 hover:bg-sky-500 text-white text-xs font-bold py-2 rounded"
        onClick={openCreateCardModal}
      >
        카드 작성하기
      </button>
      {isCreateCardOpen && (
        <div className="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50" onClick={closeCreateCardModal}>
          <div className="modal p-8 bg-white rounded-lg flex flex-col" onClick={(e) => e.stopPropagation()}>
            <div className="grid grid-cols-2 gap-4 mb-4">
              {/* 왼쪽 열 */}
              <div className="col-span-1">
                {/* <img src={img1} alt="cover" className="mb-4 w-48 h-64" /> */}
                <img src={cover} alt="cover" className="mb-4 w-48 h-64" />
              </div>
              {/* 오른쪽 열 */}
              <div className="col-span-1">
                {/* <p className="my-2 text-lg font-bold">제목</p> */}
                <p className="my-2 text-lg font-bold">{title}</p>
                {/* <p className="my-2 text-lg font-bold">작가</p> */}
                <p className="my-2 text-lg font-bold">{author}</p>
                {/* <p className="my-2 text-md font-bold">출판사</p> */}
                <p className="my-2 text-md font-bold">{publisher}</p>
                {/* <input 
                  type="text" 
                  id="comment" 
                  name="comment" 
                  value={comment} 
                  onChange={(e) => setComment(e.target.value)} 
                  className="border border-gray-300 p-2 rounded w-full mb-4" 
                  placeholder="추천사를 10자 이상 입력해주세요" 
                /> */}
                <textarea id='comment' name='comment' placeholder="추천사를 10자 이상 입력해주세요" value={comment} onChange={(e) => setComment(e.target.value)} className="border border-gray-300 p-2 rounded w-full mb-4 resize-none h-36"></textarea>
              </div>
            </div>
            <div className="flex justify-center">
              <button 
                className={`bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 ${!isValid && 'opacity-50 cursor-not-allowed'}`} 
                onClick={handleSubmit} 
                disabled={!isValid}
              >
                전송
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default CreateCard;
