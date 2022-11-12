package com.fitback.ssu.domain.authority;

import java.util.HashMap;
import java.util.Map;

public enum UserAuth {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_BABY("ROLE_BABY"),
    ROLE_PRO("ROLE_PRO")
    ;

    private static final Map<String, UserAuth> lookup = new HashMap<>();

    static {
        for(UserAuth auth : UserAuth.values()){
            lookup.put(auth.abbreviation, auth);
        }
    }

    private final String abbreviation;

    // private
    UserAuth(String abbreviation){
        this.abbreviation = abbreviation;
    }

    public static UserAuth get(String abbreviation){
        return lookup.get(abbreviation);
    }

    public static boolean containsKey(String abbreviation){
        return lookup.containsKey(abbreviation);
    }

    public String getAbbreviation(){
        return this.abbreviation;
    }
}
