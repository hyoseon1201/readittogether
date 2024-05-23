import React, { useState } from "react";
import ReactDOM from "react-dom";
import { Link } from "react-router-dom";

import FollowForm from "../../api/follow/FollowForm";

import logo from "../../assets/navbar/logo2.png";
import deleteButton from "../../assets/profile/delete.png";

const FollowModal = ({ onClose, followingList, followerList, whoFollowList }) => {
  const requestId = localStorage.getItem("memberId");
  const [isFollowingActive, setIsFollowingActive] = useState(true);
  const handleFollowerClick = () => {
    setIsFollowingActive(false);
  };

  const handleFollowingClick = () => {
    setIsFollowingActive(true);
  };

  // const handleFollow = () => {
  //   FollowForm(targetEmail, requestId);
  // };

  // const handleUnfollow = () => {
  //   FollowDelete(targetEmail, requestId);
  // };

  return ReactDOM.createPortal(
    <div
      className="fixed inset-0 flex items-center justify-center z-50"
      onClick={onClose}
    >
      <div className="absolute inset-0 bg-gray-800 opacity-50"></div>
      <div
        className="bg-white w-[400px] h-[400px] rounded-lg p-6 z-10"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="flex justify-between items-center text-gray-500 font-semibold text-sm font-serif italic leading-normal">
          <div className="flex">
            <img className="w-5 mr-1" src={logo} alt="로고" />
            <p>Read-it Together</p>
          </div>
          <button onClick={onClose}>
            <img className="w-3" src={deleteButton} alt="X" />
          </button>
        </div>
        <hr className="my-3 border-gray-300" />
        <div>
          <button
            onClick={handleFollowingClick}
            className={`rounded-t-lg text-xs py-2 px-4 ${
              isFollowingActive ? "bg-gray-200" : "bg-white hover:bg-gray-200"
            }`}
          >
            팔로잉
          </button>
          <button
            onClick={handleFollowerClick}
            className={`rounded-t-lg text-xs py-2 px-4 mr-1 ${
              !isFollowingActive ? "bg-gray-200" : "bg-white hover:bg-gray-200"
            }`}
          >
            팔로워
          </button>
        </div>
        <div className="max-h-[280px] bg-gray-200 py-0.5 rounded-b-lg rounded-r-lg overflow-y-auto">
          {/* 팔로워 또는 팔로잉 목록 표시 */}
          {isFollowingActive ? (
            // 팔로잉 목록 표시
            <div>
              {followingList.map((user, index) => (
                <div
                  key={index}
                  className="flex justify-between bg-white rounded-md px-2 py-1 m-2"
                >
                  <Link onClick={onClose} to={`/${user.memberId}`}>
                    <div className="flex items-center">
                      <img
                        className="rounded-full w-7 h-7 mr-1"
                        src={user.profileImage}
                        alt=""
                      />
                      <p className="font-semibold text-sm customFont">{user.nickname}</p>
                    </div>
                  </Link>
                </div>
              ))}
            </div>
          ) : (
            // 팔로워 목록 표시
            <div>
              {followerList.map((user, index) => (
                <div
                  key={index}
                  className="flex justify-between bg-white rounded-md px-2 py-1 m-2"
                >
                  <Link onClick={onClose} to={`/${user.memberId}`}>
                    <div className="flex items-center">
                      <img
                        className="rounded-full w-7 h-7 mr-1"
                        src={user.profileImage}
                        alt=""
                      />
                      <p className="font-semibold text-sm customFont">{user.nickname}</p>
                    </div>
                  </Link>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>,
    document.getElementById("modal-root")
  );
};

export default FollowModal;
