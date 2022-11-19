package com.fitback.ssu.service.question;

import com.fitback.ssu.domain.question.Keyword;
import com.fitback.ssu.domain.question.Question;
import com.fitback.ssu.domain.question.QuestionKeywordMap;
import com.fitback.ssu.domain.question.repository.KeywordRepository;
import com.fitback.ssu.domain.question.repository.QuestionKeywordMapRepository;
import com.fitback.ssu.domain.question.repository.QuestionRepository;
import com.fitback.ssu.domain.user.User;
import com.fitback.ssu.domain.user.UserRepository;
import com.fitback.ssu.dto.question.QuestionInfoDto;
import com.fitback.ssu.dto.question.QuestionRegisterDto;
import com.fitback.ssu.exception.BizException;
import com.fitback.ssu.exception.UserExceptionType;
import com.fitback.ssu.util.DateUtil;
import com.fitback.ssu.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final KeywordRepository keywordRepository;
    private final QuestionKeywordMapRepository questionKeywordMapRepository;
    private final KeywordService keywordService;

    @Transactional
    public void registerQuestion(QuestionRegisterDto questionRegisterDto){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedNow = LocalDate.now().format(formatter);
        User user = userRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new BizException(UserExceptionType.NOT_FOUND_USER));
        Question question = questionRepository.save(Question.builder()
                .part(questionRegisterDto.getPart()).user(user)
                .questionReference(questionRegisterDto.getReference())
                .qName(questionRegisterDto.getQuestionName())
                .qContent(questionRegisterDto.getContent())
                .isPublic(questionRegisterDto.getIsPublic())
                .startTime(formattedNow)
                .endTime(questionRegisterDto.getEndTime())
                .build());
        Set<String> keywords = questionRegisterDto.getKeywords();
        for(String keyword : keywords){
            if(keywordRepository.findByKeyword(keyword).isEmpty()){
                keywordRepository.save(Keyword.builder().keyword(keyword).build());
                questionKeywordMapRepository.save(QuestionKeywordMap.builder()
                                .question(question)
                                .keyword(keywordRepository.findByKeyword(keyword).get())
                                .build());
            } else if (keywordRepository.findByKeyword(keyword).isPresent()) {
                questionKeywordMapRepository.save(QuestionKeywordMap.builder()
                        .question(question)
                        .keyword(keywordRepository.findByKeyword(keyword).get())
                        .build());
            }
        }
    }

    @Transactional(readOnly = true)
    public QuestionInfoDto getOneQuestion(Long id) throws ParseException {
        Question question = questionRepository.findById(id).get();
        Set<String> keywords = keywordService.makeKeywords(id);
        User user = userRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new BizException(UserExceptionType.NOT_FOUND_USER));
        return QuestionInfoDto.builder()
                .isOwn(user.getUserID().equals(question.getUser().getUserID()))
                .qId(question.getQId())
                .part(question.getPart())
                .deadLine(DateUtil.calDeadLine(question.getEndTime(), question.getStartTime()))
                .questionName(question.getQName())
                .reference(question.getQuestionReference())
                .contents(question.getQContent())
                .keywords(keywords)
                .build();
    }

}
