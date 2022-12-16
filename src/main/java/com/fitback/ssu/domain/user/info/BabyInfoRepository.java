package com.fitback.ssu.domain.user.info;

import com.fitback.ssu.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BabyInfoRepository extends JpaRepository<BabyInfo, Long> {
    BabyInfo findByUser(User user);
    BabyInfo findByUser_Email(String email);
}
