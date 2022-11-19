package com.fitback.ssu.controller.pro;

import com.fitback.ssu.dto.answer.AnswerResDto;
import com.fitback.ssu.service.answer.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/answer")
public class AnswerController {
    private final AnswerService answerService;

    /**
     * 질문 답변 권한 요청
     * @param qID
     */
    @PostMapping("")
    public void requestPermissionForQuestion(@RequestParam(value = "qID") Long qID){
        answerService.requestAnswerAuthority(qID);
    }

    /**
     * 답변 작성하기
     * @param qID
     * @param aID
     * @param answerResDto
     */
    @PutMapping("")
    public void answerToQuestion(
            @RequestParam(value = "qID") Long qID, @RequestParam(value = "aID") Long aID,
            @RequestBody AnswerResDto answerResDto){
        answerService.registerAnswer(qID, aID, answerResDto);
    }


    //TODO 답변 가능한거 리스트 확인하기
}
