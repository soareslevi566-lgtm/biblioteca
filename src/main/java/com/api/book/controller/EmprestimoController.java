package com.api.book.controller;

import com.api.book.dto.EmprestimoRequestDTO;
import com.api.book.entity.EmprestimoEntity;
import com.api.book.repository.EmprestimoRepository;
import com.api.book.service.EmprestimoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {
    private final EmprestimoRepository emprestimoRepository;
    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoRepository emprestimoRepository,
                                EmprestimoService emprestimoService) {
        this.emprestimoRepository = emprestimoRepository;
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public List<EmprestimoEntity> listarEmprestimos(){
        return emprestimoRepository.findAll();
    }

    @PostMapping
    public EmprestimoEntity realizarEmprestimo(@RequestBody EmprestimoRequestDTO dto) {
        return emprestimoService.realizarEmprestimo(dto.usuarioId(),  dto.livroId());
    }

    @PutMapping("/{id}/devolver")
    public EmprestimoEntity devolverEmprestimo(@PathVariable Long id) {
        return emprestimoService.devolverLivro(id);
    }
}
