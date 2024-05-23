import React, { useState, useEffect } from "react";

import FollowForm from "../../api/follow/FollowForm";
import FollowDelete from "../../api/follow/FollowDelete";

const FollowButton = ({ isFollowing, targetEmail }) => {
  const [isFollowingState, setIsFollowingState] = useState(isFollowing);
  const requestId = localStorage.getItem("memberId");

  useEffect(() => {
    setIsFollowingState(isFollowing);
  }, [isFollowing]);

  const handleFollow = async () => {
    try {
      await FollowForm(targetEmail, requestId);
      setIsFollowingState(1); // 팔로우 성공 후 상태 업데이트
    } catch (error) {
      return null;
    }
  };

  const handleUnfollow = async () => {
    try {
      await FollowDelete(targetEmail, requestId);
      setIsFollowingState(0); // 언팔로우 성공 후 상태 업데이트
    } catch (error) {
      return null;
    }
  };

  return (
    <div>
      {isFollowingState === 1 ? (
        <button
          className="bg-pink-100 text-sm font-bold px-2 py-1 rounded-md"
          onClick={handleUnfollow}
        >
          팔로우 취소
        </button>
      ) : (
        <button
          className="bg-pink-100 text-sm font-bold px-2 py-1 rounded-md"
          onClick={handleFollow}
        >
          팔로우
        </button>
      )}
    </div>
  );
};

export default FollowButton;
