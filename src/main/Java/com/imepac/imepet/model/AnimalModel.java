package com.imepac.imepet.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class AnimalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnimal;

    private String raca;
    private String nome;
    private String especie;
    private boolean sexo;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @ManyToOne
    @JoinColumn(name = "id_tutor")
    private TutorModel tutor;

    // Getters e Setters
    public Long getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(Long idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public TutorModel getTutor() {
        return tutor;
    }

    public void setTutor(TutorModel tutor) {
        this.tutor = tutor;
    }
}
