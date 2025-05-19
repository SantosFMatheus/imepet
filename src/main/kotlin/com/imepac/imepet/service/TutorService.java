package com.imepac.imepet.service;

import com.imepac.imepet.model.TutorModel;
import com.imepac.imepet.repositories.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;

    @Autowired
    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    public TutorModel salvar(TutorModel tutor) {
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

    public boolean existePorId(Long id) {
        return tutorRepository.existsById(id);
    }
}
