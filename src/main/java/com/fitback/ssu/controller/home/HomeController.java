package com.fitback.ssu.controller.home;

import com.fitback.ssu.dto.home.ProProfileDto;
import com.fitback.ssu.dto.home.ProfileAndReviewDto;
import com.fitback.ssu.service.home.ProListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/home")
public class HomeController {

    private final ProListService proListService;

    // 1. 상단 이벤트 바

    // 추천 멘토(만족도 순)
    @GetMapping(value = "/recommendation")
    public ArrayList<ProProfileDto> recommandPro(){
        return proListService.recommendPros();
    }

    // pro에 대한 리뷰 최신순
    @GetMapping(value = "/review")
    public ArrayList<ProfileAndReviewDto> reviewList(
            @RequestParam(value = "sort", required = false, defaultValue = "all") String sort){
        return proListService.getProReviewList(sort);
    }

}
