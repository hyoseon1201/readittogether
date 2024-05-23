import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import DoughnutChart from "../../components/DoughnutChart.js";

import fetchProfileInfo from "../../api/accounts/fetchProfileInfo";

import FollowModal from "../../components/modal/FollowModal";
import EditNicknameModal from "../../components/modal/EditNicknameModal";
import EditImageModal from "../../components/modal/EditImageModal";

import settings from "../../assets/profile/settings.png";
import edit from "../../assets/profile/edit.png";

const Profile = () => {
  const navigate = useNavigate();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isNicknameEditOpen, setIsNicknameEditOpen] = useState(false);
  const [isImageEditOpen, setIsImageEditOpen] = useState(false);
  const [memberId, setMemberId] = useState(null);
  const whoFollowList = true;
  // 받아오는 프로필 정보
  const [profileInfo, setProfileInfo] = useState({
    profileImage: "",
    nickname: "",
    email: "",
    followList: [],
    followerList: [],
    evaluatedBookCnt: "",
    likedBookCnt: "",
    sendCardCnt: "",
    genreNoList: [],
  });

  useEffect(() => {
    console.log(profileInfo.followList);
    console.log(profileInfo.followerList);
  }, [profileInfo]);

  let followCount = profileInfo.followList.length;
  let follwerCount = profileInfo.followerList.length;

  // 프로필 이미지가 없는 경우 기본 이미지로 대체
  // const profileImageSrc = profileInfo.profileImage ? profileInfo.profileImage : noImg;

  useEffect(() => {
    const storedMemberId = localStorage.getItem("memberId");
    setMemberId(storedMemberId);

    // URL에서 memberId 추출하기 (예시로, 도메인/library/{memberId} 형태의 URL을 가정)
    const pathArray = window.location.pathname.split("/");
    const memberIdFromURL = pathArray[pathArray.length - 1];

    // 두 memberId가 일치하는지 확인하고, 불일치할 경우 경고 또는 처리
    if (storedMemberId && storedMemberId !== memberIdFromURL) {
      console.warn(
        "URL의 memberId와 로컬 스토리지의 memberId가 일치하지 않습니다."
      );
    }
  }, []);

  // 데이터의 장르명 표시 바꾸기
  const genreMapping = {
    1: "액션",
    2: "호러",
    3: "미스터리",
    4: "판타지",
    5: "역사",
    6: "로맨스",
    7: "SF",
    8: "한국문학",
    9: "한국단편",
    10: "영미단편",
    11: "영미문학",
    12: "일본단편",
    13: "일본문학",
    14: "중국문학",
    15: "스페인문학",
    16: "북유럽문학",
    17: "라틴문학",
    18: "러시아문학",
    19: "동유럽문학",
    20: "독일문학",
    21: "프랑스문학",
  };

  // {profileInfo.genreNoList}가 [0,1]이 아니라 01로 화면에 표시됨 -> 배열 형식으로 만들기
  const genreCountByName = [];
  for (const index in genreMapping) {
    const genreName = genreMapping[index];
    const count = profileInfo.genreNoList[index];
    genreCountByName.push({ genre: genreName, count: count }); // 장르명: 횟수 형식으로 추가
  }

  const dataLabels = [];
  const dataValues = [];
  genreCountByName.forEach((data) => {
    dataLabels.push(data.genre);
    dataValues.push(data.count);
  });

  useEffect(() => {
    // fetchProfileInfo 함수를 사용하여 프로필 정보를 가져옴
    const getProfileInfo = async () => {
      try {
        const info = await fetchProfileInfo(); // fetchProfileInfo 함수 호출
        setProfileInfo(info); // 가져온 프로필 정보 설정
      } catch (error) {
        console.error("프로필 정보를 가져오는 데 실패했습니다:", error);
      }
    };
    getProfileInfo(); // 함수 호출
  }, [isNicknameEditOpen, isImageEditOpen]);

  const handleSettingsClick = () => {
    navigate(`/modify/${memberId}`);
  };

  return (
    <div className="flex items-center justify-center pt-32">
      <div className="flex items-center justify-center bg-sky-100 w-[1000px] p-12">
        <div className="flex bg-white w-[900px] p-6 flex-col">
          {/* 유저 정보와 그래프 */}
          <div className="flex">
            {/* 유저 정보 */}
            <div className="flex-1 ml-4">
              <div className="relative">
                <img
                  className="w-52 h-52 rounded-full mb-2"
                  // src={profileImageSrc}
                  src={profileInfo.profileImage}
                  alt="프로필 이미지"
                />
                <button
                  className="absolute bottom-1 right-56 cursor-pointer"
                  onClick={() => setIsImageEditOpen(true)}
                >
                  <img src={edit} alt="edit" className="w-5 h-5" />
                </button>
                {isImageEditOpen && (
                  <EditImageModal
                    onClose={() => setIsImageEditOpen(false)}
                    nickname={profileInfo.nickname}
                  />
                )}
              </div>
              <div className="flex items-center">
                {/* <span className="font-semibold text-xl mb-2"> 닉네임 </span> */}
                <span className="customFont text-xl mb-2">
                  {profileInfo.nickname}
                </span>
                <button
                  className="cursor-pointer p-0 mx-1"
                  onClick={() => setIsNicknameEditOpen(true)}
                >
                  <img src={edit} alt="edit" className="w-4 h-4" />
                </button>
                {isNicknameEditOpen && (
                  <EditNicknameModal
                    onClose={() => setIsNicknameEditOpen(false)}
                  />
                )}
              </div>
              <p className="underline text-gray-500 text-xs mb-2">
                {/* 이메일 */}
                {profileInfo.email}
              </p>
              {/* 팔로우 수 */}
              <button onClick={() => setIsModalOpen(true)}>
                <div className="flex">
                  {/* 팔로잉 */}
                  <div className="flex mr-2">
                    <p className="text-gray-500 mr-2">팔로잉</p>
                    {/* 팔로잉 수 */}
                    <p className="font-semibold">{followCount}</p>
                  </div>
                  {/* 팔로워 */}
                  <div className="flex">
                    <p className="text-gray-500 mr-2">팔로워</p>
                    {/* 팔로워 수 */}
                    <p className="font-semibold">{follwerCount}</p>
                  </div>
                </div>
              </button>
              {isModalOpen && (
                <FollowModal
                  onClose={() => setIsModalOpen(false)}
                  followingList={profileInfo.followList}
                  followerList={profileInfo.followerList}
                  whoFollowList={whoFollowList}
                />
              )}
            </div>

            {/* 그래프 넣는 곳 */}
            <div className="flex-1 flex justify-center items-center">
              <div>
                {/* 도넛 그래프를 그릴 canvas 요소 */}
                {/* 데이터가 있는 경우에만 차트를 표시 */}
                {profileInfo.genreNoList.reduce((acc, curr) => acc + curr, 0) !== 0 && (
                  <DoughnutChart
                    dataLabels={dataLabels}
                    dataValues={dataValues}
                  />
                )}
                {/* 데이터가 없는 경우 메시지를 표시 */}
                {profileInfo.genreNoList.reduce((acc, curr) => acc + curr, 0) === 0 && (
                  <p className="text-center text-md text-black-400">최근 1달동안 읽은 책이 없습니다</p>
                )}
              </div>
            </div>
            <div
              className="relative right-0 cursor-pointer"
              onClick={handleSettingsClick}
            >
              <img className="w-5" src={settings} alt="" />
            </div>
          </div>

          {/* hr 태그와 '내가 읽은 책' 텍스트 */}
          <div className="mt-4">
            <hr />
            <div className="flex justify-between mt-4 mx-10 font-semibold text-sm">
              <div className="flex-col">
                <p>평가 완료한 책</p>
                <p className="flex items-center justify-center text-2xl">
                  {profileInfo.evaluatedBookCnt}
                </p>
              </div>
              <div className="flex-col">
                <p>관심 있는 책</p>
                <p className="flex items-center justify-center text-2xl">
                  {profileInfo.likedBookCnt}
                </p>
              </div>
              <div className="flex-col">
                <p>내가 쓴 카드</p>
                <p className="flex items-center justify-center text-2xl">
                  {profileInfo.sendCardCnt}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;
