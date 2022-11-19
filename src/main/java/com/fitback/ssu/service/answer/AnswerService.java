package com.fitback.ssu.service.answer;

import com.fitback.ssu.domain.answer.Answer;
import com.fitback.ssu.domain.answer.AnswerRepository;
import com.fitback.ssu.domain.question.Question;
import com.fitback.ssu.domain.question.repository.QuestionRepository;
import com.fitback.ssu.domain.user.User;
import com.fitback.ssu.domain.user.UserRepository;
import com.fitback.ssu.dto.answer.AnswerResDto;
import com.fitback.ssu.exception.BizException;
import com.fitback.ssu.exception.UserExceptionType;
import com.fitback.ssu.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public void requestAnswerAuthority(Long qID){
        User user = userRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new BizException(UserExceptionType.NOT_FOUND_USER));
        Question question = questionRepository.findById(qID).get();
        question.setAnswerCount(question.getReqPermissionCount()+1);
        answerRepository.save(
                Answer.builder()
                        .writer(user)
                        .question(question)
                        .build()
        );
    }

    @Transactional
    public void acceptAnswer(Long aID){
        Answer answer = answerRepository.findById(aID).get();
        answer.setIsPermitted(true);
    }

    @Transactional
    public void registerAnswer(Long qID, Long aID, AnswerResDto answerResDto){
        Question question = questionRepository.findById(qID).get();
        question.setAnswerCount(question.getAnswerCount()+1);
        question.setAnswerCount(question.getReqPermissionCount()-1);

        Answer answer = answerRepository.findById(aID).get();
        answer.setAnswerContent(answerResDto.getContents());
        answer.setAnswerReference(answerResDto.getReference());
    }
}
