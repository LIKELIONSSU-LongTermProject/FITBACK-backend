package com.fitback.ssu.service.data;

import com.fitback.ssu.domain.data.*;
import com.fitback.ssu.domain.user.User;
import com.fitback.ssu.domain.user.UserRepository;
import com.fitback.ssu.domain.user.info.ProInfoRepository;
import com.fitback.ssu.dto.answer.AnswerResDto;
import com.fitback.ssu.dto.answer.CommentDto;
import com.fitback.ssu.dto.question.QuestionsDto;
import com.fitback.ssu.exception.BizException;
import com.fitback.ssu.exception.UserExceptionType;
import com.fitback.ssu.util.DateUtil;
import com.fitback.ssu.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;
    private final ProInfoRepository proInfoRepository;
//    private final AnswerRequestRepository answerRequestRepository;

    @Transactional(readOnly = true)
    public ArrayList<QuestionsDto> proFitbackTab(String state){
        User user = userRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new BizException(UserExceptionType.NOT_FOUND_USER));

        ArrayList<QuestionsDto> questionsDtos = new ArrayList<>();
        if(state.equals("complete")){
            ArrayList<Question> list = questionRepository.findAllByIsCompleteAndIsAcceptedAndAnswer_Answerer(true, true, user);
            list.forEach(
                    question -> {
                        questionsDtos.add(QuestionsDto.builder()
                                .questionerNickname(question.getQuestioner().getBabyInfo().getNickname())
                                .qID(question.getQId())
                                .feedbackType(question.getFeedbackType())
                                .price(question.getPrice())
                                .qContent(question.getQContent())
                                .qRefer(question.getQuestionReference())
                                .createdAt(DateUtil.dateTimeToString(question.getCreatedAt()))
                                .isRejected(question.getIsRejected())
                                .feedback(null)
                                .comments(null)
                                .build());
                    });
        }else if(state.equals("progress")){
            ArrayList<Question> list = questionRepository.findAllByIsCompleteAndIsAcceptedAndAnswer_Answerer(false,true, user);
            list.forEach(
                    question -> {
                        questionsDtos.add(QuestionsDto.builder()
                                .qID(question.getQId())
                                .questionerNickname(question.getQuestioner().getBabyInfo().getNickname())
                                .feedbackType(question.getFeedbackType())
                                .price(question.getPrice())
                                .qContent(question.getQContent())
                                .qRefer(question.getQuestionReference())
                                .createdAt(DateUtil.dateTimeToString(question.getCreatedAt()))
                                .isRejected(question.getIsRejected())
                                .feedback(null)
                                .comments(null)
                                .build());
                    });
        }else {
            ArrayList<Question> list = questionRepository.findAllByIsCompleteAndIsAcceptedAndAnswer_Answerer(false, false, user);
            list.forEach(
                    question -> {
                        questionsDtos.add(QuestionsDto.builder()
                                .qID(question.getQId())
                                .questionerNickname(question.getQuestioner().getBabyInfo().getNickname())
                                .feedbackType(question.getFeedbackType())
                                .price(question.getPrice())
                                .qContent(question.getQContent())
                                .qRefer(question.getQuestionReference())
                                .createdAt(DateUtil.dateTimeToString(question.getCreatedAt()))
                                .isRejected(question.getIsRejected())
                                .feedback(null)
                                .comments(null)
                                .build());
                    });
        }
        return questionsDtos;
    }

    @Transactional
    public void acceptRequest(Long qID, Boolean ok){
        Question question = questionRepository.findByqId(qID);
        question.setIsRejected(!ok);
        question.setIsAccepted(ok);
    }

    @Transactional
    public void updateFeedback(Long qID, AnswerResDto feedback){
        Question question = questionRepository.findByqId(qID);
        question.setIsComplete(true);
        Answer answer = question.getAnswer();
        answer.setAnswerContent(feedback.getContents());
        answer.setAnswerReference(feedback.getReference());
        answer.setCreatedAt(LocalDateTime.now());
    }

    @Transactional
    public void writeComment(Long qID, CommentDto comment){
        User user = userRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new BizException(UserExceptionType.NOT_FOUND_USER));
        Question question = questionRepository.findByqId(qID);
        if(question.getQuestioner().equals(user)){
            commentRepository.save(Comment.builder()
                    .commenterImage(user.getBabyInfo().getImageUrl())
                    .nickname(user.getBabyInfo().getNickname())
                    .content(comment.getContents())
                    .question(question)
                    .createdAt(LocalDateTime.now())
                    .build());
        }else {
            commentRepository.save(Comment.builder()
                    .commenterImage(user.getProInfo().getImageUrl())
                    .nickname(user.getProInfo().getNickname())
                    .content(comment.getContents())
                    .question(question)
                    .createdAt(LocalDateTime.now())
                    .build());
        }
    }
}
