package com.fitback.ssu.domain.user.info;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Interest_company")
@Entity
public class InterestCompany {
    @JsonIgnore
    @Column(name = "interest_company_id")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long interestCompanyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baby_info_id")
    private BabyInfo babyInfo;

    @Column(name = "interest_company")
    private String interestCompany;

    @Builder
    public InterestCompany(BabyInfo babyInfo, String interestCompany) {
        this.babyInfo = babyInfo;
        this.interestCompany = interestCompany;
    }
}
