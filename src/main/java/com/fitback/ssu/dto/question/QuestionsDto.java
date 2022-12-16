package com.fitback.ssu.dto.question;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class QuestionsDto {
    private Long qID;
    private String questionerNickname;
    private String feedbackType;
    private Integer price;
    private String qContent;
    private String qRefer;
    private String createdAt;
    private Boolean isRejected;
    private Feedback feedback;
    private ArrayList<Comment> comments;

    @Builder
    public QuestionsDto(Long qID, String questionerNickname, String feedbackType,
                        Integer price, String qContent, String qRefer, String createdAt,
                        Boolean isRejected, Feedback feedback, ArrayList<Comment> comments) {
        this.qID = qID;
        this.questionerNickname = questionerNickname;
        this.feedbackType = feedbackType;
        this.price = price;
        this.qContent = qContent;
        this.qRefer = qRefer;
        this.createdAt = createdAt;
        this.isRejected = isRejected;
        this.feedback = feedback;
        this.comments = comments;
    }

    public static class Feedback{
        public String nickname;
        public String pImage;
        public String company;
        public String duty;
        public Integer annual;
        public Integer reviewCnt;
        public Integer satisfactionRatio;
        public String referenceUrl;
        public String answerContent;

        @Builder
        public Feedback(String nickname, String pImage,
                        String company, String duty, Integer annual,
                        Integer reviewCnt, Integer satisfactionRatio,
                        String referenceUrl, String answerContent) {
            this.nickname = nickname;
            this.pImage = pImage;
            this.company = company;
            this.duty = duty;
            this.annual = annual;
            this.reviewCnt = reviewCnt;
            this.satisfactionRatio = satisfactionRatio;
            this.referenceUrl = referenceUrl;
            this.answerContent = answerContent;
        }
    }

    public static class Comment{
        public String image;
        public String nickname;
        public String content;
        public String createdAt;

        @Builder
        public Comment(String image, String nickname, String content, String createdAt) {
            this.image = image;
            this.nickname = nickname;
            this.content = content;
            this.createdAt = createdAt;
        }
    }
}
