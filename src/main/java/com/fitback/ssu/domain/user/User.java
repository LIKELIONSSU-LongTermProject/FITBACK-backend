package com.fitback.ssu.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitback.ssu.domain.answer.Answer;
import com.fitback.ssu.domain.authority.Authority;
import com.fitback.ssu.domain.authority.UserAuth;
import com.fitback.ssu.domain.question.Question;
import com.fitback.ssu.dto.user.UserUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User")
@Entity
public class User{

    @JsonIgnore
    @Column(name = "user_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Column(name = "username",length = 50, nullable = false)
    private String username;

    // Email을 토큰의 ID로 관리하기 때문에 unique = True
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "peanut")
    private Integer peanut;
    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

    @OneToMany(mappedBy = "user",
                cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "writer",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")}
    )
    private Set<Authority> authorities = new HashSet<>();

    @Builder
    public User(String username, String email, String password, Integer peanut, boolean activated, Set<Authority> authorities) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.peanut = peanut;
        this.activated = activated;
        this.authorities = authorities;
    }

    public void addAuthority(Authority authority){
        this.getAuthorities().add(authority);
    }

    public void removeAuthority(Authority authority){
        this.getAuthorities().remove(authority);
    }

    public void activate(boolean flag){
        this.activated = flag;
    }

    public String getAuthoritiesToString(){
        return this.authorities.stream()
                .map(Authority::getAuthorityName)
                .collect(Collectors.joining(","));
    }

    public void updateUser(UserUpdateDTO dto, PasswordEncoder passwordEncoder){
        if(dto.getPassword() != null) this.password = passwordEncoder.encode(dto.getPassword());
        if(dto.getUsername() != null) this.username = dto.getUsername();
        if(dto.getAuthorities().size() > 0){
            this.authorities = dto.getAuthorities().stream()
                    .filter(UserAuth::containsKey)
                    .map(UserAuth::get)
                    .map(Authority::new)
                    .collect(Collectors.toSet());
        }
    }
}
