package com.fitback.ssu.domain.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@Entity
public class RefreshToken {

    @Id
    private String tokenKey;

    @Column(nullable = false)
    private String value;

    @Builder
    public RefreshToken(String tokenKey, String value){
        this.tokenKey = tokenKey;
        this.value = value;
    }

    public void updateValue(String token){
        this.value = token;
    }
}
