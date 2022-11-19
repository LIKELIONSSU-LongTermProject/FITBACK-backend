package com.fitback.ssu.dto.question;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionOnProgressBoxDto {
    private Long qID;
    private Integer deadLine;
    private String qName;
    private String qContent;
    private String qRefer;
    private Integer permitReqCnt;

    @Builder
    public QuestionOnProgressBoxDto(Long qID, Integer deadLine, String qName, String qContent, String qRefer, Integer permitReqCnt) {
        this.qID = qID;
        this.deadLine = deadLine;
        this.qName = qName;
        this.qContent = qContent;
        this.qRefer = qRefer;
        this.permitReqCnt = permitReqCnt;
    }
}
