package com.api.book.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;


@Entity
@Table(name = "emprestimos")
@Getter
@Setter
public class EmprestimoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UsuarioEntity usuario;

    @ManyToOne
    private LivroEntity livro;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoEfetiva;

    private String status; // ATIVO, DEVOLVIDO, ATRASADO;

}
