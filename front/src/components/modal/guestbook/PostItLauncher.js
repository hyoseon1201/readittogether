import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import WriteFormModal from "./PostItWriteForm"; // PostIt 작성 컴포넌트
import PostItView from "./PostItView"; // PostIt 컴포넌트
import GuestBookForm from "../../../api/llibrary/guestbook/GuestBookForm";
import GuestBookListGet from "../../../api/llibrary/guestbook/GuestBookListGet";

// 이미지
import postIt from "../../../assets/library/post-it.png";
import pen from "../../../assets/library/pen.png";

const PostItLauncher = ({ onClose, isMemberPage, memberId }) => {
  const [postItList, setPostItList] = useState(); // 방명록 리스트
  const [currentPostIt, setCurrentPostIt] = useState(0); // 현재 보여줄 postit 인덱스
  const [postItContent, setPostItContent] = useState(""); // 포스트잇 내용
  const [modalState, setModalState] = useState({
    showButtonsModal: true,
    showPostIt: false,
    showWriteForm: false,
  });
  const location = useLocation();


  const refreshPostItList = async () => {
    const list = await GuestBookListGet(isMemberPage); // 방명록 리스트 재조회
    setPostItList(list); // 상태 업데이트
  
    if (list.length === 0) {
      setModal({
        showButtonsModal: true,
        showPostIt: false,
        showWriteForm: false,
      });
    } else if (list.length <= currentPostIt) {
      // 리스트가 있지만 현재 선택된 항목이 범위를 벗어난 경우
      setCurrentPostIt(Math.max(0, list.length - 1));
    }
  };
  


  useEffect(() => {
    const getPostItList = async () => {
      try {
        const list = await GuestBookListGet(isMemberPage);
        setPostItList(list);
      } catch (error) {
        console.error("방명록 리스트 가져오기 실패:", error);
      }
    };

    getPostItList();
  }, [location]);
  
  const moveLeft = () => {
    setCurrentPostIt((prevIndex) => Math.max(0, prevIndex - 1));
  };

  const moveRight = () => {
    setCurrentPostIt((prevIndex) =>
      Math.min(postItList.length - 1, prevIndex + 1)
    );
  };

  useEffect(() => {
    if (postItList) {
      console.log(postItList[currentPostIt]);
    }
  }, [postItList]);

  // modalState의 각 속성을 구조 분해 할당
  const { showButtonsModal, showPostIt, showWriteForm } = modalState;

  const setModal = ({ showButtonsModal, showPostIt, showWriteForm }) => {
    setModalState({ showButtonsModal, showPostIt, showWriteForm });
  };

  const handleOpenWriteForm = () => {
    setModal({
      showButtonsModal: false,
      showPostIt: false,
      showWriteForm: true,
    });
  };

  const handleOpenPostIt = () => {
    if (!postItList || postItList.length === 0) {
      alert("방명록이 없습니다.");
      // 여기서 함수 실행을 중지
      return;
    }
    setModal({
      showButtonsModal: false,
      showPostIt: true,
      showWriteForm: false,
    });
  };

  const handleCloseAll = () => {
    setModal(
      {
        showButtonsModal: false,
        showPostIt: false,
        showWriteForm: false,
      },
      onClose()
    );
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // API 호출
    try {
      const responseData = await GuestBookForm(isMemberPage, postItContent);
      console.log("성공 했으면 true가 떠요:", responseData); // API 호출 결과 로깅
      refreshPostItList(); // 방명록 목록 새로고침
    } catch (error) {
      console.error("API 호출 중 오류 발생:", error);
    }

    setPostItContent(""); // 폼 초기화
    setModal({
      showButtonsModal: false,
      showPostIt: true,
      showWriteForm: false,
    }); // 작성 폼 모달 닫고, 포스트잇 보기
  };

  return (
    <div>
      {showButtonsModal && (
        <div
          className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 overflow-y-auto h-full w-full z-40"
          onClick={handleCloseAll}
        >
          <div
            className="flex flex-col items-center justify-center p-5 border w-96 h-96 shadow-lg bg-yellow-200"
            onClick={(e) => e.stopPropagation()}
          >
            <div className="mb-6">
              <button
                onClick={handleOpenWriteForm}
                className="flex items-center"
              >
                <img className=" w-20 mb-2 mr-4" src={pen} alt="작성" />
                <div className="customFont text-2xl">방명록 작성하기</div>
              </button>
            </div>
            <div className="mt-6">
              <button onClick={handleOpenPostIt} className="flex items-center">
                <img className="w-20 mb-2 mr-4" src={postIt} alt="보기" />
                <div className="customFont text-2xl">방명록 보러가기</div>
              </button>
            </div>
          </div>
        </div>
      )}

      {showWriteForm && (
        <WriteFormModal
          postItContent={postItContent}
          setPostItContent={setPostItContent}
          handleSubmit={handleSubmit}
          handleCloseAll={handleCloseAll}
          refreshList={refreshPostItList}
        />
      )}

      {showPostIt && postItList.length > 0 && (
        <div
          onClick={handleCloseAll}
          className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 overflow-y-auto h-full w-full z-60"
        >
          {postItList[currentPostIt] ? (
            <PostItView
              onClose={handleCloseAll}
              postId={postItList[currentPostIt]} // 현재 postIt의 id를 PostItView에 전달
              moveLeft={moveLeft}
              moveRight={moveRight}
              memberId={memberId}
              isMemberPage={isMemberPage}
              refreshList={refreshPostItList}
      
            />
          ) : (
            <div>방명록이 없습니다.</div>
          )}
        </div>
      )}
    </div>
  );
};

export default PostItLauncher;
