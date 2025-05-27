package com.imepac.imepet.repositories;

import com.imepac.imepet.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    UsuarioModel findByUsername(String username);
    List<UsuarioModel> findByNomeContainingIgnoreCase(String nome);
}
