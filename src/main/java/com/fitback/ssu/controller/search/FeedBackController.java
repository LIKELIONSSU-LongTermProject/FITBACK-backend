package com.fitback.ssu.controller.search;

import com.fitback.ssu.dto.question.QuestionInfoDto;
import com.fitback.ssu.service.question.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/feedback")
public class FeedBackController {
    private final SearchService searchService;
    @GetMapping(value = "/new")
    public ArrayList<QuestionInfoDto> designQuestions(){
        return searchService.newQuestions();
    }
//    @GetMapping(value = "/order/starttime")
//    @GetMapping(value = "/order/endtime")
//    @GetMapping(value = "/order/like")
}
