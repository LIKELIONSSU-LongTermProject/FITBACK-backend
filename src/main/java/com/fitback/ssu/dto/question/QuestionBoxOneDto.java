package com.fitback.ssu.dto.question;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class QuestionBoxOneDto {
    private Integer ansReqCnt;
    private ArrayList<AnsReq> ansReqs;

    @Builder
    public QuestionBoxOneDto(ArrayList<AnsReq> ansReqs) {
        this.ansReqCnt = ansReqs.size();
        this.ansReqs = ansReqs;
    }

    public static class AnsReq{
        private Long aID;
        private String username;
        private Integer ansCount;

        @Builder
        public AnsReq(Long aID, String username, Integer ansCount) {
            this.aID = aID;
            this.username = username;
            this.ansCount = ansCount;
        }
    }
}
