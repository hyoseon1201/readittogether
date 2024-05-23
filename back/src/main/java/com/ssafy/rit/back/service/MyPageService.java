package com.ssafy.rit.back.service;

import com.ssafy.rit.back.entity.Member;

import java.util.List;

public interface MyPageService {

    int getEvaluatedBookCnt(Long memberId);
    int getLikedBookCnt(Long memberId);
    int getSendCardCnt(Long memberId);
    List<Long> getGenreNoList();
}
