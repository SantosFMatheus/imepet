package com.imepac.imepet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Entity
public class TutorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String celular;
    private String cpf;
    private String rg;

    private LocalDate dataNascimento;
    private String naturalidade;
    private String estadoCivil;
    private String nomeMarido;

    private String temFilhos; // "Sim" ou "Não"
    private String quantidadeFilhos;

    @OneToOne(mappedBy = "tutor", cascade = CascadeType.ALL)
    private DadosSocioeconomicosModel dadosSocioeconomicos;

    // Getters e Setters

    // Construtores padrão e com argumentos, se desejar
}
