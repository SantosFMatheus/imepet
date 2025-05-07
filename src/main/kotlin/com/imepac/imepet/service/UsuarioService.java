package com.imepac.imepet.service;

import com.imepac.imepet.model.UsuarioModel;
import com.imepac.imepet.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioModel> listarTodos() {
        return usuarioRepository.findAll();
    }

    public UsuarioModel salvar(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
