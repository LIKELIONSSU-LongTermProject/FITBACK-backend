package com.fitback.ssu.util;

import com.fitback.ssu.exception.BizException;
import com.fitback.ssu.exception.UserExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {
    /**
     * @return SecurityContext에 저장되어 있는 유저 아이디를 반환함
     */
    public static String getCurrentMemberEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null) {
            throw new BizException(UserExceptionType.NOT_FOUND_AUTHENTICATION);
        }

        return authentication.getName();
    }
}
