//package com.fitback.ssu.domain.data;
//
//import com.fitback.ssu.domain.user.User;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "answer_request")
//@Entity
//public class AnswerRequest {
//
//    @Column(name = "answer_request_id")
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long answerRequestId;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User answerer;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "q_id")
//    private Question question;
//
//    @Builder
//    public AnswerRequest(User answerer, Question question) {
//        this.answerer = answerer;
//        this.question = question;
//    }
//}
