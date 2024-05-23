package com.ssafy.rit.back.scheduler;

import com.ssafy.rit.back.service.GroupRecommendListService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupRecommendListScheduler {

    private final GroupRecommendListService groupRecommendListService;

    @Scheduled(cron = "0 19 8 * * *")
    public void createGroupRecommendList() {
        groupRecommendListService.createGroupRecommendList();
    }

    @Scheduled(cron = "0 0 2 * * SUN")
    public void changeIsReceivable() {
        groupRecommendListService.changeIsReceivable();
    }

    // 초기 책 데이터 삽입용 임시 메서드
    // @Scheduled(cron = "20 23 8 * * *")
    public void setTempBookCover() {
        groupRecommendListService.setTempBookCover();
    }
}
