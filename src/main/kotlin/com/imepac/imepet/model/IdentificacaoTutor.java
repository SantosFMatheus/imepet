package com.imepac.imepet.model;

import java.util.Date;


//REQUERIMENTO PARA USUARIO DA CLINICA ESCOLA VETERINÁRIA
//IDENTIFICAÇÃO DO TUTOR

public class IdentificacaoTutor {
    private String nome;
    private String endereco;
    private Long celular;
    private long telefoneFixo;
    private String rg;
    private Long cpf;
    private Date dataNascimento;
    private String naturalidade;
    private String estadoCivil;       // ex: "Solteiro", "Casado(a)", ou "Outro"
    private String estadoCivilOutro;  // se "Outro", preencher esse campo
    private String filhos;
    private int filhosAfirmativo;


    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco(){return endereco;}
    public void setEndereco(String endereco) {this.endereco = endereco; }

    public Long getCelular() { return celular; }
    public void setCelular(Long celular) { this.celular = celular; }

    public Long telefoneFixo() { return telefoneFixo; }
    public void setTelefoneFixo(Long telefoneFixo) { this.telefoneFixo = telefoneFixo; }

    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }

    public Long getCpf() { return cpf; }
    public void setCpf(Long cpf) { this.cpf = cpf; }


    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getNaturalidade() { return naturalidade; }
    public void setNaturalidade(String naturalidade) { this.naturalidade = naturalidade; }

    public String getEstadoCivil() {return estadoCivil;}
    public void setEstadoCivil(String estadoCivil) {this.estadoCivil = estadoCivil;}

    public String getEstadoCivilOutro() {return estadoCivilOutro;}
    public void setEstadoCivilOutro(String estadoCivilOutro) {this.estadoCivilOutro = estadoCivilOutro;}

    public String getFilhos() {return filhos;}
    public void setFilhos(String filhos) {this.filhos = filhos;}

    public int getFilhosAfirmativo() {return this.filhosAfirmativo;}
    public void setFilhosAfirmativo(int filhosAfirmativo) {
        this.filhosAfirmativo = filhosAfirmativo;}

}
