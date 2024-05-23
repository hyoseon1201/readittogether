package com.ssafy.rit.back.service;

import com.ssafy.rit.back.entity.Member;

import java.util.ArrayList;
import java.util.List;

public interface FollowService {

    void follow(Member follower, Member following);
    void unfollow(Member following, Member follower);

    List<Member> getFollowingList(Member followingOwner);
    List<Member> getFollowerList(Member followerOwner);
    Boolean isFollowing(Member currentMember, Member memberToCheck);
}
