package com.imepac.imepet.service;

import com.imepac.imepet.model.DadosSocioeconomicosModel;
import com.imepac.imepet.repositories.DadosSocioeconomicosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DadosSocioeconomicosService {

    @Autowired
    private DadosSocioeconomicosRepository repository;

    public DadosSocioeconomicosModel salvar(DadosSocioeconomicosModel dados) {
        return repository.save(dados);
    }

    public List<DadosSocioeconomicosModel> listarTodos() {
        return repository.findAll();
    }

    public Optional<DadosSocioeconomicosModel> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
