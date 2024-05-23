package com.ssafy.rit.back.serviceImpl;

import com.ssafy.rit.back.entity.Follow;
import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.exception.follow.AlreadyFollowedException;
import com.ssafy.rit.back.exception.follow.FollowException;
import com.ssafy.rit.back.exception.follow.FollowNotFoundException;
import com.ssafy.rit.back.exception.follow.FollowSelfException;
import com.ssafy.rit.back.repository.FollowRepository;
import com.ssafy.rit.back.service.FollowService;
import com.ssafy.rit.back.util.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;


    public FollowServiceImpl(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    @Override
    public void follow(Member following, Member follower) {

        if (follower == following) {
            throw new FollowSelfException();
        }

        if (followRepository.findFollow(following, follower).isPresent()) {
            throw new AlreadyFollowedException();
        }

        Follow follow = Follow.builder()
                .followingMember(following)
                .followerMember(follower)
                .build();

        followRepository.save(follow);

        following.getFollowings().add(follow);
        follower.getFollowers().add(follow);

    }

    @Override
    public void unfollow(Member following, Member follower) {

        Follow follow = followRepository.findFollow(following, follower)
                .orElseThrow(() -> new FollowNotFoundException());

        followRepository.delete(follow);

        following.getFollowings().remove(follow);
        follower.getFollowers().remove(follow);

    }

    @Override
    public List<Member> getFollowingList(Member followingOwner) {

        List<Follow> followingRelationships = followRepository.findByFollowingMember(followingOwner);
        List<Member> followingList = new ArrayList<>();

        for (Follow f : followingRelationships) {
            followingList.add(f.getFollowerMember());

        }


        return followingList;
    }

    @Override
    public List<Member> getFollowerList(Member followerOwner) {

        List<Follow> followerRelationships = followRepository.findByFollowerMember(followerOwner);
        List<Member> followerList = new ArrayList<>();

        for (Follow f : followerRelationships) {
            followerList.add(f.getFollowingMember());
        }

        return followerList;
    }

    @Override
    public Boolean isFollowing(Member currentMember, Member targetMember) {
        return followRepository.isFollowing(currentMember, targetMember);
    }



}
