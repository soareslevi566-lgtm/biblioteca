package com.api.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.book.entity.LivroEntity;

public interface LivroRepository extends JpaRepository<LivroEntity, Long> {

    boolean existsByIsbn(String isbn);
    
}
