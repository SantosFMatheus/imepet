package com.imepac.imepet.Dto;

import com.imepac.imepet.model.UsuarioModel;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioResumoDTO {
    private Long id;
    private String nome;
    private String usuario; // username
    private String emailResponsavel;
    private String telefone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCadastro;

    public UsuarioResumoDTO(UsuarioModel usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.usuario = usuario.getUsername();
        this.emailResponsavel = usuario.getEmail();
        this.telefone = usuario.getTelefone();
        this.dataCadastro = usuario.getDataCadastro();
    }
}
