package com.fitback.ssu.dto.home;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileAndReviewDto {
    private String imageUrl;
    private String nickname;
    private String company;
    private String duty;
    private Integer annual;
    private ReviewInfo reviewInfo;

    @Builder
    public ProfileAndReviewDto(String nickname, String imageUrl,
                               String company, String duty, Integer annual,
                               ReviewInfo reviewInfo) {
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.company = company;
        this.duty = duty;
        this.annual = annual;
        this.reviewInfo = reviewInfo;
    }

    public static class ReviewInfo{
        public String writer;
        public String review;
        public String createdAt;

        @Builder
        public ReviewInfo(String writer, String review, String createdAt) {
            this.writer = writer;
            this.review = review;
            this.createdAt = createdAt;
        }
    }
}
