//package com.fitback.ssu.controller.baby;
//
//import com.fitback.ssu.dto.question.QuestionInfoDto;
//import com.fitback.ssu.dto.question.QuestionRegisterDto;
//import com.fitback.ssu.service.question.QuestionService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//import java.text.ParseException;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(value = "/question")
//public class QuestionController {
//
//    private final QuestionService questionService;
//
//    /**
//     * 질문 등록하기
//     * @param questionRegisterDto
//     */
//    @PostMapping("")
//    public void registerQuestion(@RequestBody QuestionRegisterDto questionRegisterDto){
//        questionService.registerQuestion(questionRegisterDto);
//    }
//
//    /**
//     * 메인 화면에서 질문하나 클릭했을 때 -> 자신의 것이면 아래쪽 블러x, 아니면 아래쪽 블러o
//     * @param qID
//     * @return
//     * @throws ParseException
//     */
//    @GetMapping("/{qID}")
//    public QuestionInfoDto getOneQuestion(@PathVariable Long qID) throws ParseException {
//        return questionService.getOneQuestion(qID);
//    }
//
//    //TODO 질문에 해당하는 답변 리스트
//}
