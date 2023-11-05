package com.zerolab.bookecho.repository;

import com.zerolab.bookecho.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
