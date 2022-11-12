package com.fitback.ssu.domain.question.converter;

import com.fitback.ssu.domain.question.enums.Part;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class PartConverter extends CodeValueConverter<Part> {
    PartConverter(){
        super(Part.class);
    }
}
