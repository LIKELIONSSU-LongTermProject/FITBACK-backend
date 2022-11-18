package com.fitback.ssu.controller.baby;

import com.fitback.ssu.dto.question.QuestionInfoDto;
import com.fitback.ssu.dto.question.QuestionRegisterDto;
import com.fitback.ssu.service.question.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/question")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("")
    public void registerQuestion(@RequestBody QuestionRegisterDto questionRegisterDto){
        questionService.registerQuestion(questionRegisterDto);
    }

    @GetMapping("/{qID}")
    public QuestionInfoDto getOneQuestion(@PathVariable Long qID) throws ParseException {
        return questionService.getOneQuestion(qID);
    }
}
