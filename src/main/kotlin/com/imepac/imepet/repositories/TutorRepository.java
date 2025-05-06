package com.imepac.imepet.repositories;

import com.imepac.imepet.model.TutorModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TutorRepository {
    private final List<TutorModel> tutores = new ArrayList<>();

    public void salvar(TutorModel tutor) {
        tutores.add(tutor);
    }

    public List<TutorModel> listarTodos() {
        return tutores;
    }
}
