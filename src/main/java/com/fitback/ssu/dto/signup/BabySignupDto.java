package com.fitback.ssu.dto.signup;

import com.fitback.ssu.domain.authority.Authority;
import com.fitback.ssu.domain.user.User;
import com.fitback.ssu.domain.user.info.BabyInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BabySignupDto {
    private String email;
    private String password;
    private String username;
    private String birth;
    private String status;
    private String nickname;
    private ArrayList<String> interestCompanies;
    private ArrayList<String> interestDuties;

    @Transactional
    public User toMember(PasswordEncoder passwordEncoder, Set<Authority> authorities, Boolean activate, BabyInfo babyInfo) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .username(username)
                .birth(birth)
                .status(status)
                .peanut(0)
                .activated(activate)
                .authorities(authorities)
                .babyInfo(babyInfo)
                .proInfo(null)
                .build();
    }
}
