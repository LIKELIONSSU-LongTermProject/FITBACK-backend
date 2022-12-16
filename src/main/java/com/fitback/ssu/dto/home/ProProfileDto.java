package com.fitback.ssu.dto.home;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProProfileDto {
    private Long profileId;
    private String shortIntro;
    private String nickname;
    private String company;
    private String duty;
    private Integer annual;
    private Integer reviewNum;
    private Integer satisfactionRatio;

    @Builder
    public ProProfileDto(Long profileId, String shortIntro, String nickname,
                         String company, String duty, Integer annual,
                         Integer reviewNum, Integer satisfactionRatio) {
        this.profileId = profileId;
        this.shortIntro = shortIntro;
        this.nickname = nickname;
        this.company = company;
        this.duty = duty;
        this.annual = annual;
        this.reviewNum = reviewNum;
        this.satisfactionRatio = satisfactionRatio;
    }
}
