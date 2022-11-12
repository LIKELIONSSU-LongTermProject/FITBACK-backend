package com.fitback.ssu.config;

import com.fitback.ssu.domain.authority.Authority;
import com.fitback.ssu.domain.authority.AuthorityRepository;
import com.fitback.ssu.domain.authority.UserAuth;
import com.fitback.ssu.domain.user.User;
import com.fitback.ssu.domain.user.UserRepository;
import com.fitback.ssu.dto.user.UserReqDTO;
import com.fitback.ssu.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * initDataBaseForLocal 설명 : 로컬에서 테스트용으로 사용하기위해 데이터를 넣어 두기 위한 코드
 * @version 1.0.0
 *
 **/
@Profile("local") // local 용
@Component
@RequiredArgsConstructor
public class initDataBaseForLocal {

    private final initDataBaseForLocalService initDataBaseForLocalService;

    @PostConstruct
    private void init() {
        this.initDataBaseForLocalService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class initDataBaseForLocalService {
        private final AuthService authService;
        private final UserRepository userRepository;
        private final AuthorityRepository authorityRepository;


        @Transactional
        public void init() {

            authorityRepository.save(new Authority(UserAuth.ROLE_ADMIN));
            authorityRepository.save(new Authority(UserAuth.ROLE_USER));
            authorityRepository.save(new Authority(UserAuth.ROLE_BABY));
            authorityRepository.save(new Authority(UserAuth.ROLE_PRO));


            authService.signup(new UserReqDTO(
                    "admin@admin.com",
                    "1234",
                    "admin1",
                    false
            ));

            authService.signup(new UserReqDTO(
                    "user@user.com",
                    "1234",
                    "user1",
                    false
            ));

            authService.signup(new UserReqDTO(
                    "pro@pro.com",
                    "1234",
                    "pro",
                    true
            ));

            authService.signup(new UserReqDTO(
                    "baby@baby.com",
                    "1234",
                    "baby",
                    false
            ));

            User admin = userRepository.findByEmail("admin@admin.com").get();
            User user = userRepository.findByEmail("user@user.com").get();
            User pro = userRepository.findByEmail("pro@pro.com").get();
            User baby = userRepository.findByEmail("baby@baby.com").get();

            admin.addAuthority(authorityRepository.findAuthorityByAuthorityName(UserAuth.ROLE_ADMIN).get());
            admin.activate(true);
            user.activate(true);
            pro.addAuthority(authorityRepository.findAuthorityByAuthorityName(UserAuth.ROLE_PRO).get());
            pro.activate(true);
            baby.addAuthority(authorityRepository.findAuthorityByAuthorityName(UserAuth.ROLE_BABY).get());
            baby.activate(true);
        }
    }
}