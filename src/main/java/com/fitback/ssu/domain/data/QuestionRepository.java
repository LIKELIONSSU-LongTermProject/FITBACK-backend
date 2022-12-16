package com.fitback.ssu.domain.data;

import com.fitback.ssu.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findByqId(Long qId);
    @Query(value = "SELECT q FROM Question q ORDER BY q.qId DESC")
    ArrayList<Question> findTop5();

    ArrayList<Question> findAllByIsCompleteAndIsAcceptedAndQuestioner(Boolean isAccepted, Boolean isComplete, User user);
    ArrayList<Question> findAllByIsCompleteAndIsAcceptedAndAnswer_Answerer(Boolean isAccepted, Boolean isComplete, User user);
}
