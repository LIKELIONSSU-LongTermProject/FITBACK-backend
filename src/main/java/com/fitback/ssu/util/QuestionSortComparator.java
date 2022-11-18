package com.fitback.ssu.util;

import com.fitback.ssu.dto.question.QuestionInfoDto;

import java.util.Comparator;

public class QuestionSortComparator implements Comparator<QuestionInfoDto> {

    @Override
    public int compare(QuestionInfoDto q1, QuestionInfoDto q2) {
        if (q1.getDeadLine() > q2.getDeadLine()) {
            return 1;
        } else if (q1.getDeadLine() < q2.getDeadLine()) {
            return -1;
        }
        return 0;
    }
}
