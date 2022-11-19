package com.fitback.ssu.domain.answer;

import com.fitback.ssu.domain.question.Question;
import com.fitback.ssu.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findById(Long aID);
    ArrayList<Answer> findAllByIsPermittedFalseAndQuestion(Boolean isPermitted, Question question);

    List<Answer> findAllByWriter(User user);
}
