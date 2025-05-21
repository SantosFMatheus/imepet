package com.imepac.imepet.model;

import jakarta.persistence.*;

@Entity
public class DadosSocioeconomicosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String localTrabalho;
    private String valorRemuneracao;

    private String temOutrasFontesDeRenda; // "Sim" ou "Não"
    private Double valorOutrasFontesDeRenda;

    private String temContaBancaria; // "Sim" ou "Não"
    private String nomeBanco;

    @OneToOne
    @JoinColumn(name = "tutor_id")
    private TutorModel tutor;

    // Getters e Setters

    // Construtores padrão e com argumentos, se desejar
}
