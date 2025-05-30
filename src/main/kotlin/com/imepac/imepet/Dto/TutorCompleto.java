package com.imepac.imepet.Dto;

import com.imepac.imepet.model.TutorModel;
import com.imepac.imepet.model.DadosSocioeconomicosModel;

public class TutorCompleto {
    private TutorModel tutor;
    private DadosSocioeconomicosModel dadosSocioeconomicos;

    public TutorCompleto() {
        this.tutor = new TutorModel();
        this.dadosSocioeconomicos = new DadosSocioeconomicosModel();
    }

    // Novo construtor adicionado
    public TutorCompleto(TutorModel tutor) {
        this.tutor = tutor;
        this.dadosSocioeconomicos = tutor.getDadosSocioeconomicos();
    }

    public TutorModel getTutor() {
        return tutor;
    }

    public void setTutor(TutorModel tutor) {
        this.tutor = tutor;
    }

    public DadosSocioeconomicosModel getDadosSocioeconomicos() {
        return dadosSocioeconomicos;
    }

    public void setDadosSocioeconomicos(DadosSocioeconomicosModel dadosSocioeconomicos) {
        this.dadosSocioeconomicos = dadosSocioeconomicos;
    }
}
