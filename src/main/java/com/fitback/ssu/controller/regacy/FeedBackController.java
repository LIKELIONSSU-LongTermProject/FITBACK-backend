//package com.fitback.ssu.controller.regacy;
//
//import com.fitback.ssu.dto.question.QuestionInfoDto;
//import com.fitback.ssu.service.question.SortService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(value = "/feedback")
//public class FeedBackController {
//    private final SortService sortService;
//
//    /**
//     * 피드백 페이지에서 최신 질문 5개 가져오기
//     * @return
//     */
//    @GetMapping(value = "/new")
//    public ArrayList<QuestionInfoDto> newQuestions(){
//        return sortService.newQuestions();
//    }
//}
