import profileView from "./ProfileView";

const fetchProfileInfo = async () => {
  try {
    const response = await profileView();
    const { profileImage, nickname, email, followList, followerList, evaluatedBookCnt, likedBookCnt, sendCardCnt, genreNoList } = response.data;
    // 이쪽에서 업데이트
    localStorage.setItem("profileImage", profileImage);
    localStorage.setItem("nickname", nickname);
    return {
      profileImage,
      nickname,
      email,
      followList,
      followerList,
      evaluatedBookCnt,
      likedBookCnt,
      sendCardCnt,
      genreNoList
    };
  } catch (error) {
    throw new Error("프로필 정보를 가져오는 데 실패했습니다.");
  }
};

export default fetchProfileInfo;