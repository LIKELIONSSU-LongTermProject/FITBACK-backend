package com.fitback.ssu.domain.question.converter;

import com.fitback.ssu.domain.question.enums.Keyword;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class KeywordConverter extends CodeValueConverter<Keyword>{
    KeywordConverter(){
        super(Keyword.class);
    }
}
