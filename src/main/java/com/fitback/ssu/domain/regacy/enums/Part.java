package com.fitback.ssu.domain.regacy.enums;

import com.fitback.ssu.domain.regacy.converter.CodeValue;

public enum Part implements CodeValue {
    DEV_BACK("B","DEV_BACK"),
    DEV_FRONT("F","DEV_FRONT"),
    DESIGN("D","DESIGN"),
    PM("P","PM")
    ;
    private String code;
    private String value;

    Part(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }
}
