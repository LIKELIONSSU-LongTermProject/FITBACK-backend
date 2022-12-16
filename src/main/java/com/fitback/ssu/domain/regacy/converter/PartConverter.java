package com.fitback.ssu.domain.regacy.converter;

import com.fitback.ssu.domain.regacy.enums.Part;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class PartConverter extends CodeValueConverter<Part> {
    PartConverter(){
        super(Part.class);
    }
}
