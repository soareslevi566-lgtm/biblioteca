package com.api.book.controller;

import com.api.book.dto.UsuarioRequestDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PutExchange;
import com.api.book.entity.UsuarioEntity;
import com.api.book.repository.UsuarioRepository;

import java.util.List;

@Controller
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/usuarios")
    public UsuarioEntity criarUsuario(@RequestBody UsuarioRequestDTO dto) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/usuarios/{id}")
    public UsuarioEntity atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO dto) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        return usuarioRepository.save(usuario);
    }

    @GetMapping
    public List<UsuarioEntity> listarUsuarios(UsuarioEntity usuario) {
        return usuarioRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }

    @PostMapping
    public UsuarioEntity salvarUsuario(@RequestBody UsuarioEntity usuario) {
        return usuarioRepository.save(usuario);
    }

    @GetMapping("/{id}")
    public UsuarioEntity buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}
