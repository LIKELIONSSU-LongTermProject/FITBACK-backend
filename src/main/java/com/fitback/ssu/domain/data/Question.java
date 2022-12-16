package com.fitback.ssu.domain.data;

import com.fitback.ssu.domain.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Question")
@Entity
public class Question {
    @Column(name = "q_id")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long qId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questioner")
    private User questioner;

//    @OneToOne(mappedBy = "question",
//            cascade = CascadeType.ALL, orphanRemoval = true)
//    private AnswerRequest answerRequest;

    @OneToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @OneToMany(mappedBy = "question",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "feedback_type")
    private String feedbackType;

    @Column(name = "price")
    private Integer price;

    @Column(name = "question_reference")
    private String questionReference;

    @Column(name = "q_content", nullable = false)
    private String qContent;

    @Column(name = "is_rejected")
    private Boolean isRejected;
    @Column(name = "is_accepted")
    private Boolean isAccepted;
    @Column(name = "is_complete")
    private Boolean isComplete;

    @CreatedDate
    @Column(name = "created_at",updatable = false, nullable = false)
    private LocalDateTime createdAt;


    @Builder
    public Question(User questioner, Answer answer,
                    String feedbackType, Integer price,
                    String questionReference, String qContent, LocalDateTime createdAt,
                    Boolean isRejected, Boolean isAccepted, Boolean isComplete) {
        this.questioner = questioner;
        this.answer = answer;
        this.feedbackType = feedbackType;
        this.price = price;
        this.questionReference = questionReference;
        this.qContent = qContent;
        this.createdAt = createdAt;
        this.isRejected = isRejected;
        this.isAccepted = isAccepted;
        this.isComplete = isComplete;
    }
}
