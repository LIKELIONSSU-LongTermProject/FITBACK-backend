package com.fitback.ssu.controller.user;

import com.fitback.ssu.dto.user.UserRespDTO;
import com.fitback.ssu.dto.user.UserUpdateDTO;
import com.fitback.ssu.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserRespDTO getMyInfo(){
        return userService.getMyInfo();
    }

    @GetMapping(value = "/info/{email}")
    public UserRespDTO getUserInfo(@RequestParam String email){
        return userService.getUserInfo(email);
    }

    @PutMapping("")
    public void updateUser(@RequestBody UserUpdateDTO dto){
        userService.updateUserInfo(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admintest")
    public String adminTest(){
        return "ADMIN OK!!";
    }
}
