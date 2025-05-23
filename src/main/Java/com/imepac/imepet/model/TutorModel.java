package com.imepac.imepet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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

    private String cep;
    private String municipio;
    private String uf;
    private String rua;
    private String numero;
    private String bairro;
    private String telefone;

    private String tipoResidencia; // Urbana ou Rural
    private String situacaoImovel; // Próprio, Financiado, Cedido, Alugado
    private BigDecimal valorAluguel;


    @OneToOne(mappedBy = "tutor", cascade = CascadeType.ALL)
    private DadosSocioeconomicosModel dadosSocioeconomicos;

    // Getters e Setters

    // Construtores padrão e com argumentos, se desejar
}
