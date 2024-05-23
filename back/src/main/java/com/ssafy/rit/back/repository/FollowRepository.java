package com.ssafy.rit.back.repository;

import com.ssafy.rit.back.dto.library.responseDto.FollowerDto;
import com.ssafy.rit.back.entity.Follow;
import com.ssafy.rit.back.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Integer> {

    @Query("select f from Follow f where f.followingMember = :following and f.followerMember = :follower")
    Optional<Follow> findFollow(@Param("following") Member following, @Param("follower") Member follower);

    List<Follow> findByFollowingMember(Member followingMember);
    List<Follow> findByFollowerMember(Member followerMember);

    // 팔로잉 수 카운트
    @Query("select count(f) from Follow f where f.followingMember = :member")
    int countByFollowingMember(@Param("member") Member member);

    // 팔로워 수 카운트
    @Query("select count(f) from Follow f where f.followerMember = :member")
    int countByFollowerMember(@Param("member") Member member);

    @Query("SELECT f.followerMember.id FROM Follow f WHERE f.followingMember = :member")
    List<Long> findFollowingMemberIdsByFollower(@Param("member") Member member);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Follow f " +
            "WHERE f.followingMember = :following AND f.followerMember = :follower")
    Boolean isFollowing(@Param("following") Member following, @Param("follower") Member follower);
}
