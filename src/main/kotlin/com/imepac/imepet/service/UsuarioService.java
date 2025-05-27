package com.imepac.imepet.service;

import com.imepac.imepet.model.UsuarioModel;
import com.imepac.imepet.repositories.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Cria o usuário admin no banco de dados se ele ainda não existir.
     */
    @PostConstruct
    public void initAdmin() {
        if (usuarioRepository.findByUsername("admin") == null) {
            UsuarioModel admin = new UsuarioModel(
                    "Administrador do Sistema",
                    "admin",
                    "1234",
                    "admin@imepac.com",
                    "(34) 99999-9999"
            );
            usuarioRepository.save(admin);
        }
    }

    public boolean autenticar(String username, String password) {
        UsuarioModel usuario = usuarioRepository.findByUsername(username);
        return usuario != null && usuario.getPassword().equals(password);
    }

    public UsuarioModel salvar(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<UsuarioModel> listarTodos() {
        return usuarioRepository.findAll();
    }

    public List<UsuarioModel> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    public java.util.Optional<UsuarioModel> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }
}
