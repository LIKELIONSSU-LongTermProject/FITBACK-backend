package com.fitback.ssu.dto.question;

import com.fitback.ssu.domain.question.enums.Part;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class QuestionInfoDto {
    private Long qId;
    private Part part;
    private Integer deadLine;
    private String questionName;
    private String reference;
    private String contents;
    private Set<String> keywords;

    @Builder
    public QuestionInfoDto(Long qId, Part part, Integer deadLine, String questionName, String reference, String contents, Set<String> keywords) {
        this.qId = qId;
        this.part = part;
        this.deadLine = deadLine;
        this.questionName = questionName;
        this.reference = reference;
        this.contents = contents;
        this.keywords = keywords;
    }

}
