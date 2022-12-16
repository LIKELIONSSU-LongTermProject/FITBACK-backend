package com.fitback.ssu.domain.user.info;

import com.fitback.ssu.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ProInfoRepository extends JpaRepository<ProInfo, Long> {
    ProInfo findByUser(User user);
    ProInfo findByProInfoId(Long pid);
    ArrayList<ProInfo> findTop10ByDutyContaining(String duty);
    ArrayList<ProInfo> findAllBySatisfactionRatioIsGreaterThan(Integer satisfactionRatio);
    ArrayList<ProInfo> findTop10BySatisfactionRatioIsGreaterThanOrderBySatisfactionRatio(Integer satisfactionRatio);

    ArrayList<ProInfo> findAllByOrderByReviewNumDesc();
    ArrayList<ProInfo> findAllByOrderBySatisfactionRatioDesc();
    ArrayList<ProInfo> findByCompanyContainingOrderByReviewNumDesc(String company);
    ArrayList<ProInfo> findByCompanyContainingOrderBySatisfactionRatioDesc(String company);
    ArrayList<ProInfo> findByDutyContainingOrderByReviewNumDesc(String duty);
    ArrayList<ProInfo> findByDutyContainingOrderBySatisfactionRatioDesc(String duty);
    ArrayList<ProInfo> findByAnnualIsGreaterThanOrderByReviewNumDesc(Integer annual);
    ArrayList<ProInfo> findByAnnualIsGreaterThanOrderBySatisfactionRatioDesc(Integer annual);
    ArrayList<ProInfo> findByCompanyContainingAndDutyContainingOrderByReviewNumDesc(String company, String duty);
    ArrayList<ProInfo> findByCompanyContainingAndDutyContainingOrderBySatisfactionRatioDesc(String company, String duty);
    ArrayList<ProInfo> findByCompanyContainingAndAnnualIsGreaterThanOrderByReviewNumDesc(String company, Integer annual);
    ArrayList<ProInfo> findByCompanyContainingAndAnnualIsGreaterThanOrderBySatisfactionRatioDesc(String company, Integer annual);
    ArrayList<ProInfo> findByDutyContainingAndAnnualIsGreaterThanOrderByReviewNumDesc(String duty, Integer annual);
    ArrayList<ProInfo> findByDutyContainingAndAnnualIsGreaterThanOrderBySatisfactionRatioDesc(String duty, Integer annual);
    ArrayList<ProInfo> findByCompanyContainingAndDutyContainingAndAnnualIsGreaterThanOrderByReviewNumDesc(String company, String duty, Integer annual);
    ArrayList<ProInfo> findByCompanyContainingAndDutyContainingAndAnnualIsGreaterThanOrderBySatisfactionRatioDesc(String company, String duty, Integer annual);



}
