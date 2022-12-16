package com.fitback.ssu.domain.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByAnswerId(Long answerId);
    Answer findByQuestion(Question question);
}
