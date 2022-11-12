package com.fitback.ssu.dto.user;

import com.fitback.ssu.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRespDTO {
    private String email;

    public static UserRespDTO of(User user){
        return new UserRespDTO(user.getEmail());
    }
}
