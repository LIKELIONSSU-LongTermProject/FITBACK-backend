package com.fitback.ssu.dto.user;

import com.fitback.ssu.domain.authority.Authority;
import com.fitback.ssu.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReqDTO {
    private String email;
    private String password;
    private String username;
    private Boolean isPro;

    public User toMember(PasswordEncoder passwordEncoder, Set<Authority> authorities) {
        return User.builder()
                .email(email)
                .username(username)
                .password(passwordEncoder.encode(password))
                .peanut(0)
                .activated(false)
                .authorities(authorities)
                .build();
    }

}
