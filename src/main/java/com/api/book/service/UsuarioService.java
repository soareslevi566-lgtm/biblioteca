package com.api.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.book.entity.UsuarioEntity;
import com.api.book.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }

    public void salvarUsuario(UsuarioEntity usuario) {
        usuarioRepository.save(usuario);
    }

    public UsuarioEntity buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}
