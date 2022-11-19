package com.fitback.ssu.service.user;

import com.fitback.ssu.domain.answer.Answer;
import com.fitback.ssu.domain.answer.AnswerRepository;
import com.fitback.ssu.domain.question.Question;
import com.fitback.ssu.domain.question.repository.QuestionRepository;
import com.fitback.ssu.domain.user.User;
import com.fitback.ssu.domain.user.UserRepository;
import com.fitback.ssu.dto.question.QuestionBoxOneDto;
import com.fitback.ssu.dto.question.QuestionCompleteBoxDto;
import com.fitback.ssu.dto.question.QuestionOnProgressBoxDto;
import com.fitback.ssu.exception.BizException;
import com.fitback.ssu.exception.UserExceptionType;
import com.fitback.ssu.util.DateUtil;
import com.fitback.ssu.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionBoxService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Transactional(readOnly = true)
    public ArrayList<?> questionBoxPage(Boolean isProgress){
        User user = userRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new BizException(UserExceptionType.NOT_FOUND_USER));
        if(isProgress){
            ArrayList<QuestionOnProgressBoxDto> questionOnProgressBoxDto = new ArrayList<>();
            ArrayList<Question> list = questionRepository.findAllByIsCompleteAndUser(true, user);
            list.forEach(
                    question -> {
                        try {
                            questionOnProgressBoxDto.add(QuestionOnProgressBoxDto.builder()
                                    .qID(question.getQId())
                                    .deadLine(DateUtil.calDeadLine(question.getEndTime(),question.getStartTime()))
                                    .qName(question.getQName())
                                    .qContent(question.getQContent())
                                    .qRefer(question.getQuestionReference())
                                    .permitReqCnt(question.getReqPermissionCount())
                                    .build());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    });
            return questionOnProgressBoxDto;
        }else {
            ArrayList<QuestionCompleteBoxDto> questionCompleteBoxDto = new ArrayList<>();
            ArrayList<Question> list = questionRepository.findAllByIsCompleteAndUser(false, user);
            list.forEach(
                    question -> {
                        questionCompleteBoxDto.add(QuestionCompleteBoxDto.builder()
                                    .qID(question.getQId())
                                    .sTime(question.getStartTime())
                                    .qName(question.getQName())
                                    .qContent(question.getQContent())
                                    .qRefer(question.getQuestionReference())
                                    .answerCount(question.getAnswers().size())
                                    .build());
                    });
            return questionCompleteBoxDto;
        }
    }

    @Transactional(readOnly = true)
    public QuestionBoxOneDto checkAnswerReqs(Long qID){
        Question question = questionRepository.findById(qID).get();
        ArrayList<Answer> answers = answerRepository.findAllByIsPermittedAndQuestion(false, question);
        ArrayList<QuestionBoxOneDto.AnsReq> ansReqs = new ArrayList<>();
        answers.forEach(
                answer -> {
                    ansReqs.add(QuestionBoxOneDto.AnsReq.builder()
                                    .aID(answer.getAnswerId())
                                    .username(answer.getWriter().getUsername())
                                    .ansCount(answer.getWriter().getAnswers().size())
                            .build());
                }
        );
        return QuestionBoxOneDto.builder().ansReqs(ansReqs).build();
    }
}
