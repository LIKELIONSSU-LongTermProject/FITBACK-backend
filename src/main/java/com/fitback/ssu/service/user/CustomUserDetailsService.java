package com.fitback.ssu.service.user;

import com.fitback.ssu.domain.authority.Authority;
import com.fitback.ssu.domain.user.User;
import com.fitback.ssu.domain.user.UserRepository;
import com.fitback.ssu.exception.BizException;
import com.fitback.ssu.exception.UserExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws BizException {
        log.debug("CustomUserDetailsService -> email = {}", email);
        return userRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new BizException(UserExceptionType.NOT_FOUND_USER));
    }

    @Transactional(readOnly = true)
    public User getUser(String email) throws BizException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BizException(UserExceptionType.NOT_FOUND_USER));
    }

    // DB에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(User user){
        List<SimpleGrantedAuthority> authList = user.getAuthorities()
                .stream()
                .map(Authority::getAuthorityName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        authList.forEach(o -> log.debug("authList -> {}", o.getAuthority()));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authList
        );
    }
}
