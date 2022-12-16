package com.fitback.ssu.dto.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {
    private Long questionId;
    private String review;
    private Integer satisfaction;

}
