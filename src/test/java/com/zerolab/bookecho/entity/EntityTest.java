package com.zerolab.bookecho.entity;

import com.zerolab.bookecho.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
@Transactional
@Commit
public class EntityTest {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void initTableCheck(){
        Member member = new Member("헤이즈","로그인 아이디", "패스워드");
        memberRepository.save(member);
    }
}
