import React, { useState } from "react";
import { checkNicknameDuplicate } from "../../api/accounts/NicknameDuplicate";
import changeNickname from "../../api/accounts/ChangeNickname";
import fetchProfileInfo from "../../api/accounts/fetchProfileInfo";

const EditNicknameModal = ({ onClose }) => {
  const [newNickname, setNewNickname] = useState('');
  const [isNicknameOK, setIsNicknameOK] = useState(false);
  const [nicknameStatusMessage, setNicknameStatusMessage] = useState('');
  const [nicknameStatusMessageClassName, setNicknameStatusMessageClassName] = useState('');
  const [isNicknameDuplicate, setIsNicknameDuplicate] = useState(false);

  const nicknameRegExp = /^[가-힣a-zA-Z0-9]{2,8}$/;

  const handleNewNicknameChange = (e) => {
    setNewNickname(e.target.value);
    setIsNicknameOK(nicknameRegExp.test(e.target.value));
    setNicknameStatusMessage('');
  }

  const handleCheckNickname = async () => {
    try {
      const isDuplicate = await checkNicknameDuplicate(newNickname);
      setIsNicknameDuplicate(isDuplicate);
      setNicknameStatusMessage(!isDuplicate ? '중복된 닉네임입니다.' : '사용 가능한 닉네임입니다.');
      setNicknameStatusMessageClassName(!isDuplicate ? 'text-sm text-red-500' : 'text-sm text-blue-500');
    } catch (error) {
      console.log(error);
    };
  };

  const handleNicknameSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await changeNickname(newNickname);
      console.log(response);
      // 닉네임 변경 성공 시 nickname 업데이트
      // localStorage.setItem("nickname", newNickname);
      onClose();
      alert('닉네임이 변경되었습니다.');
      await fetchProfileInfo(); // 프로필 정보 다시 가져오기
      return response;
    } catch (error) {
      console.error(error);
      alert('실패');
    }
  };

  return(
    <div className="fixed inset-0 flex items-center justify-center z-50" onClick={onClose}>
      <div className="absolute inset-0 bg-gray-800 opacity-50"></div>
      <div className="flex items-center justify-center w-full h-full" onClick={(e) => e.stopPropagation()}>
        <div className="bg-white w-[400px] rounded-lg p-6 z-10">
          <form onSubmit={handleNicknameSubmit} className="flex flex-col gap-4 items-center">
            {/* 새 닉네임 */}
            <label htmlFor="newNickname" className="font-semibold">새로운 닉네임</label>
            <p className="text-sm text-gray-500">한글·영문·숫자를 포함한 2~8자(공백 허용X)</p>
            <input
              type="text"
              value={newNickname}
              onChange={handleNewNicknameChange}
              className="border border-gray-300 px-2 py-1 w-full"
            />
            {/* 닉네임 중복확인 */}
            <button type="button" onClick={handleCheckNickname} disabled={!isNicknameOK} className={`bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 ${isNicknameOK && !isNicknameDuplicate ? '' : 'opacity-50 cursor-not-allowed'}`}>
              중복 확인
            </button>
            {/* 닉네임 상태 메시지 */}
            <p className={nicknameStatusMessageClassName}>{nicknameStatusMessage}</p>
            {/* 닉네임 변경 버튼 */}
            <button type="submit" disabled={!isNicknameDuplicate} className={`bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 ${(!isNicknameDuplicate) && 'opacity-50 cursor-not-allowed'}`}>
              닉네임 변경
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default EditNicknameModal;