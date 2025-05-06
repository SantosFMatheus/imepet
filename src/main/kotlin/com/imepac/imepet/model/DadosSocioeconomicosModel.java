package com.imepac.imepet.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class DadosSocioeconomicosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String localTrabalho;

    private BigDecimal remuneracaoTotal;

    private Boolean outrasFontesRenda;

    private BigDecimal valorOutrasRendas;

    private Boolean possuiContaBancaria;

    private String banco;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalTrabalho() {
        return localTrabalho;
    }

    public void setLocalTrabalho(String localTrabalho) {
        this.localTrabalho = localTrabalho;
    }

    public BigDecimal getRemuneracaoTotal() {
        return remuneracaoTotal;
    }

    public void setRemuneracaoTotal(BigDecimal remuneracaoTotal) {
        this.remuneracaoTotal = remuneracaoTotal;
    }

    public Boolean getOutrasFontesRenda() {
        return outrasFontesRenda;
    }

    public void setOutrasFontesRenda(Boolean outrasFontesRenda) {
        this.outrasFontesRenda = outrasFontesRenda;
    }

    public BigDecimal getValorOutrasRendas() {
        return valorOutrasRendas;
    }

    public void setValorOutrasRendas(BigDecimal valorOutrasRendas) {
        this.valorOutrasRendas = valorOutrasRendas;
    }

    public Boolean getPossuiContaBancaria() {
        return possuiContaBancaria;
    }

    public void setPossuiContaBancaria(Boolean possuiContaBancaria) {
        this.possuiContaBancaria = possuiContaBancaria;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }
}
