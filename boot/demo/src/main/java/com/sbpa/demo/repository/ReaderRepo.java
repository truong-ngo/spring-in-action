package com.sbpa.demo.repository;

import com.sbpa.demo.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepo extends JpaRepository<Reader, String> {
}
