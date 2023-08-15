package com.sbpa.demo.repository;

import com.sbpa.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Long> {
    List<Book> findByReader(String reader);
}
