package com.fitback.ssu.service.question;

import com.fitback.ssu.domain.question.Question;
import com.fitback.ssu.domain.question.QuestionKeywordMap;
import com.fitback.ssu.domain.question.repository.QuestionKeywordMapRepository;
import com.fitback.ssu.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordService {

    private final QuestionRepository questionRepository;
    private final QuestionKeywordMapRepository questionKeywordMapRepository;

    @Transactional(readOnly = true)
    public Set<String> makeKeywords(Long id){
        Set<String> keywords = new HashSet<>();
        Question question = questionRepository.findById(id).get();
        Set<QuestionKeywordMap> keywordSearch = questionKeywordMapRepository.findByQuestion(question);
        Iterator<QuestionKeywordMap> keywordIterator = keywordSearch.iterator();
        while(keywordIterator.hasNext()){
            keywords.add(keywordIterator.next().getKeyword().getKeyword());
        }
        return keywords;
    }
}
