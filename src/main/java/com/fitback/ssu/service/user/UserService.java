package com.fitback.ssu.service.user;

import com.fitback.ssu.domain.user.User;
import com.fitback.ssu.domain.user.UserRepository;
import com.fitback.ssu.dto.user.UserRespDTO;
import com.fitback.ssu.dto.user.UserUpdateDTO;
import com.fitback.ssu.exception.BizException;
import com.fitback.ssu.exception.UserExceptionType;
import com.fitback.ssu.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     *
     * @param email
     * @return email에 해당하는 멤버의 정보를 반환한다.
     */
    @Transactional(readOnly = true)
    public UserRespDTO getUserInfo(String email) {
        System.out.println(email);
        System.out.println(userRepository.findByEmail(email));
        return userRepository.findByEmail(email)
                .map(UserRespDTO::of)
                .orElseThrow(()-> new BizException(UserExceptionType.NOT_FOUND_USER)); // 유저를 찾을 수 없습니다.
    }

    /**
     * @return 현재 securityContext에 있는 유저 정보를 반환한다.
     */
    @Transactional(readOnly = true)
    public UserRespDTO getMyInfo() {
        return userRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
                .map(UserRespDTO::of)
                .orElseThrow(()->new BizException(UserExceptionType.NOT_FOUND_USER));
//        User user = userRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
//                .orElseThrow(() -> new BizException(UserExceptionType.NOT_FOUND_USER));
//        if(user.getAuthorities().contains(UserAuth.valueOf("ROLE_PRO"))){
//
//        } else if (user.getAuthorities().contains(UserAuth.valueOf("ROLE_BABY"))) {
//            return BabyPageDto.builder()
//                    .email(user.getEmail())
//                    .username(user.getUsername())
//                    .build();
//        }else{
//
//        }
//        return null;
    }


    /**
     * DirtyChecking 을 통한 멤버 업데이트 ( Email은 업데이트 할 수 없다.)
     * @param dto
     */
    @Transactional
    public void updateUserInfo(UserUpdateDTO dto) {
        User user = userRepository
                .findByEmail(dto.getEmail())
                .orElseThrow(() -> new BizException(UserExceptionType.NOT_FOUND_USER));

        user.updateUser(dto,passwordEncoder);
    }

}
