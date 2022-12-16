package com.fitback.ssu.controller.user;

import com.fitback.ssu.dto.answer.CommentDto;
import com.fitback.ssu.dto.user.UserRespDTO;
import com.fitback.ssu.dto.user.UserUpdateDTO;
import com.fitback.ssu.service.data.AnswerService;
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
    private final AnswerService answerService;

    private final UserService userService;

    @GetMapping
    public UserRespDTO getMyInfo(){
        return userService.getMyInfo();
    }

    @PostMapping(value = "/comment")
    public void feedback(
            @RequestParam(value = "qID") Long qID, @RequestBody CommentDto comment){
        answerService.writeComment(qID, comment);
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
