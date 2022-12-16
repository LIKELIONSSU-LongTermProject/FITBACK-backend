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
@Table(name = "ProInfo")
@Entity
public class ProInfo {

    @JsonIgnore
    @Column(name = "pro_info_id")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long proInfoId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "proInfo")
    private User user;

    @Column(name = "email_check")
    private Boolean emailCheck;

    @Column(name = "card_check")
    private Boolean cardCheck;

    @Column(name = "email_check_code")
    private String emailCheckCode;

    @Column(name = "review_num")
    private Integer reviewNum;

    @OneToMany(mappedBy = "proInfo",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Column(name = "satisfaction_ratio")
    private Integer satisfactionRatio;

    // 1단계 회원 가입 이후 저장
    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "duty", nullable = false)
    private String duty;

    @Column(name = "annual", nullable = false)
    private Integer annual;

    // 3단계 회원 가입 이후 저장

    @Column(name = "iamge_url")
    private String imageUrl;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "short_intro")
    private String shortIntro;

    @Column(name = "long_intro")
    private String longIntro;

    @Column(name = "expression_experience")
    private String expressionExperience;

    @Column(name = "expression_company")
    private String expressionCompany;

    @Column(name = "expression_speaking")
    private String expressionSpeaking;

    @Column(name = "expression_part")
    private String expressionPart;

    @Column(name = "expression_strength")
    private String expressionStrength;

    @Builder
    public ProInfo(User user, String company, String duty, Integer annual) {
        this.user = user;
        this.emailCheck = false;
        this.cardCheck = false;
        this.reviewNum = 0;
        this.satisfactionRatio = 0;
        this.company = company;
        this.duty = duty;
        this.annual = annual;
    }


}
