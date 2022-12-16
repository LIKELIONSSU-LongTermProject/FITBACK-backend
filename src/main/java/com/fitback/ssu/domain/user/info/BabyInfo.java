package com.fitback.ssu.domain.user.info;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitback.ssu.domain.data.Review;
import com.fitback.ssu.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BabyInfo")
@Entity
public class BabyInfo {
    @JsonIgnore
    @Column(name = "baby_info_id")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long babyInfoId;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "babyInfo")
    private User user;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @OneToMany(mappedBy = "babyInfo",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterestCompany> interestCompanies = new ArrayList<>();

    @OneToMany(mappedBy = "babyInfo",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterestDuty> interestDuties = new ArrayList<>();

    @OneToMany(mappedBy = "babyInfo",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public BabyInfo(User user, String imageUrl, String nickname) {
        this.user = user;
        this.imageUrl = imageUrl;
        this.nickname = nickname;
    }
}
