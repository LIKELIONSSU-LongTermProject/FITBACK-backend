//package com.fitback.ssu.service.auth;
//
//import com.fitback.ssu.domain.authority.Authority;
//import com.fitback.ssu.domain.authority.AuthorityRepository;
//import com.fitback.ssu.domain.authority.UserAuth;
//import com.fitback.ssu.domain.jwt.RefreshTokenRepository;
//import com.fitback.ssu.domain.user.User;
//import com.fitback.ssu.domain.user.UserRepository;
//import com.fitback.ssu.dto.jwt.TokenDTO;
//import com.fitback.ssu.dto.login.LoginReqDTO;
//import com.fitback.ssu.dto.user.UserReqDTO;
//import com.fitback.ssu.exception.BizException;
//import com.fitback.ssu.exception.UserExceptionType;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//class AuthServiceTest {
//    @Autowired
//    AuthService authService;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    AuthorityRepository authorityRepository;
//
//    @Autowired
//    RefreshTokenRepository refreshTokenRepository;
//
//    @Autowired
//    EntityManager em;
//
//    /**
//     * 각 테스트 실행 전에 실행
//     */
//    @BeforeEach
//    public void beforeEach(){
//        System.out.println("@BeforeEach");
//        authorityRepository.save(new Authority(UserAuth.ROLE_USER));
//        authorityRepository.save(new Authority(UserAuth.ROLE_ADMIN));
//
//        UserReqDTO dto = new UserReqDTO();
//        dto.setUsername("normalUser");
//        dto.setEmail("normalUser@normalUser.com");
//        dto.setPassword("1234");
//
//        authService.signup(dto);
//    }
//
//    @DisplayName("회원가입이 정상적으로 된다")
//    @Test
//    @Transactional
//    public void signup(){
//        UserReqDTO dto = new UserReqDTO();
//        dto.setUsername("user1");
//        dto.setEmail("test@test.com");
//        dto.setPassword("54321");
//
//        authService.signup(dto);
//
//        // 영속성 컨텍스트 플러쉬
//        em.flush();
//        em.clear();
//
//        Optional<User> ret = userRepository.findByEmail("test@test.com");
//        assertEquals(ret.get().getUsername(),"user1");
//    }
//
//    @DisplayName("이미 존재하는 회원인 경우 회원가입 불가")
//    @Test
//    @Transactional
//    public void signupDuplicateMember() {
//        UserReqDTO dto = new UserReqDTO();
//        dto.setUsername("normalUser");
//        dto.setEmail("normalUser@normalUser.com");
//        dto.setPassword("1234");
//
//        BizException bizException = assertThrows(BizException.class, () -> {
//            authService.signup(dto);
//        });
//
//        // 이미 존재하는 사용자 입니다.
//        assertEquals(bizException.getBaseExceptionType().getErrorCode(),"DUPLICATE_USER");
//    }
//
//    @DisplayName("이메일이 존재하지 않아 로그인에 실패한다.")
//    @Test
//    @Transactional
//    public void loginFailBecauseNotFoundEmail() {
//        //given
//        LoginReqDTO dto = new LoginReqDTO();
//        dto.setEmail("abc@abc.com");
//
//        //when
//        BizException bizException = assertThrows(BizException.class, () -> {
//            authService.login(dto);
//        });
//
//        //then
//        // 사용자를 찾을 수 없습니다.
//        assertEquals(bizException.getBaseExceptionType().getErrorCode(), UserExceptionType.NOT_FOUND_USER.getErrorCode());
//    }
//
//    @DisplayName("비밀번호를 입력하지 않아 로그인 실패")
//    @Test
//    @Transactional
//    public void loginFailBecauseEmptyPassword() {
//        // given
//        LoginReqDTO dto = new LoginReqDTO();
//        dto.setEmail("normalUser@normalUser.com");
//
//        //when
//        BizException bizException = assertThrows(BizException.class, () -> {
//            authService.login(dto);
//        });
//
//        //then
//        // 비밀번호를 입력해주세요.
//        System.out.println(bizException.getBaseExceptionType().getErrorCode());
//        assertEquals(bizException.getBaseExceptionType().getErrorCode()
//                ,UserExceptionType.NOT_FOUND_PASSWORD.getErrorCode());
//
//    }
//
//    @DisplayName("비밀번호가 틀려서 로그인 실패")
//    @Test
//    @Transactional
//    public void loginFailBecauseWrongPassword() {
//        // given
//        LoginReqDTO dto = new LoginReqDTO();
//        dto.setEmail("normalUser@normalUser.com");
//        dto.setPassword("12345"); // right password : 1234
//
//        //when
//        BizException bizException = assertThrows(BizException.class, () -> {
//            authService.login(dto);
//        });
//
//        //then
//        // 비밀번호를 잘못 입력하였습니다.
//        assertEquals(bizException.getBaseExceptionType().getErrorCode()
//                ,UserExceptionType.WRONG_PASSWORD.getErrorCode());
//
//    }
//
//    @DisplayName("로그인에 성공한다")
//    @Test
//    @Transactional
//    public void loginSuccess() {
//        // given
//        LoginReqDTO dto = new LoginReqDTO();
//        dto.setEmail("normalUser@normalUser.com");
//        dto.setPassword("1234"); // right password : 1234
//
//        TokenDTO login = authService.login(dto);
//        assertEquals(login.getGrantType(),"Bearer");
//    }
//
//}