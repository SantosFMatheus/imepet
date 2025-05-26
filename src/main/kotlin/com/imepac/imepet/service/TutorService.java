package com.imepac.imepet.service;

import com.imepac.imepet.model.TutorModel;
import com.imepac.imepet.repositories.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class TutorService {

    private final TutorRepository tutorRepository;

    @Autowired
    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    public TutorModel salvar(TutorModel tutor) {
        if (tutor.getStatus() == null) {
            tutor.setStatus("Em análise");
        }
        return tutorRepository.save(tutor);
    }

    public List<TutorModel> listarTodos() {
        return tutorRepository.findAll();
    }

    public Optional<TutorModel> buscarPorId(Long id) {
        return tutorRepository.findById(id);
    }

    public void deletarPorId(Long id) {
        tutorRepository.deleteById(id);
    }

    public List<Map<String, Object>> listarCamposResumidos() {
        return tutorRepository.findAll().stream().map(tutor -> {
            Map<String, Object> dados = new HashMap<>();
            dados.put("id", tutor.getId());
            dados.put("nome", tutor.getNome());
            dados.put("dataNascimento", tutor.getDataNascimento());
            dados.put("status", tutor.getStatus());
            dados.put("cpf", tutor.getCpf());
            dados.put("rg", tutor.getRg());
            dados.put("celular", tutor.getCelular());
            return dados;
        }).toList();
    }

    public List<Map<String, Object>> listarCamposResumidosPorNome(String nome) {
        return tutorRepository.findByNomeContainingIgnoreCase(nome).stream().map((TutorModel tutor) -> {
            Map<String, Object> dados = new HashMap<>();
            dados.put("id", tutor.getId());
            dados.put("nome", tutor.getNome());
            dados.put("dataNascimento", tutor.getDataNascimento());
            dados.put("status", tutor.getStatus());
            dados.put("cpf", tutor.getCpf());
            dados.put("rg", tutor.getRg());
            dados.put("celular", tutor.getCelular());
            return dados;
        }).toList();
    }

    public List<TutorModel> buscarPorNome(String nome) {
        return tutorRepository.findByNomeContainingIgnoreCase(nome);
    }

    public boolean existePorId(Long id) {
        return tutorRepository.existsById(id);
    }


}
