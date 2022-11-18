package com.fitback.ssu.domain.question;

import com.fitback.ssu.domain.answer.Answer;
import com.fitback.ssu.domain.question.converter.PartConverter;
import com.fitback.ssu.domain.question.enums.Part;
import com.fitback.ssu.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Question")
@Entity
public class Question {
    @Column(name = "q_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "question",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Setter
    private List<Answer> answers = new ArrayList<>();

    @Column(name = "part", nullable = false)
    @Convert(converter = PartConverter.class)
    private Part part;

//    @Column(name = "keyword")
//    @Convert(converter = KeywordConverter.class)
//    private Keyword keyword;

    @OneToMany(mappedBy = "question",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Setter
    private Set<QuestionKeywordMap> keywords = new HashSet<>();

    @Column(name = "question_reference")
    private String questionReference;

    @Column(name = "q_name", nullable = false)
    private String qName;

    @Column(name = "q_content", nullable = false)
    private String qContent;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "answer_count")
    private Integer answerCount;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Builder
    public Question(Part part, User user,
                    String questionReference, String qName, String qContent,
                    Boolean isPublic, String startTime, String endTime) {
        this.part = part;
        this.user = user;
        this.questionReference = questionReference;
        this.qName = qName;
        this.qContent = qContent;
        this.isPublic = isPublic;
        this.answerCount = 0;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String timeConverter(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return String.valueOf(LocalDateTime.parse(time, formatter));
    }
}
