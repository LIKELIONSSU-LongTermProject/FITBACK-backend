package com.fitback.ssu.dto.signup;

import com.fitback.ssu.domain.authority.Authority;
import com.fitback.ssu.domain.user.User;
import com.fitback.ssu.domain.user.info.ProInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProSignup1Dto {
    private String email;
    private String password;
    private String username;
    private String birth;

    private String company;
    private String duty;
    private Integer annual;

    public User toMember(PasswordEncoder passwordEncoder, Set<Authority> authorities, Boolean activate, ProInfo proInfo) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .username(username)
                .birth(birth)
                .status(null)
                .peanut(0)
                .activated(activate)
                .authorities(authorities)
                .babyInfo(null)
                .proInfo(proInfo)
                .build();
    }
}

