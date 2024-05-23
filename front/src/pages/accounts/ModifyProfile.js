import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import changePassword from "../../api/accounts/ChangePassword";
import DisableModal from "../../components/modal/DisableModal";

const ModifyProfile = () => {
  const navigate = useNavigate();

  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [newPasswordConfirm, setNewPasswordConfirm] = useState("");

  const [isPasswordOK, setIsPasswordOK] = useState(false);
  const [isPasswordMatch, setIsPasswordMatch] = useState(false);

  const [passwordStatusMessage, setPasswordStatusMessage] = useState("");
  const [passwordConfirmStatusMessage, setPasswordConfirmStatusMessage] =
    useState("");

  const [isOpenModal, setIsOpenModal] = useState(false);

  const passwordRegExp =
    /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;

  const handleOldPasswordChange = (e) => {
    setOldPassword(e.target.value);
  };

  const handleNewPasswordChange = (e) => {
    const { value } = e.target;
    setNewPassword(value);
    setIsPasswordOK(passwordRegExp.test(value));
    setIsPasswordMatch(value === newPasswordConfirm && value !== oldPassword);
    setPasswordStatusMessage(
      passwordRegExp.test(value)
        ? ""
        : "비밀번호는 8~20자 사이의 영문, 숫자, 특수문자 조합이어야 합니다."
    );
  };

  const handleNewPasswordConfirmChange = (e) => {
    const { value } = e.target;
    setNewPasswordConfirm(value);
    setIsPasswordMatch(value === newPassword && value !== oldPassword);
    setPasswordConfirmStatusMessage(
      value === newPassword ? "" : "비밀번호가 일치하지 않습니다."
    );
  };

  useEffect(() => {
    const isValid =
      oldPassword &&
      newPassword &&
      newPasswordConfirm &&
      newPassword === newPasswordConfirm;
    setIsPasswordOK(isValid);
  }, [oldPassword, newPassword, newPasswordConfirm]);

  const handlePasswordSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await changePassword(oldPassword, newPassword);
      console.log(response);
      alert("비밀번호가 변경되었습니다.");
      // 로그인 풀리고 로그인 화면으로
      navigate(`/login`);
      return response;
    } catch (error) {
      console.error(error);
      alert("실패");
    }
  };

  return (
    <div>
      <div className="flex flex-col items-center justify-center pt-32">
        <form
          onSubmit={handlePasswordSubmit}
          className="flex flex-col gap-4 mb-8"
        >
          <label htmlFor="oldPassword" className="text-gray-700">
            현재 비밀번호
          </label>
          <input
            type="password"
            value={oldPassword}
            onChange={handleOldPasswordChange}
            className="w-[380px] border border-gray-300 px-3 py-2 rounded-md focus:outline-none focus:border-blue-500"
          />
          <label htmlFor="newPassword" className="text-gray-700">
            새로운 비밀번호
          </label>
          <input
            type="password"
            value={newPassword}
            onChange={handleNewPasswordChange}
            className="border border-gray-300 px-3 py-2 rounded-md focus:outline-none focus:border-blue-500"
          />
          {/* 비밀번호 메시지 */}
          <p className="text-xs text-red-500">{passwordStatusMessage}</p>
          <label htmlFor="newPasswordConfirm" className="text-gray-700">
            새로운 비밀번호 확인
          </label>
          <input
            type="password"
            value={newPasswordConfirm}
            onChange={handleNewPasswordConfirmChange}
            className="border border-gray-300 px-3 py-2 rounded-md focus:outline-none focus:border-blue-500"
          />
          {/* 비밀번호 확인 메시지 */}
          <p className="text-xs text-red-500">{passwordConfirmStatusMessage}</p>
          {/* 비밀번호 변경 버튼 */}
          <button
            type="submit"
            disabled={!isPasswordOK || !isPasswordMatch}
            className={`bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 ${
              (!isPasswordOK || !isPasswordMatch) &&
              "opacity-50 cursor-not-allowed"
            }`}
          >
            비밀번호 변경
          </button>
        </form>
        <button
          className="w-[380px] bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600"
          onClick={() => setIsOpenModal(true)}
        >
          탈퇴하기
        </button>
        {isOpenModal && <DisableModal onClose={() => setIsOpenModal(false)} />}
      </div>
    </div>
  );
};

export default ModifyProfile;
