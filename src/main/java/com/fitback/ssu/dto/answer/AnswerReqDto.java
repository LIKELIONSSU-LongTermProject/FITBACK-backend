package com.fitback.ssu.dto.answer;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class AnswerReqDto {
    private Long reqCount;
    private ArrayList<PermissionReqAnswer> permissionReqAnswers;

    public static class PermissionReqAnswer{
        private Long aID;
        private String username;
        private Long answerCount;
        private Double satisfaction;

        @Builder
        public PermissionReqAnswer(Long aID, String username, Long answerCount, Double satisfaction) {
            this.aID = aID;
            this.username = username;
            this.answerCount = answerCount;
            this.satisfaction = satisfaction;
        }
    }
}
