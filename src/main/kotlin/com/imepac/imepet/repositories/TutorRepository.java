package com.imepac.imepet.repositories;

import com.imepac.imepet.Dto.DadosTutorResumidoDTO;
import com.imepac.imepet.model.TutorModel;
import com.imepac.imepet.service.TutorService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorRepository extends JpaRepository<TutorModel, Long> {
    List<TutorModel> findByNomeContainingIgnoreCase(String nome);
}
