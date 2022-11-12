package com.fitback.ssu.controller.auth;

import com.fitback.ssu.dto.jwt.TokenDTO;
import com.fitback.ssu.dto.jwt.TokenReqDTO;
import com.fitback.ssu.dto.login.LoginReqDTO;
import com.fitback.ssu.dto.user.UserReqDTO;
import com.fitback.ssu.dto.user.UserRespDTO;
import com.fitback.ssu.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public UserRespDTO signup(@RequestBody UserReqDTO userReqDTO, HttpServletRequest req) {
        System.out.println(req.getRequestURL());
        log.debug("userReqDto = {}", userReqDTO);
        return authService.signup(userReqDTO);
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginReqDTO loginReqDTO){
        return authService.login(loginReqDTO);
    }

    @PostMapping("/reissue")
    public TokenDTO reissue(@RequestBody TokenReqDTO tokenReqDTO){
        return authService.reissue(tokenReqDTO);
    }
}
