package com.fitback.ssu.service.auth;

import com.fitback.ssu.domain.authority.Authority;
import com.fitback.ssu.domain.authority.AuthorityRepository;
import com.fitback.ssu.domain.authority.UserAuth;
import com.fitback.ssu.domain.jwt.RefreshToken;
import com.fitback.ssu.domain.jwt.RefreshTokenRepository;
import com.fitback.ssu.domain.user.User;
import com.fitback.ssu.domain.user.UserRepository;
import com.fitback.ssu.domain.user.info.*;
import com.fitback.ssu.dto.jwt.TokenDTO;
import com.fitback.ssu.dto.jwt.TokenReqDTO;
import com.fitback.ssu.dto.login.LoginReqDTO;
import com.fitback.ssu.dto.login.LoginRespDto;
import com.fitback.ssu.dto.signup.BabySignupDto;
import com.fitback.ssu.dto.signup.ProCheckDto;
import com.fitback.ssu.dto.signup.ProSignup1Dto;
import com.fitback.ssu.dto.signup.ProSignup2Dto;
import com.fitback.ssu.dto.user.UserReqDTO;
import com.fitback.ssu.dto.user.UserRespDTO;
import com.fitback.ssu.exception.AuthorityExceptionType;
import com.fitback.ssu.exception.BizException;
import com.fitback.ssu.exception.JwtExceptionType;
import com.fitback.ssu.exception.UserExceptionType;
import com.fitback.ssu.jwt.CustomEmailPasswordAuthToken;
import com.fitback.ssu.jwt.TokenProvider;
import com.fitback.ssu.service.user.CustomUserDetailsService;
import com.fitback.ssu.service.user.EmailService;
import com.fitback.ssu.util.OCR;
import com.fitback.ssu.util.s3.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final FileUploadService fileUploadService;
    private final BabyInfoRepository babyInfoRepository;
    private final UserRepository userRepository;
    private final InterestCompanyRepository interestCompanyRepository;
    private final InterestDutyRepository interestDutyRepository;
    private final ProInfoRepository proInfoRepository;
    @Value("${RESOURCE_S3}")
    private String RESOURCE_S3;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;

    @Transactional
    public UserRespDTO signup(UserReqDTO userReqDTO) {
        if(userRepository.existsByEmail(userReqDTO.getEmail())){
            throw new BizException(UserExceptionType.DUPLICATE_USER);
        }

        Authority authority = authorityRepository
                .findAuthorityByAuthorityName(UserAuth.ROLE_USER).orElseThrow(
                        () -> new BizException(AuthorityExceptionType.NOT_FOUND_AUTHORITY));

        Set<Authority> set = new HashSet<>();
        set.add(authority); // 기본적으로 ROLE_USER 권한 부여

       // 회원가입시 현직자인지 초보자인지 체크해서 ROLE 세팅
        if(userReqDTO.getIsPro() == true){
            set.add(authorityRepository.findAuthorityByAuthorityName(UserAuth.ROLE_PRO)
                    .orElseThrow(() -> new BizException(AuthorityExceptionType.NOT_FOUND_AUTHORITY)));
        }else{
            set.add(authorityRepository.findAuthorityByAuthorityName(UserAuth.ROLE_BABY)
                    .orElseThrow(() -> new BizException(AuthorityExceptionType.NOT_FOUND_AUTHORITY)));
        }

        User user = userReqDTO.toMember(passwordEncoder, set);
        log.debug("user = {}", user);
        return UserRespDTO.of(userRepository.save(user));
    }

    @Transactional
    public void babySignup(MultipartFile image, BabySignupDto babySignupDto) {
        if(userRepository.existsByEmail(babySignupDto.getEmail())){
            throw new BizException(UserExceptionType.DUPLICATE_USER);
        }

        Authority authority = authorityRepository
                .findAuthorityByAuthorityName(UserAuth.ROLE_USER).orElseThrow(
                        () -> new BizException(AuthorityExceptionType.NOT_FOUND_AUTHORITY));

        Set<Authority> set = new HashSet<>();
        set.add(authority); // 기본적으로 ROLE_USER 권한 부여
        set.add(authorityRepository.findAuthorityByAuthorityName(UserAuth.ROLE_BABY)
                .orElseThrow(() -> new BizException(AuthorityExceptionType.NOT_FOUND_AUTHORITY)));

        User user = babySignupDto.toMember(passwordEncoder, set, true, null);

        String imageUrl = RESOURCE_S3 + fileUploadService.save(image).getPath();

        BabyInfo babyInfo = babyInfoRepository.save(BabyInfo.builder()
                .user(user)
                .imageUrl(imageUrl)
                .nickname(babySignupDto.getNickname())
                .build());
        babySignupDto.getInterestCompanies().forEach(
                interest -> interestCompanyRepository.save(InterestCompany.builder()
                                .babyInfo(babyInfo)
                                .interestCompany(interest)
                                .build())
        );
        babySignupDto.getInterestCompanies().forEach(
                interest -> interestDutyRepository.save(InterestDuty.builder()
                        .babyInfo(babyInfo)
                        .interestDuty(interest)
                        .build())
        );
        user.setBabyInfo(babyInfo);
        log.debug("user = {}", user);
    }

    @Transactional
    public void proSignup1(ProSignup1Dto proSignup1Dto) {
        if(userRepository.existsByEmail(proSignup1Dto.getEmail())){
            throw new BizException(UserExceptionType.DUPLICATE_USER);
        }

        Authority authority = authorityRepository
                .findAuthorityByAuthorityName(UserAuth.ROLE_USER).orElseThrow(
                        () -> new BizException(AuthorityExceptionType.NOT_FOUND_AUTHORITY));

        Set<Authority> set = new HashSet<>();
        set.add(authority); // 기본적으로 ROLE_USER 권한 부여
        set.add(authorityRepository.findAuthorityByAuthorityName(UserAuth.ROLE_PRO)
                .orElseThrow(() -> new BizException(AuthorityExceptionType.NOT_FOUND_AUTHORITY)));

        User user = proSignup1Dto.toMember(passwordEncoder, set, false, null);
        ProInfo proInfo = proInfoRepository.save(ProInfo.builder()
                .user(user).company(proSignup1Dto.getCompany())
                .duty(proSignup1Dto.getDuty()).annual(proSignup1Dto.getAnnual())
                .build());
        user.setProInfo(proInfo);
        log.debug("user = {}", user);
    }

    @Transactional
    public void sendEmailCheckCode(String email) throws Exception {
        User user = userRepository.findByEmail(email).get();
        ProInfo proInfo = user.getProInfo();
        proInfo.setEmailCheckCode(emailService.sendSimpleMessage(email));
    }

    @Transactional
    public void checkEmailCheckCode(String email, String emailCheckCode) throws Exception {
        User user = userRepository.findByEmail(email).get();
        ProInfo proInfo = user.getProInfo();
        if (proInfo.getEmailCheckCode().equals(emailCheckCode)){
            proInfo.setEmailCheck(true);
        }else {
            throw new BizException(UserExceptionType.INVAILD_EMAIL_CHECK);
        }
    }

    @Transactional
    public void proCheck(MultipartFile card, ProCheckDto proCheckDto) {
        User user = userRepository.findByEmail(proCheckDto.getEmail()).get();
        ProInfo proInfo = user.getProInfo();

        String extractEmail = OCR.extractEmail(card);
        System.out.println(extractEmail);

        if(proInfo.getEmailCheckCode().equals(proCheckDto.getEmailCheckCode()) && proInfo.getEmailCheck()){
            if(user.getEmail().equals(proCheckDto.getEmail())){
                user.setActivated(true);
                proInfo.setCardCheck(true);
            }else{
                throw new BizException(UserExceptionType.INVAILD_CARD);
            }
        }else{
            throw new BizException(UserExceptionType.INVAILD_EMAIL_CHECK);
        }
    }

    @Transactional
    public void proSignup2(MultipartFile image, ProSignup2Dto proSignup2Dto) {
        User user = userRepository.findByEmail(proSignup2Dto.getEmail()).get();
        ProInfo proInfo = user.getProInfo();

        String imageUrl = RESOURCE_S3 + fileUploadService.save(image).getPath();

        proInfo.setImageUrl(imageUrl);
        proInfo.setNickname(proSignup2Dto.getNickname());
        proInfo.setShortIntro(proSignup2Dto.getShortIntro());
        proInfo.setLongIntro(proSignup2Dto.getLongIntro());
        proInfo.setExpressionExperience(proSignup2Dto.getExpressionExperience());
        proInfo.setExpressionCompany(proSignup2Dto.getExpressionCompany());
        proInfo.setExpressionSpeaking(proSignup2Dto.getExpressionSpeaking());
        proInfo.setExpressionPart(proSignup2Dto.getExpressionPart());
        proInfo.setExpressionStrength(proSignup2Dto.getExpressionStrength());
        log.debug("user = {}", user);
    }

    @Transactional
    public TokenDTO login(LoginReqDTO loginReqDTO){
        CustomEmailPasswordAuthToken customEmailPasswordAuthToken = new CustomEmailPasswordAuthToken(
                loginReqDTO.getEmail(), loginReqDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(customEmailPasswordAuthToken);
        String email = authenticate.getName();
        User user = customUserDetailsService.getUser(email);
        if(user.isActivated()) {

            String accessToken = tokenProvider.createAccessToken(email, user.getAuthorities());
            String refreshToken = tokenProvider.createRefreshToken(email, user.getAuthorities());

            // refreshToken 저장
            refreshTokenRepository.save(
                    RefreshToken.builder()
                            .tokenKey(email)
                            .value(refreshToken)
                            .build()
            );

            return tokenProvider.createTokenDTO(accessToken, refreshToken);
        }else{
            throw new BizException(UserExceptionType.INVAILD_USER);
        }
    }

    @Transactional
    public TokenDTO reissue(TokenReqDTO tokenReqDTO) {
        /**
         * accessToken 은 JWT Filter 에서 검증되고 옴
         */
        String originAccessToken = tokenReqDTO.getAccessToken();
        String originRefreshToken = tokenReqDTO.getRefreshToken();

        // refreshToken 검증
        int refreshTokenFlag = tokenProvider.validateToken(originRefreshToken);

        log.debug("refreshTokenFlag = {}", refreshTokenFlag);

        // refreshToken 검증하고 상황에 맞는 오류를 내보냄
        if(refreshTokenFlag == -1){
            throw new BizException(JwtExceptionType.BAD_TOKEN); // 잘못된 리프레쉬 토큰
        } else if (refreshTokenFlag == 2){
            throw new BizException(JwtExceptionType.REFRESH_TOKEN_EXPIRED); // 유효기간 끝난 토큰
        }

        // 2. Access Token 에서 User Email 가져오기
        Authentication authentication = tokenProvider.getAuthentication(originAccessToken);
        log.debug("Authentication = {}",authentication);

        // 3. 저장소에서 User Email 을 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByTokenKey(authentication.getName())
                .orElseThrow(() -> new BizException(UserExceptionType.LOGOUT_MEMBER)); // 로그아웃된 사용자

        // 4. Refresh Token 일치하는지 검사
        if(!refreshToken.getValue().equals(originRefreshToken)){
            throw new BizException(JwtExceptionType.BAD_TOKEN); // 토큰이 일치하지 않음
        }

        // 5. 새로운 토큰 생성
        String email = tokenProvider.getUserEmailByToken(originAccessToken);
        User user = customUserDetailsService.getUser(email);

        String newAccessToken = tokenProvider.createAccessToken(email, user.getAuthorities());
        String newRefreshToken = tokenProvider.createRefreshToken(email, user.getAuthorities());
        TokenDTO tokenDTO = tokenProvider.createTokenDTO(newAccessToken, newRefreshToken);

        log.debug("refresh Origin = {}", originRefreshToken);
        log.debug("refresh New = {}", newRefreshToken);

        // 6. 저장소 정보 업데이트 (dirtyChecking으로 업데이트)
        refreshToken.updateValue(newRefreshToken);

        // 토큰 발금
        return tokenDTO;
    }

    @Transactional(readOnly = true)
    public LoginRespDto loginData(LoginReqDTO loginReqDTO) {
        User user = userRepository.findByEmail(loginReqDTO.getEmail()).get();
        TokenDTO tokenDTO = login(loginReqDTO);
        return LoginRespDto.builder()
                .uid(user.getUserID())
                .tokenDTO(tokenDTO)
                .build();
    }
}
