package com.fitback.ssu.dto.question;

import com.fitback.ssu.domain.question.enums.Part;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class QuestionRegisterDto {
    private Part part;
    private Set<String> keywords;
    private String startTime;
    private String endTime;
    private String reference;
    private String questionName;
    private String content;
    private Boolean isPublic;
}
