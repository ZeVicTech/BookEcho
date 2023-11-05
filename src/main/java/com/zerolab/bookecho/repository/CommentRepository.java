package com.zerolab.bookecho.repository;

import com.zerolab.bookecho.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
