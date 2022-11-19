package com.fitback.ssu.dto.question;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionCompleteBoxDto {
    private Long qID;
    private String sTime;
    private String qName;
    private String qContent;
    private String qRefer;
    private Integer answerCount;

    @Builder
    public QuestionCompleteBoxDto(Long qID, String sTime, String qName, String qContent, String qRefer, Integer answerCount) {
        this.qID = qID;
        this.sTime = sTime;
        this.qName = qName;
        this.qContent = qContent;
        this.qRefer = qRefer;
        this.answerCount = answerCount;
    }
}
