package com.fitback.ssu.domain.answer;

import com.fitback.ssu.domain.question.Question;
import com.fitback.ssu.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Answer")
@Entity
public class Answer {
    @Column(name = "answer_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "q_id")
    private Question question;

    @Column(name = "is_permitted", nullable = false)
    private Boolean isPermitted;

    @Column(name = "answer_reference", nullable = false)
    private String answerReference;

    @Column(name = "answer_content", nullable = false)
    private String answerContent;

    @Column(name = "answer_time", nullable = false)
    @CreatedDate
    private String answerTime;

    @PrePersist
    public void onPrePersist(){
        this.answerTime = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }


    @Builder
    public Answer(User writer, Question question,Boolean isPermitted, String answerReference, String answerContent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedNow = LocalDate.now().format(formatter);
        this.writer = writer;
        this.question = question;
        this.isPermitted = false;
        this.answerReference = answerReference;
        this.answerContent = answerContent;
        this.answerTime = formattedNow;
    }
}
