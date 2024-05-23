import React, { useState } from "react";
import { Link } from "react-router-dom";

import FollowModal from "../modal/FollowModal";

// 로고
import logo from "../../assets/navbar/logo2.png";

const SideBar = ({ profileInfo, FollowingList, FollowerList }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const memberId = localStorage.getItem("memberId");
  const whoFollowList = true;

  return (
    <nav className="flex flex-col h-full">
      <div className="bg-stone-100 py-12 flex flex-col items-center justify-center">
        <Link to={`/${memberId}`} className="flex flex-col items-center">
          <img className="w-20 mb-4" src={logo} alt="로고" />
          <div className="text-gray-500 font-serif italic text-xs text-center leading-normal">
            <p>You can make anything</p>
            <p>by writing!</p>
            <p className="text-gray-500 font-serif italic text-sm tracking-wide mt-1 text-center">
              Read-it Together!
            </p>
          </div>
        </Link>
        <Link to="/recommend-book">
          <button className="bg-white border border-teal-500 hover:bg-teal-500 hover:text-white rounded-2xl text-teal-500 text-xs h-8 mt-5 w-32 transition-colors duration-300">
            추천 홈 바로가기
          </button>
        </Link>
      </div>
      <div className="py- flex flex-col items-center justify-center flex-grow">
        {/* 유저 프로필 바로가기 */}
        <Link to={`/profile/${memberId}`}>
          <div className="flex flex-col items-center mb-4">
            {/* 프로필 사진 */}
            <img
              className="w-20 h-20 rounded-full mb-2"
              src={profileInfo.profileImage}
              alt="프로필 사진"
            />
            {/* 닉네임 */}
            <p className="text-lg font-semibold customFont">{profileInfo.nickname}</p>
          </div>
        </Link>
        <Link to="/logout">
          <button className="bg-white border border-gray-300 hover:bg-gray-300 hover:text-white rounded-lg text-gray-500 text-xs py-2 px-4 mt-2 transition-colors duration-300">
            로그아웃
          </button>
        </Link>
        <button
          className="bg-white border border-gray-300 hover:bg-gray-300 hover:text-white rounded-lg text-gray-500 text-xs py-2 px-4 mt-4 transition-colors duration-300"
          onClick={() => setIsModalOpen(true)}
        >
          팔로우 목록
        </button>
        {isModalOpen && (
          <FollowModal
            onClose={() => setIsModalOpen(false)}
            followingList={FollowingList}
            followerList={FollowerList}
            whoFollowList={whoFollowList}
          />
        )}

      </div>
      <footer className="py-4 px-6 flex flex-col items-center justify-center bg-gray-100 rounded-b-lg">
        <div className="flex items-center mb-2">
          <img className="w-6 h-6 mr-2" src={logo} alt="로고" />
          <span className="text-sm font-semibold">Read-it Together</span>
        </div>
        <p className="text-xs text-gray-500 text-center">
          &copy; 2023 Read-it Together. All rights reserved.
        </p>
      </footer>
    </nav>
  );
};

export default SideBar;
