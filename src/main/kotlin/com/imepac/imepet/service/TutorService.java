package com.imepac.imepet.service;

import com.imepac.imepet.model.TutorModel;
import com.imepac.imepet.repositories.TutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;

    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    public void salvar(TutorModel tutor) {
        tutorRepository.salvar(tutor);
    }

    public List<TutorModel> listarTodos() {
        return tutorRepository.listarTodos();
    }
}
