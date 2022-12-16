package com.fitback.ssu.service.data;

import com.fitback.ssu.domain.data.*;
import com.fitback.ssu.domain.user.User;
import com.fitback.ssu.domain.user.UserRepository;
import com.fitback.ssu.domain.user.info.ProInfo;
import com.fitback.ssu.domain.user.info.ProInfoRepository;
import com.fitback.ssu.dto.question.QuestionRegisterDto;
import com.fitback.ssu.dto.question.QuestionsDto;
import com.fitback.ssu.dto.question.ReviewDto;
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
public class QuestionService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ProInfoRepository proInfoRepository;
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public ArrayList<QuestionsDto> babyFitbackTab(String state){
        User user = userRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new BizException(UserExceptionType.NOT_FOUND_USER));
        ArrayList<QuestionsDto> questionsDtos = new ArrayList<>();

        if(state.equals("complete")){
            ArrayList<Question> list = questionRepository.findAllByIsCompleteAndIsAcceptedAndQuestioner(true, true, user);
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
        }else if(state.equals("progress")){
            ArrayList<Question> list = questionRepository.findAllByIsCompleteAndIsAcceptedAndQuestioner(false,true, user);
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
            ArrayList<Question> list = questionRepository.findAllByIsCompleteAndIsAcceptedAndQuestioner(false, false, user);
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

    @Transactional(readOnly = true)
    public QuestionsDto getOneQuestion(Long qID){
        Question question = questionRepository.findByqId(qID);
        if (question.getIsComplete()){
            Answer answer = answerRepository.findByAnswerId(question.getAnswer().getAnswerId()).get();
            ProInfo proInfo = proInfoRepository.findByProInfoId(answer.getAnswerer().getProInfo().getProInfoId());
            ArrayList<Comment> comments = commentRepository.findByQuestionOrderByCreatedAtAsc(question);
            ArrayList<QuestionsDto.Comment> commentList = new ArrayList<>();
            comments.forEach(
                    comment -> {
                        commentList.add(QuestionsDto.Comment.builder()
                                .image(comment.getCommenterImage())
                                .nickname(comment.getNickname())
                                .content(comment.getContent())
                                .createdAt(DateUtil.dateTimeToString(comment.getCreatedAt()))
                                .build());
                    }
            );
            QuestionsDto.Feedback feedback = QuestionsDto.Feedback.builder()
                    .nickname(proInfo.getNickname()).pImage(proInfo.getImageUrl())
                    .company(proInfo.getCompany()).duty(proInfo.getDuty()).annual(proInfo.getAnnual())
                    .reviewCnt(proInfo.getReviewNum()).satisfactionRatio(proInfo.getSatisfactionRatio())
                    .answerContent(answer.getAnswerContent())
                    .referenceUrl(answer.getAnswerReference())
                    .build();
            return QuestionsDto.builder()
                    .qID(question.getQId())
                    .questionerNickname(question.getQuestioner().getBabyInfo().getNickname())
                    .feedbackType(question.getFeedbackType())
                    .createdAt(DateUtil.dateTimeToString(question.getCreatedAt()))
                    .price(question.getPrice())
                    .qContent(question.getQContent())
                    .qRefer(question.getQuestionReference())
                    .feedback(feedback)
                    .comments(commentList)
                    .build();
        }else{
            return QuestionsDto.builder()
                    .qID(question.getQId())
                    .questionerNickname(question.getQuestioner().getBabyInfo().getNickname())
                    .feedbackType(question.getFeedbackType())
                    .price(question.getPrice())
                    .qContent(question.getQContent())
                    .qRefer(question.getQuestionReference())
                    .feedback(null)
                    .comments(null)
                    .build();
        }
    }

    @Transactional
    public void registerQuestion(QuestionRegisterDto questionRegisterDto) {
        User user = userRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new BizException(UserExceptionType.NOT_FOUND_USER));
        ProInfo proInfo = proInfoRepository.findByProInfoId(questionRegisterDto.getProId());
        User answerer = userRepository.findByUserID(proInfo.getUser().getUserID()).get();
        Answer answer = answerRepository.save(Answer.builder()
                        .answerer(answerer)
                        .createdAt(LocalDateTime.now())
                        .build());
        questionRepository.save(Question.builder()
                        .questioner(user)
                        .answer(answer)
                        .feedbackType(questionRegisterDto.getQuestionType())
                        .price(questionRegisterDto.getPrice())
                        .questionReference(questionRegisterDto.getReferenceUrl())
                        .qContent(questionRegisterDto.getContent())
                        .createdAt(LocalDateTime.now())
                        .isRejected(false)
                        .isAccepted(false)
                        .isComplete(false)
                        .build());
    }

    @Transactional
    public void evaluateFeedback(ReviewDto reviewDto) {
        User user = userRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new BizException(UserExceptionType.NOT_FOUND_USER));
        Question question = questionRepository.findByqId(reviewDto.getQuestionId());
        ProInfo proInfo = question.getAnswer().getAnswerer().getProInfo();
        Integer preReviewNum = proInfo.getReviewNum();
        proInfo.setReviewNum(preReviewNum+1);

        Integer preSatisfactionRatio = proInfo.getSatisfactionRatio();
        proInfo.setSatisfactionRatio((preSatisfactionRatio*preReviewNum+reviewDto.getSatisfaction())
                                        /(preReviewNum+1));
        reviewRepository.save(Review.builder()
                        .proInfo(proInfo)
                        .babyInfo(user.getBabyInfo())
                        .review(reviewDto.getReview())
                        .satisfaction(reviewDto.getSatisfaction())
                        .createdAt(LocalDateTime.now())
                        .build());
    }
}
