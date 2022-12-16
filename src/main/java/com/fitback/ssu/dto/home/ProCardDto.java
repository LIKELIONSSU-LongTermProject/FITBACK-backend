package com.fitback.ssu.dto.home;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class ProCardDto {
    private String imageUrl;
    private String nickname;
    private String company;
    private String duty;
    private Integer annual;
    private Integer reviewNum;
    private Integer satisfactionRatio;

    private String shortIntro;
    private String longIntro;
    private String expressionExperience;
    private String expressionCompany;
    private String expressionSpeaking;
    private String expressionPart;
    private String expressionStrength;

    private ArrayList<ReviewInfo> reviewInfos;

    @Builder

    public ProCardDto(String imageUrl, String nickname,
                      String company, String duty, Integer annual,
                      Integer reviewNum, Integer satisfactionRatio,
                      String shortIntro, String longIntro,
                      String expressionExperience, String expressionCompany,
                      String expressionSpeaking, String expressionPart, String expressionStrength,
                      ArrayList<ReviewInfo> reviewInfos) {
        this.imageUrl = imageUrl;
        this.nickname = nickname;
        this.company = company;
        this.duty = duty;
        this.annual = annual;
        this.reviewNum = reviewNum;
        this.satisfactionRatio = satisfactionRatio;
        this.shortIntro = shortIntro;
        this.longIntro = longIntro;
        this.expressionExperience = expressionExperience;
        this.expressionCompany = expressionCompany;
        this.expressionSpeaking = expressionSpeaking;
        this.expressionPart = expressionPart;
        this.expressionStrength = expressionStrength;
        this.reviewInfos = reviewInfos;
    }

    public static class ReviewInfo{
        public String userNickname;
        public Integer satisfaction;
        public String createdAt;
        public String review;

        @Builder
        public ReviewInfo(String userNickname, Integer satisfaction, String createdAt, String review) {
            this.userNickname = userNickname;
            this.satisfaction = satisfaction;
            this.createdAt = createdAt;
            this.review = review;
        }
    }
}
