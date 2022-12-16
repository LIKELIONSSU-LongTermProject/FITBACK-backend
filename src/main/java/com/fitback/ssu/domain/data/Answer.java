package com.fitback.ssu.domain.data;

import com.fitback.ssu.domain.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Answer")
@Entity
public class Answer {
    @Column(name = "answer_id")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answerer")
    private User answerer;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "answer")
    private Question question;

    @Column(name = "answer_reference")
    private String answerReference;

    @Column(name = "answer_content")
    private String answerContent;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


    @Builder
    public Answer(User answerer, Question question, String answerReference, String answerContent, LocalDateTime createdAt) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//        String formattedNow = LocalDate.now().format(formatter);
        this.answerer= answerer;
        this.question = question;
        this.answerReference = answerReference;
        this.answerContent = answerContent;
        this.createdAt = createdAt;
    }
}
