package com.imepac.imepet.service;

import com.imepac.imepet.model.UsuarioModel;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    private final List<UsuarioModel> usuarios = List.of(
            new UsuarioModel("admin", "1234"),
            new UsuarioModel("user", "senha")
    );

    public boolean autenticar(String username, String password) {
        return usuarios.stream()
                .anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
    }
}
