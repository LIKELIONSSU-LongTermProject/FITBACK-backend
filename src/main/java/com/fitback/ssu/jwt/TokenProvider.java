package com.fitback.ssu.jwt;

import com.fitback.ssu.domain.authority.Authority;
import com.fitback.ssu.dto.jwt.TokenDTO;
import com.fitback.ssu.exception.AuthorityExceptionType;
import com.fitback.ssu.exception.BizException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Component
public class TokenProvider {

    // JWT claim 정보에 들어갈 멤버의 권한을 나타내는 key 값, ex) auth : ROLE_USER
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";

    private final long ACCESS_TOKEN_EXPIRE_TIME;
    private final long REFRESH_TOKEN_EXPIRE_TIME;

    private final Key key;

    // 토큰의 유효기간과 토큰에 사인하기 위한 키값을 생성
    public TokenProvider(
            @Value("${jwt.secret}")String secretKey,
            @Value("${jwt.access-token-expire-time}") long accessTime,
            @Value("${jwt.refresh-token-expire-time}") long refreshTime
    ){
        this.ACCESS_TOKEN_EXPIRE_TIME = accessTime;
        this.REFRESH_TOKEN_EXPIRE_TIME = refreshTime;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     *
     * @param email
     * @param auth
     * @param tokenValid
     * @return token
     * 사용자의 이메일과 권한들을 받아서 토큰을 생성합니다. String으로 권한들을 joining 하였음
     */
    protected String createToken(String email, Set<Authority> auth, long tokenValid){
        // ex) sub : abc@abc.com
        Claims claims = Jwts.claims().setSubject(email);

        // ex) auth : ROLE_USER,ROLE_ADMIN
        claims.put(AUTHORITIES_KEY,
                auth.stream()
                        .map(Authority::getAuthorityName)
                        .collect(Collectors.joining(","))
        );

        // now
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now. getTime() + tokenValid)) // token 만료시간
                .signWith(key, SignatureAlgorithm.HS512) // 키와 알고리즘
                .compact();
    }

    /**
     * @param email
     * @param auth
     * @return access token 생성
     */
    public String createAccessToken(String email, Set<Authority> auth){
        return this.createToken(email, auth, ACCESS_TOKEN_EXPIRE_TIME);
    }

    /**
     * @param email
     * @param auth
     * @return refresh token 생성
     */
    public String createRefreshToken(String email, Set<Authority> auth){
        return this.createToken(email, auth, REFRESH_TOKEN_EXPIRE_TIME);
    }

    /**
     * @param token
     * @return token값을 파싱하여 클레임에 담긴 이메일 값을 가져온다.
     */
    public String getUserEmailByToken(String token){
        // token 의 claim 의 sub 키에 이메일 값이 들어있다.
        return this.parseClaims(token).getSubject();
    }

    /**
     *
     * @param accessToken
     * @param refreshToken
     * @return token DTO 생성
     */
    public TokenDTO createTokenDTO(String accessToken, String refreshToken){
        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .grantType(BEARER_TYPE)
                .build();
    }

    // 토큰에서 Authentication 인스턴스를 가져오는 메서드
    public Authentication getAuthentication(String accessToken) throws BizException {
        // token 복호화
        Claims claims = parseClaims(accessToken);

        if(claims.get(AUTHORITIES_KEY) == null || !StringUtils.hasText(claims.get(AUTHORITIES_KEY).toString())){
            throw new BizException(AuthorityExceptionType.NOT_FOUND_AUTHORITY); // 유저에게 아무런 권한이 없습니다.
        }

        log.debug("claims.getAuth = {}", claims.get(AUTHORITIES_KEY));
        log.debug("claims.getEmail = {}", claims.getSubject());

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        authorities.stream().forEach(o -> {
            log.debug("getAuthentication -> authorities = {}",o.getAuthority());
        });

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new CustomEmailPasswordAuthToken(principal, "", authorities);
    }

    public int validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return 1;
        } catch (ExpiredJwtException e){
            log.info("만료된 JWT 토큰입니다.");
            return 2;
        } catch (Exception e){
            log.info("잘못된 토큰입니다.");
            return -1;
        }
    }

    // 액세스 토큰을 파싱함, 만료되더라도 재발행을 위해 반환해야함
    private Claims parseClaims(String accessToken){
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        }catch (ExpiredJwtException e){ // 만료된 토큰이더라도 일단 파싱을 함
            return e.getClaims();
        }
    }


}
