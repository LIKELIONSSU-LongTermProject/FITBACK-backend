package com.fitback.ssu.service.home;

import com.fitback.ssu.domain.data.Review;
import com.fitback.ssu.domain.data.ReviewRepository;
import com.fitback.ssu.domain.user.User;
import com.fitback.ssu.domain.user.UserRepository;
import com.fitback.ssu.domain.user.info.*;
import com.fitback.ssu.dto.home.ProCardDto;
import com.fitback.ssu.dto.home.ProProfileDto;
import com.fitback.ssu.dto.home.ProfileAndReviewDto;
import com.fitback.ssu.exception.BizException;
import com.fitback.ssu.exception.UserExceptionType;
import com.fitback.ssu.util.DateUtil;
import com.fitback.ssu.util.SecurityUtil;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProListService {
    private final UserRepository userRepository;
    private final BabyInfoRepository babyInfoRepository;
    private final ProInfoRepository proInfoRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public ArrayList<ProProfileDto> recommendPros(){
        ArrayList<ProProfileDto> profileDtos = new ArrayList<>();
        ArrayList<ProInfo> infos = proInfoRepository.findTop10BySatisfactionRatioIsGreaterThanOrderBySatisfactionRatio(70);

        infos.forEach(
                proInfo -> {
                    profileDtos.add(ProProfileDto.builder()
                                    .profileId(proInfo.getProInfoId())
                                    .shortIntro(proInfo.getShortIntro())
                                    .nickname(proInfo.getNickname())
                                    .company(proInfo.getCompany()).duty(proInfo.getDuty()).annual(proInfo.getAnnual())
                                    .reviewNum(proInfo.getReviewNum()).satisfactionRatio(proInfo.getSatisfactionRatio())
                            .build());
                }
        );
        return profileDtos;
    }

    @Transactional(readOnly = true)
    public ArrayList<ProProfileDto> recommendProsByUserData(){
        User user = userRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new BizException(UserExceptionType.NOT_FOUND_USER));
//        Random random = new Random();
        ArrayList<ProProfileDto> profileDtos = new ArrayList<>();

        // 관심사 직무에 해당하는 pro 모두 찾기로 변경해야함
        BabyInfo babyInfo = babyInfoRepository.findByUser_Email(user.getEmail());
        List<InterestDuty> interestDuties = babyInfo.getInterestDuties();
//        String interestDuty = interestDuties.get(random.nextInt(interestDuties.size())).getInterestDuty();
        for(int i=0 ; i< interestDuties.size() ; i++){
            ArrayList<ProInfo> infos = proInfoRepository.findTop10ByDutyContaining(interestDuties.get(i).getInterestDuty());

            infos.forEach(
                    proInfo -> {
                        profileDtos.add(ProProfileDto.builder()
                                .profileId(proInfo.getProInfoId())
                                .shortIntro(proInfo.getShortIntro())
                                .nickname(proInfo.getNickname())
                                .company(proInfo.getCompany()).duty(proInfo.getDuty()).annual(proInfo.getAnnual())
                                .reviewNum(proInfo.getReviewNum()).satisfactionRatio(proInfo.getSatisfactionRatio())
                                .build());
                    }
            );
        }

        return profileDtos;
    }
    
    @Transactional(readOnly = true)
    public ArrayList<ProfileAndReviewDto> getProReviewList(String sort) {
        ArrayList<ProfileAndReviewDto> profileAndReviewDtos = new ArrayList<>();
        if(sort.equals("all")){ // 직무 상관없이 전부 다
            ArrayList<Review> reviews = reviewRepository.findAllByCreatedAtIsLessThanOrderByCreatedAtDesc(LocalDateTime.now());
            reviews.forEach(
                    review -> {
                        profileAndReviewDtos.add(ProfileAndReviewDto.builder()
                                        .nickname(review.getProInfo().getNickname())
                                        .imageUrl(review.getProInfo().getImageUrl())
                                        .company(review.getProInfo().getCompany())
                                        .duty(review.getProInfo().getDuty())
                                        .annual(review.getProInfo().getAnnual())
                                        .reviewInfo(ProfileAndReviewDto.ReviewInfo.builder()
                                                .writer(review.getBabyInfo().getNickname())
                                                .review(review.getReview())
                                                .createdAt(DateUtil.dateTimeToString(review.getCreatedAt()))
                                                .build())
                                .build());
                    }
            );
            return profileAndReviewDtos;
        } else{ // 직무 별로 보고 싶을 때
            ArrayList<Review> reviews = reviewRepository.findByProInfo_DutyOrderByCreatedAtDesc(sort);
            reviews.forEach(
                    review -> {
                        profileAndReviewDtos.add(ProfileAndReviewDto.builder()
                                .nickname(review.getProInfo().getNickname())
                                .imageUrl(review.getProInfo().getImageUrl())
                                .company(review.getProInfo().getCompany())
                                .duty(review.getProInfo().getDuty())
                                .annual(review.getProInfo().getAnnual())
                                .reviewInfo(ProfileAndReviewDto.ReviewInfo.builder()
                                        .writer(review.getBabyInfo().getNickname())
                                        .review(review.getReview())
                                        .createdAt(DateUtil.dateTimeToString(review.getCreatedAt()))
                                        .build())
                                .build());
                    }
            );
            return profileAndReviewDtos;
        }
    }

    @Transactional(readOnly = true)
    public ProCardDto getProCard(Long pid){
        ArrayList<ProCardDto.ReviewInfo> reviewInfos = new ArrayList<>();
        ProInfo proInfo = proInfoRepository.findByProInfoId(pid);
        proInfo.getReviews().forEach(
                review -> {
                    reviewInfos.add(ProCardDto.ReviewInfo.builder()
                            .userNickname(review.getBabyInfo().getNickname())
                            .satisfaction(review.getSatisfaction())
                            .createdAt(DateUtil.dateTimeToString(review.getCreatedAt()))
                            .review(review.getReview())
                            .build());
                }
        );

        return ProCardDto.builder()
                .imageUrl(proInfo.getImageUrl()).nickname(proInfo.getNickname())
                .company(proInfo.getCompany()).duty(proInfo.getDuty()).annual(proInfo.getAnnual())
                .reviewNum(proInfo.getReviewNum()).satisfactionRatio(proInfo.getSatisfactionRatio())
                .shortIntro(proInfo.getShortIntro()).longIntro(proInfo.getLongIntro())
                .expressionExperience(proInfo.getExpressionExperience())
                .expressionCompany(proInfo.getExpressionCompany())
                .expressionPart(proInfo.getExpressionPart())
                .expressionSpeaking(proInfo.getExpressionSpeaking())
                .expressionStrength(proInfo.getExpressionStrength())
                .reviewInfos(reviewInfos)
                .build();
    }

    @Transactional(readOnly = true)
    public List<List<ProProfileDto>> searchPro(String company, String duty, Integer annual, String sort){
        ArrayList<ProInfo> proInfos;
        List<List<ProProfileDto>> data = new ArrayList<>();
        if (company.isEmpty() && duty.isEmpty() && annual.equals(0)){ // 000
            proInfos = sort.equals("rnum") ?
                    proInfoRepository.findAllByOrderByReviewNumDesc() :
                    proInfoRepository.findAllByOrderBySatisfactionRatioDesc();

        } else if (!company.isEmpty() && duty.isEmpty() && annual.equals(0)) { // 100
            proInfos = sort.equals("rnum") ?
                    proInfoRepository.findByCompanyContainingOrderByReviewNumDesc(company) :
                    proInfoRepository.findByCompanyContainingOrderBySatisfactionRatioDesc(company);

        } else if (company.isEmpty() && !duty.isEmpty() && annual.equals(0)) { // 010
            proInfos = sort.equals("rnum") ?
                    proInfoRepository.findByDutyContainingOrderByReviewNumDesc(duty) :
                    proInfoRepository.findByDutyContainingOrderBySatisfactionRatioDesc(duty);

        } else if (company.isEmpty() && duty.isEmpty() && !annual.equals(0)) { // 001
            proInfos = sort.equals("rnum") ?
                    proInfoRepository.findByAnnualIsGreaterThanOrderByReviewNumDesc(annual) :
                    proInfoRepository.findByAnnualIsGreaterThanOrderBySatisfactionRatioDesc(annual);

        } else if (!company.isEmpty() && !duty.isEmpty() && annual.equals(0)) { // 110
            proInfos = sort.equals("rnum") ?
                    proInfoRepository.findByCompanyContainingAndDutyContainingOrderByReviewNumDesc(company, duty) :
                    proInfoRepository.findByCompanyContainingAndDutyContainingOrderBySatisfactionRatioDesc(company, duty);

        } else if (!company.isEmpty() && duty.isEmpty() && !annual.equals(0)) { // 101
            proInfos = sort.equals("rnum") ?
                    proInfoRepository.findByCompanyContainingAndAnnualIsGreaterThanOrderByReviewNumDesc(company, annual) :
                    proInfoRepository.findByCompanyContainingAndAnnualIsGreaterThanOrderBySatisfactionRatioDesc(company, annual);

        } else if (company.isEmpty() && !duty.isEmpty() && !annual.equals(0)) { // 011
            proInfos = sort.equals("rnum") ?
                    proInfoRepository.findByDutyContainingAndAnnualIsGreaterThanOrderByReviewNumDesc(duty, annual) :
                    proInfoRepository.findByDutyContainingAndAnnualIsGreaterThanOrderBySatisfactionRatioDesc(duty, annual);

        }else { // 111
            proInfos = sort.equals("rnum") ?
                    proInfoRepository.findByCompanyContainingAndDutyContainingAndAnnualIsGreaterThanOrderByReviewNumDesc(company, duty, annual) :
                    proInfoRepository.findByCompanyContainingAndDutyContainingAndAnnualIsGreaterThanOrderBySatisfactionRatioDesc(company, duty, annual);
        }
        ArrayList<ProProfileDto> profileDtos = new ArrayList<>();
        proInfos.forEach(
                proInfo -> {
                    profileDtos.add(ProProfileDto.builder()
                            .profileId(proInfo.getProInfoId())
                            .shortIntro(proInfo.getShortIntro())
                            .nickname(proInfo.getNickname())
                            .company(proInfo.getCompany()).duty(proInfo.getDuty()).annual(proInfo.getAnnual())
                            .reviewNum(proInfo.getReviewNum()).satisfactionRatio(proInfo.getSatisfactionRatio())
                            .build());
                }
        );
        data = Lists.partition(profileDtos, 16);
        return data;
    }
}
