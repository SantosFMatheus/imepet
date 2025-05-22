package com.imepac.imepet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class DadosSocioeconomicosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String situacaoMoradia;
    private String moradiaColetivaEspecificacao;
    private Double valorAluguel;
    private String outrosEspecificacao;

    private String localTrabalho;
    private Double valorRemuneracao;

    private String temOutrasFontesDeRenda; // "Sim" ou "Não"
    private Double valorOutrasFontesDeRenda;

    private String temContaBancaria; // "Sim" ou "Não"
    private String nomeBanco;

    private String localTrabalhoPai;
    private String localTrabalhoMae;
    private String localTrabalhoConjuge;
    private String localTrabalhoFilhos;

    private BigDecimal rendaPai;
    private BigDecimal rendaMae;
    private BigDecimal rendaConjuge;
    private BigDecimal valorTotalRemuneracao;

    private Integer numeroPessoasGrupoFamiliar;
    private String programaSocial;
    private String outroProgramaSocial;
    private String bensFamiliares;

    @OneToOne
    @JoinColumn(name = "tutor_id")
    private TutorModel tutor;
}
