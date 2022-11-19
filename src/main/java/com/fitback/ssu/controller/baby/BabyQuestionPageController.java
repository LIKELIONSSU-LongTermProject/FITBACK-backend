package com.fitback.ssu.controller.baby;

import com.fitback.ssu.dto.question.QuestionBoxOneDto;
import com.fitback.ssu.service.answer.AnswerService;
import com.fitback.ssu.service.user.QuestionBoxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/baby")
public class BabyQuestionPageController {

    private final AnswerService answerService;
    private final QuestionBoxService questionBoxService;

    /**
     * 내 질문함
     * @param isProgress
     * @return
     */
    @GetMapping(value = "/qbox")
    public ArrayList<?> getQuestionList(@RequestParam(value = "is_progress") Boolean isProgress){
        return questionBoxService.questionBoxPage(isProgress);
    }

    /**
     * 내 질문함에서 하나 클릭
     * @param qID
     * @return
     */
    @GetMapping(value = "/qbox/one")
    public QuestionBoxOneDto getQuestionOne(@RequestParam(value = "qID") Long qID){
        return questionBoxService.checkAnswerReqs(qID);
    }

    /**
     * 답변 권한 주기
     * @param aID
     */
    @PutMapping(value = "/qbox/one")
    public void grantAnswerPermission(@RequestParam(value = "aID") Long aID){
        answerService.acceptAnswer(aID);
    }
}
