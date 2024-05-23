import React, { useState, useEffect, useRef } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";

import SearchForm from "../../api/book/SearchForm";
import SideBar from "./SideBar";
import fetchProfileInfo from "../../api/accounts/fetchProfileInfo";

import "../../App.css";
import logo from "../../assets/navbar/logo.png";
import burger from "../../assets/navbar/hamberger.png";
import search from "../../assets/navbar/search.png";

import FollowerGet from "../../api/follow/FollowerGet";
import FollowingGet from "../../api/follow/FollowingGet";

const NavBar = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [isSearchOpen, setIsSearchOpen] = useState(false); // 검색 입력 창 상태
  const [profileInfo, setProfileInfo] = useState({
    nickname: "닉네임",
    profileImage: "",
  }); // 프로필 정보 상태 변수를 여기에 추가
  const [searchTerm, setSearchTerm] = useState("");
  const navRef = useRef(); // 네비게이션 바와 사이드바를 위한 ref
  const location = useLocation();
  const navigate = useNavigate();
  const memberId = localStorage.getItem("memberId");
  const email = localStorage.getItem("myEmail")
  const [FollowingList, setFollowingList] = useState([]);
  const [FollowerList, setFollowerList] = useState([]);

  useEffect(() => {
    const getFollowingList = async () => {
      try {
        const response = await FollowingGet(email);
        setFollowingList(response.data);
      } catch (error) {
        return null;
      }
    };
    const getFollowerList = async () => {
      try {
        const response = await FollowerGet(email);
        setFollowerList(response.data);
      } catch (error) {
        return null;
      }
    };
    getFollowingList();
    getFollowerList();
  }, [email]);

  // 프로필 정보 가져오기
  useEffect(() => {
    const getProfileData = async () => {
      try {
        const data = await fetchProfileInfo();
        setProfileInfo(data);
      } catch (error) {
        console.error("프로필 정보를 가져오는 데 실패했습니다:", error);
      }
    };
    getProfileData();
  }, [isOpen]); // 사이드바 Open 상태가 바뀔 때마다 정보 가져옴

  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  // 검색 입력 창 토글 함수
  const toggleSearch = () => {
    setIsSearchOpen(!isSearchOpen);
    setSearchTerm("")
  };

  // location 변경될 때 마운트
  // hamberger 및 search 버튼 비활성화
  useEffect(() => {
    setIsOpen(false);
    setIsSearchOpen(false);
  }, [location]);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (navRef.current && !navRef.current.contains(event.target)) {
        setIsOpen(false);
        setIsSearchOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [navRef]);

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
    if (event.key === "Enter") {
      navigate(`/search?query=${searchTerm}`);
    }
  };

  return (
    <nav
      ref={navRef}
      className="fixed flex justify-between items-center p-4  w-full z-50"
    >
      {/* 햄버거 버튼 */}
      <div className="flex">
        <button className="mr-4" onClick={toggleMenu}>
          <img className="w-5" src={burger} alt="햄버거" />
        </button>

        {/* 로고/홈 버튼 */}
        <Link to={`/${memberId}`} className="font-extrabold mr-6">
          <img className="w-36" src={logo} alt="" />
        </Link>
      </div>

      <div className="flex items-center">
        {/* 검색 버튼 */}
        <button onClick={toggleSearch}>
          <img className="w-5 mr-2" src={search} alt="검색" />
        </button>

        {/* 검색 입력 창, isSearchOpen 상태에 따라 표시 여부 결정 */}
        <input
          type="text"
          value={searchTerm}
          className={`text-xs transition-width duration-300 ease-in-out ${
            isSearchOpen ? "w-48" : "w-0"
          } h-8 pl-2`}
          style={{ visibility: isSearchOpen ? "visible" : "hidden" }}
          onChange={handleSearch}
          onKeyDown={handleSearch}
          placeholder="제목 및 작가 검색..."
        />
      </div>

      {/* 사이드바 메뉴, isOpen 상태에 따라 표시 여부 결정 */}
      <div
        className={`fixed top-0 left-0 w-[260px] h-full bg-white box-border z-50 transform transition duration-300 ease-in-out ${
          isOpen ? "translate-x-0" : "-translate-x-full"
        }`}
      >
        <button className="fixed ml-4 mt-6" onClick={toggleMenu}>
          <img className="w-5" src={burger} alt="햄버거" />
        </button>
        <SideBar profileInfo={profileInfo} FollowingList={FollowingList} FollowerList={FollowerList} />
      </div>
    </nav>
  );
};

export default NavBar;

