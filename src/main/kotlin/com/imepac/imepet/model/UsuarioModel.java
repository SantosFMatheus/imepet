package com.imepac.imepet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "usuarios")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    private String telefone;

    private LocalDate dataCadastro;

    // Construtor padrão
    public UsuarioModel() {
        this.dataCadastro = LocalDate.now(); // data automática no construtor
    }

    // Construtor com campos principais (sem ID, que é gerado)
    public UsuarioModel(String nome, String username, String password, String email, String telefone) {
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = LocalDate.now();
    }
}