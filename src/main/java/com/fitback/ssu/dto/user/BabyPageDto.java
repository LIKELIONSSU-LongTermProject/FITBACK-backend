package com.fitback.ssu.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BabyPageDto {
    private String username;
    private String email;

    @Builder
    public BabyPageDto(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
