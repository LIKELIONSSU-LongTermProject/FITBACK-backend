package com.fitback.ssu.dto.signup;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProCheckDto {
    private String email;
    private String emailCheckCode;

    @Builder
    public ProCheckDto(String email, String emailCheckCode) {
        this.email = email;
        this.emailCheckCode = emailCheckCode;
    }
}
