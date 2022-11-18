package com.fitback.ssu.domain.question.repository;

import com.fitback.ssu.domain.question.Keyword;
import com.fitback.ssu.domain.question.Question;
import com.fitback.ssu.domain.question.QuestionKeywordMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Set;

@Repository
public interface QuestionKeywordMapRepository extends JpaRepository<QuestionKeywordMap, Long> {
    Set<QuestionKeywordMap> findByQuestion(Question question);
    ArrayList<QuestionKeywordMap> findByKeyword(Keyword keyword);
}
