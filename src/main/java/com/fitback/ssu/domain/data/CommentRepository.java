package com.fitback.ssu.domain.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    ArrayList<Comment> findByQuestionOrderByCreatedAtAsc(Question question);
}
