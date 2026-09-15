package com.api.book.service;

import com.api.book.entity.EmprestimoEntity;
import com.api.book.entity.LivroEntity;
import com.api.book.entity.UsuarioEntity;
import com.api.book.repository.EmprestimoRepository;
import com.api.book.repository.LivroRepository;
import com.api.book.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             LivroRepository livroRepository,
                             UsuarioRepository usuarioRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    public EmprestimoEntity realizarEmprestimo(Long usuarioId, Long livroId) {
        // 1. Valida se o usuario existe
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario não localizado!"));

        // 2. Valida se o livro existe
        LivroEntity livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new RuntimeException("Livro não localizado!"));

        // 3. Regra: O livro já foi emprestado?
        boolean livroOcupado = emprestimoRepository.existsByLivroIdAndStatus(livroId, "ATIVO");
        if (livroOcupado) {
            throw new RuntimeException("Este livro já está emprestado!");
        }
        // 4. Regra: Limite de no maximo 3 livros por usuario
        Long emprestimosAtivos = emprestimoRepository.countByUsuarioIdAndStatus(usuarioId, "ATIVO");
        if (emprestimosAtivos >= 3) {
            throw new RuntimeException("Usuario já atingiu o limite de 3 livros emprestados!");
        }

        // 5. Cria e salva o emprestimo com prazo de 14 dias    

        EmprestimoEntity emprestimo = new EmprestimoEntity();
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucaoPrevista(LocalDate.now().plusDays(14));
        emprestimo.setStatus("ATIVO");

        return emprestimoRepository.save(emprestimo);
    }

    public EmprestimoEntity devolverLivro(Long emprestimoId) {
        EmprestimoEntity emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new RuntimeException("Emprestimo não encontrado!"));
        if ("DESOLVIDO".equals(emprestimo.getStatus())) {
            throw new RuntimeException("Este livro já foi dessolvido!");
        }

        emprestimo.setDataDevolucaoEfetiva(LocalDate.now());
        emprestimo.setStatus("DESOLVIDO");

        return emprestimoRepository.save(emprestimo);
    }
}
