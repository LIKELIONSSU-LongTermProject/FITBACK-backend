package com.fitback.ssu.service.question;

import com.fitback.ssu.domain.question.Keyword;
import com.fitback.ssu.domain.question.Question;
import com.fitback.ssu.domain.question.QuestionKeywordMap;
import com.fitback.ssu.domain.question.enums.Part;
import com.fitback.ssu.domain.question.repository.KeywordRepository;
import com.fitback.ssu.domain.question.repository.QuestionKeywordMapRepository;
import com.fitback.ssu.domain.question.repository.QuestionRepository;
import com.fitback.ssu.dto.question.QuestionInfoDto;
import com.fitback.ssu.util.DateUtil;
import com.fitback.ssu.util.QuestionSortComparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {
    private final QuestionRepository questionRepository;
    private final KeywordRepository keywordRepository;
    private final QuestionKeywordMapRepository questionKeywordMapRepository;
    private final KeywordService keywordService;

    @Transactional(readOnly = true)
    public ArrayList<QuestionInfoDto> mainPageInfo(String keyword){
        ArrayList<QuestionInfoDto> questions = new ArrayList<>();
        if(!keyword.equals("dev") && !keyword.equals("design") && !keyword.equals("pm")){
            Keyword keyWord = keywordRepository.findByKeyword(keyword).get();
            ArrayList<QuestionKeywordMap> questionKeywordMaps = questionKeywordMapRepository.findByKeyword(keyWord);
            questionKeywordMaps.forEach(
                    questionKeywordMap -> {
                        try {
                            questions.add(
                                    QuestionInfoDto.builder()
                                            .qId(questionKeywordMap.getQuestion().getQId())
                                            .part(questionKeywordMap.getQuestion().getPart())
                                            .deadLine(DateUtil.calDeadLine(questionKeywordMap.getQuestion().getEndTime(), questionKeywordMap.getQuestion().getStartTime()))
                                            .questionName(questionKeywordMap.getQuestion().getQName())
                                            .reference(questionKeywordMap.getQuestion().getQuestionReference())
                                            .contents(questionKeywordMap.getQuestion().getQContent())
                                            .keywords(keywordService.makeKeywords(questionKeywordMap.getQuestion().getQId()))
                                            .build()
                            );
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
            Collections.sort(questions, new QuestionSortComparator()); // 마감기한 얼마 안 남은 순으로 정렬
            return questions;
        }
        else{
            return partQuestions(keyword);
        }
    }

    @Transactional(readOnly = true)
    public ArrayList<QuestionInfoDto> partQuestions(String keyword){
        ArrayList<QuestionInfoDto> questions = new ArrayList<>();
        switch (keyword){
            case "dev":
                ArrayList<Question> frontQuestions = questionRepository.findByPartOrderByEndTimeDesc(Part.DEV_FRONT);
                ArrayList<Question> backQuestions = questionRepository.findByPartOrderByEndTimeDesc(Part.DEV_BACK);
                questions = makeArrayList(frontQuestions);
                questions.addAll(makeArrayList(backQuestions));
                break;
            case "design":
                ArrayList<Question> designQuestions = questionRepository.findByPartOrderByEndTimeDesc(Part.DESIGN);
                questions = makeArrayList(designQuestions);
                break;
            case "pm":
                ArrayList<Question> pmQuestions = questionRepository.findByPartOrderByEndTimeDesc(Part.PM);
                questions = makeArrayList(pmQuestions);
                break;
            default:
                break;
        }
        Collections.sort(questions, new QuestionSortComparator()); // 마감기한 얼마 안 남은 순으로 정렬
        return questions;
    }

    @Transactional(readOnly = true)
    public ArrayList<QuestionInfoDto> newQuestions(){
        ArrayList<Question> q = new ArrayList<>();
        ArrayList<Question> list = questionRepository.findTop5();
        for(int i=0 ; i<5 ; i++){
            q.add(list.get(i));
        }
        ArrayList<QuestionInfoDto> questions = makeArrayList(q);
        Collections.sort(questions, new QuestionSortComparator());
        return questions;
    }
    public ArrayList<QuestionInfoDto> makeArrayList(ArrayList<Question> list){
        ArrayList<QuestionInfoDto> questions = new ArrayList<>();
        list.forEach(
                l -> {
                    try {
                        questions.add(QuestionInfoDto.builder()
                                .qId(l.getQId())
                                .part(l.getPart())
                                .deadLine(DateUtil.calDeadLine(l.getEndTime(), l.getStartTime()))
                                .questionName(l.getQName())
                                .reference(l.getQuestionReference())
                                .contents(l.getQContent())
                                .keywords(keywordService.makeKeywords(l.getQId()))
                                .build());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return questions;
    }

}
