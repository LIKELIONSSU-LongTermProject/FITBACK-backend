package com.fitback.ssu.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProSignup2Dto {

    private String email;
    private String nickname;

    private String shortIntro;
    private String longIntro;
    private String expressionExperience;
    private String expressionCompany;
    private String expressionSpeaking;
    private String expressionPart;
    private String expressionStrength;
}

