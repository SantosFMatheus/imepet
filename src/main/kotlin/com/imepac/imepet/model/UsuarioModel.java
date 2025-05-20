package com.imepac.imepet.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios") // opcional: define o nome da tabela
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    // Construtor padrão obrigatório para JPA
    public UsuarioModel() {
    }

    // Construtor com campos (opcional, útil para testes ou inicialização)
    public UsuarioModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
