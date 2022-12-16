package com.fitback.ssu.controller.pro;

import com.fitback.ssu.dto.answer.AnswerResDto;
import com.fitback.ssu.dto.question.QuestionsDto;
import com.fitback.ssu.service.data.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/pro/fitback")
public class ProMyFitBackController {
    private final AnswerService answerService;

    /**
     * 질문 요청 온거 확인 탭
     * @param state request, progress, complete
     */
    @GetMapping(value = "/tab")
    public ArrayList<QuestionsDto> getRequestList(@RequestParam(value = "state") String state){
        return answerService.proFitbackTab(state);
    }

    @PostMapping(value = "/accept")
    public void acceptRequest(
            @RequestParam(value = "qID") Long qID, @RequestParam(value = "ok") Boolean ok){
        answerService.acceptRequest(qID, ok);
    }

    @PostMapping(value = "/feedback")
    public void feedback(
            @RequestParam(value = "qID") Long qID, @RequestBody AnswerResDto feedback){
        answerService.updateFeedback(qID, feedback);
    }

}
