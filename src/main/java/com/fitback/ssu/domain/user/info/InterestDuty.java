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
@Table(name = "Interest_duty")
@Entity
public class InterestDuty {
    @JsonIgnore
    @Column(name = "interest_duty_id")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long interestDutyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baby_info_id")
    private BabyInfo babyInfo;

    @Column(name = "interest_duty")
    private String interestDuty;

    @Builder
    public InterestDuty(BabyInfo babyInfo, String interestDuty) {
        this.babyInfo = babyInfo;
        this.interestDuty = interestDuty;
    }
}
