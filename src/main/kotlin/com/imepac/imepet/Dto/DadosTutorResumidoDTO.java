package com.imepac.imepet.Dto;

import com.imepac.imepet.model.TutorModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
@Getter
@Setter


public class DadosTutorResumidoDTO {
    private Long id;
    private String nome;
    private String cpf;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String dataNascimento;
    private String rg;
    private String status;
    private String celular;

    public DadosTutorResumidoDTO(TutorModel tutor) {
        this.id = tutor.getId();
        this.nome = tutor.getNome();
        this.cpf = tutor.getCpf();
        this.dataNascimento = tutor.getDataNascimento().toString(); // ou formata como quiser
        this.celular = tutor.getCelular();
        this.rg = tutor.getRg(); // ← ADICIONE ESTA LINHA
        this.status = tutor.getStatus(); // ← ADICIONE ESTA LINHA
    }

    // Getters e setters (ou usa Lombok com @Getter @Setter)
}
