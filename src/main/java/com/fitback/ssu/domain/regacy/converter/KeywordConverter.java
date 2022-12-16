package com.fitback.ssu.domain.regacy.converter;

import com.fitback.ssu.domain.regacy.enums.Keyword;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class KeywordConverter extends CodeValueConverter<Keyword>{
    KeywordConverter(){
        super(Keyword.class);
    }
}
