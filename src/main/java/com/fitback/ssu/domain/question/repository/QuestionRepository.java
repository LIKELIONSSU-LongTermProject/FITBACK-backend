package com.fitback.ssu.domain.question.repository;

import com.fitback.ssu.domain.question.Question;
import com.fitback.ssu.domain.question.enums.Part;
import com.fitback.ssu.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findById(Long id);
    ArrayList<Question> findByPartOrderByEndTimeDesc(Part part);

//    List<Question> findTop5By();

    @Query(value = "SELECT q FROM Question q ORDER BY q.qId DESC")
    ArrayList<Question> findTop5();

    ArrayList<Question> findAllByIsCompleteAndUser(Boolean isComplete, User user);
//    ArrayList<Question> findAllByIsCompleteTrueAndUser(Boolean isComplete, User user);
}
