package com.zerolab.bookecho.repository;

import com.zerolab.bookecho.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
