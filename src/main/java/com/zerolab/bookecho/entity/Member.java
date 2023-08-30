package com.zerolab.bookecho.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String nickName;
    private String loginId;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL, orphanRemoval = true)//CascadeType 확인 안바뀔수도 있음
    private List<Session> sessions = new ArrayList<>();

    public Session addSession(){
        Session session = Session.builder()
                        .member(this)
                        .build();
        sessions.add(session);

        return session;
    }

    @Builder
    public Member(String nickName, String loginId, String password) {
        this.nickName = nickName;
        this.loginId = loginId;
        this.password = password;
    }
}
