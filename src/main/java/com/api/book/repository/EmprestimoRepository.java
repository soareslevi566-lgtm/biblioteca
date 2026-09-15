package com.api.book.repository;

import com.api.book.entity.EmprestimoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<EmprestimoEntity, Long> {

    //Buscar se o livro já está emprestado no momento
    boolean existsByLivroIdAndStatus(Long livroId, String status);

    //Conta quantos livros o usuario já pegou e ainda não devolveu
    long countByUsuarioIdAndStatus(Long usuarioId, String status);

}
