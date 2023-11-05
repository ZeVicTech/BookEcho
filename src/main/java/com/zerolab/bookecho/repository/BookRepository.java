package com.zerolab.bookecho.repository;

import com.zerolab.bookecho.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
