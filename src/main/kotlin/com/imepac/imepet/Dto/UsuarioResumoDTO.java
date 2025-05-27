package com.imepac.imepet.Dto;

import com.imepac.imepet.model.UsuarioModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class UsuarioResumoDTO {
    private Long id;
    private String nome;
    private String usuario; // <- esse campo será preenchido a partir de username
    private String emailResponsavel;
    private String telefone;
    private LocalDate dataCadastro;

    public UsuarioResumoDTO(UsuarioModel usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.usuario = usuario.getUsername(); // ← aqui está o mapeamento
        this.emailResponsavel = usuario.getEmail();
        this.telefone = usuario.getTelefone();
        this.dataCadastro = usuario.getDataCadastro();
    }
}

