package com.api.book.controller;

import java.util.List;

import com.api.book.dto.LivroRequestDTO;
import com.api.book.repository.LivroRepository;
import org.springframework.web.bind.annotation.*;

import com.api.book.entity.LivroEntity;

@RestController
@RequestMapping("livros")
public class LivroController {
    private final LivroRepository livroRepository;

    public LivroController(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @GetMapping
    public List<LivroEntity> listarLivros() {
        return livroRepository.findAll();
    }

    @GetMapping("/{id}")
    public LivroEntity buscarLivroPorId(@PathVariable Long id) {
        return livroRepository.findById(id).orElse(null);
    }

    @PostMapping
    public LivroEntity criarLivro(@RequestBody LivroRequestDTO dto) {
        LivroEntity livro = new LivroEntity();
        livro.setTitulo(dto.titulo());
        livro.setAutor(dto.autor());
        return livroRepository.save(livro);
    }

    @PutMapping("/{id}")
    public LivroEntity atualizarLivro(@PathVariable Long id, @RequestBody LivroRequestDTO dto) {
        LivroEntity livro = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        livro.setTitulo(dto.titulo());
        livro.setAutor(dto.autor());
        return livroRepository.save(livro);
    }

    @DeleteMapping("/{id}")
    public void deletarLivro(@PathVariable Long id) {
        livroRepository.deleteById(id);
    }

}