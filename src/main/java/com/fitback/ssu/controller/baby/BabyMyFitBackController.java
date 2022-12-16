package com.fitback.ssu.controller.baby;

import com.fitback.ssu.dto.question.QuestionRegisterDto;
import com.fitback.ssu.dto.question.QuestionsDto;
import com.fitback.ssu.dto.question.ReviewDto;
import com.fitback.ssu.service.data.AnswerService;
import com.fitback.ssu.service.data.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/baby/fitback")
public class BabyMyFitBackController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    /**
     *
     * @param state request, progress, complete
     * @return
     */
    @GetMapping(value = "/tab")
    public ArrayList<QuestionsDto> getQuestionList(@RequestParam(value = "state") String state){
        return questionService.babyFitbackTab(state);
    }

    /**
     * 내 질문함에서 하나 클릭
     * @param qID
     * @return
     */
    @GetMapping(value = "/qbox/one")
    public QuestionsDto getQuestionOne(@RequestParam(value = "qID") Long qID){
        return questionService.getOneQuestion(qID);
    }

    @PostMapping(value = "/request")
    public void makeQuestion(@RequestBody QuestionRegisterDto questionRegisterDto){
        questionService.registerQuestion(questionRegisterDto);
    }

    @PostMapping(value = "/value")
    public void evaluateFeedback(@RequestBody ReviewDto reviewDto){
        questionService.evaluateFeedback(reviewDto);
    }

}
