package com.fitback.ssu.controller.user;


import com.fitback.ssu.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mypage")
public class MyPageController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_BABY') or hasRole('ROLE_PRO')")
    @GetMapping("")
    public void mypageInfo(){
//        return userService.getMyInfo();
    }
}
