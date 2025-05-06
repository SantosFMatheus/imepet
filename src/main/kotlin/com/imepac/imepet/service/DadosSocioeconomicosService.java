package com.imepac.imepet.service;

import com.imepac.imepet.model.DadosSocioeconomicosModel;
import com.imepac.imepet.repositories.DadosSocioeconomicosRepository;
import org.springframework.stereotype.Service;

@Service
public class DadosSocioeconomicosService {

    private final DadosSocioeconomicosRepository repository;

    public DadosSocioeconomicosService(DadosSocioeconomicosRepository repository) {
        this.repository = repository;
    }

    public void salvar(DadosSocioeconomicosModel dados) {
        repository.save(dados);
    }
}
