package com.zerolab.bookecho.repository;

import com.zerolab.bookecho.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
