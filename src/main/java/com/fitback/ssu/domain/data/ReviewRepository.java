package com.fitback.ssu.domain.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    ArrayList<Review> findAllByCreatedAtIsLessThanOrderByCreatedAtDesc(LocalDateTime now);
    ArrayList<Review> findByProInfo_DutyOrderByCreatedAtDesc(String duty);
}
