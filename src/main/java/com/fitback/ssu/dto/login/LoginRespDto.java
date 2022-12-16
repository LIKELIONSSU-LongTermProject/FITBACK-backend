package com.fitback.ssu.dto.login;

import com.fitback.ssu.dto.jwt.TokenDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRespDto {
    private Long uid;
    private TokenDTO tokenDTO;

    @Builder
    public LoginRespDto(Long uid, TokenDTO tokenDTO) {
        this.uid = uid;
        this.tokenDTO = tokenDTO;
    }
}
