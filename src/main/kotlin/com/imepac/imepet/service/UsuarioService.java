package com.imepac.imepet.service;

import com.imepac.imepet.model.UsuarioModel;
import com.imepac.imepet.repositories.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Cria um usuário administrador padrão no banco de dados
     * caso ainda não exista ao iniciar a aplicação.
     */
    @PostConstruct
    public void initAdmin() {
        if (usuarioRepository.findByUsername("admin") == null) {
            UsuarioModel admin = new UsuarioModel(
                    "Administrador do Sistema",
                    "admin",
                    "imepet@1234", // ⚠️ Você pode depois criptografar essa senha
                    "admin@imepac.com",
                    "(34) 99999-9999"
            );
            usuarioRepository.save(admin);
        }
    }

    // ----------- CRUD Principal -----------

    public UsuarioModel salvar(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<UsuarioModel> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<UsuarioModel> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }

    // ----------- Buscas Personalizadas -----------

    public UsuarioModel buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public List<UsuarioModel> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    // ----------- Autenticação (Simples) -----------

    public boolean autenticar(String username, String password) {
        UsuarioModel usuario = usuarioRepository.findByUsername(username);
        return usuario != null && usuario.getPassword().equals(password);
    }
}
