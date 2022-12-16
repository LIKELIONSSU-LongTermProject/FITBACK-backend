package com.fitback.ssu.controller.auth;

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
import com.fitback.ssu.service.auth.AuthService;
import com.fitback.ssu.service.user.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/signup")
    public UserRespDTO signup(@RequestBody UserReqDTO userReqDTO, HttpServletRequest req) {
        System.out.println(req.getRequestURL());
        log.debug("userReqDto = {}", userReqDTO);
        return authService.signup(userReqDTO);
    }

    @PostMapping("/signup/baby")
    public void babySignup(
            @RequestPart("image") MultipartFile image,
            @RequestPart("babySignupDto") BabySignupDto babySignupDto, HttpServletRequest req) {
        System.out.println(req.getRequestURL());
        log.debug("babyUserSignupDto = {}", babySignupDto);
        authService.babySignup(image, babySignupDto);
    }

    @PostMapping("/signup1/pro")
    public void proSignup1(
            @RequestBody ProSignup1Dto proSignup1Dto, HttpServletRequest req) {
        System.out.println(req.getRequestURL());
        log.debug("proUserSignupDto = {}", proSignup1Dto);
        authService.proSignup1(proSignup1Dto);
    }

    @PostMapping("/email")
    public void sendEmailCode(@RequestParam String email) throws Exception {
        authService.sendEmailCheckCode(email);
    }

    @PostMapping("/email/check")
    public void sendEmailCode(@RequestParam String email, @RequestParam String checkCode) throws Exception {
        authService.checkEmailCheckCode(email, checkCode);
    }

    @PostMapping("/confirmation")
    public void confirmPro(
            @RequestPart("image") MultipartFile card,
            @RequestPart(value = "proCheckDto") ProCheckDto proCheckDto, HttpServletRequest req) {
        System.out.println(req.getRequestURL());
        log.debug("proCheckDto = {}", proCheckDto);
        authService.proCheck(card, proCheckDto);
    }

    @PostMapping("/signup2/pro")
    public void proSignup2(
            @RequestPart("image") MultipartFile image,
            @RequestPart(value = "proSignup2Dto") ProSignup2Dto proSignup2Dto, HttpServletRequest req) {
        System.out.println(req.getRequestURL());
        log.debug("proUserSignupDto = {}", proSignup2Dto);
        authService.proSignup2(image, proSignup2Dto);
    }

    @PostMapping("/login")
    public LoginRespDto login(@RequestBody LoginReqDTO loginReqDTO){
        return authService.loginData(loginReqDTO);
    }

    @PostMapping("/reissue")
    public TokenDTO reissue(@RequestBody TokenReqDTO tokenReqDTO){
        return authService.reissue(tokenReqDTO);
    }
}
