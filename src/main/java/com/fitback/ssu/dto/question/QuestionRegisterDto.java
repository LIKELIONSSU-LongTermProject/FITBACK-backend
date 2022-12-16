package com.fitback.ssu.dto.question;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionRegisterDto {
    private Long proId;
    private String questionType;
    private Integer price;
    private String referenceUrl;
    private String content;
}
