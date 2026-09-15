package com.api.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.book.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

}
