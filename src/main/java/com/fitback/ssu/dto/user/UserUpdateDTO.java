package com.fitback.ssu.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    private String email;
    private String password;
    private String username;
    private List<String> authorities;

}