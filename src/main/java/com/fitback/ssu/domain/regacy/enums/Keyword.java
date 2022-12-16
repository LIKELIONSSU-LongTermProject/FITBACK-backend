package com.fitback.ssu.domain.regacy.enums;

import com.fitback.ssu.domain.regacy.converter.CodeValue;

public enum Keyword implements CodeValue {
    NextJS("F1","Next.JS"),
    TypeScript("F2","TypeScript"),
    Axios("F3","Axios"),
    Recoil("F4","Recoil"),
    Java("B1","Java"),
    SpringBoot("B2","SpringBoot"),
    DataJPA("B3","Data JPA"),
    Security("B4","Security"),
    BX("D1","BX"),
    UI("D2","UI/GUI디자인"),
    UX("D3","UX디자인"),
    Illustration("D4","일러스트"),
    PHOTOSHOP("D5","포토샵"),
    FIGMA("D6","Figma"),
    AdobeXd("D7","Adobe Xd")
    ;
    private String code;
    private String value;

    Keyword(String code, String value) {
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
