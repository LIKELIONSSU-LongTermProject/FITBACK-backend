package com.fitback.ssu.domain.data;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
@Entity
public class Comment {
    @Column(name = "comment_id")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question")
    private Question question;

    @Column(name = "commenter_image")
    private String commenterImage;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "content")
    private String content;

    @CreatedDate
    @Column(name = "created_at",updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Comment(Question question, String commenterImage, String nickname, String content, LocalDateTime createdAt) {
        this.question = question;
        this.commenterImage = commenterImage;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
    }
}
