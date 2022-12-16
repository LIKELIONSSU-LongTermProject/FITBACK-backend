package com.fitback.ssu.domain.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitback.ssu.domain.user.info.BabyInfo;
import com.fitback.ssu.domain.user.info.ProInfo;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Review")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Review {

    @JsonIgnore
    @Column(name = "review_id")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_info_id")
    private ProInfo proInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baby_info_id")
    private BabyInfo babyInfo;

    @Column(name = "review", nullable = false)
    private String review;

    @Column(name = "satisfaction", nullable = false)
    private Integer satisfaction;

    @CreatedDate
    @Column(name = "created_at",updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Review(ProInfo proInfo, BabyInfo babyInfo, String review, Integer satisfaction, LocalDateTime createdAt) {
        this.proInfo = proInfo;
        this.babyInfo = babyInfo;
        this.review = review;
        this.satisfaction = satisfaction;
        this.createdAt = createdAt;
    }
}
