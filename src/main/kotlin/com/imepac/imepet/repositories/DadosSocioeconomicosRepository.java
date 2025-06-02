package com.imepac.imepet.repositories;

import com.imepac.imepet.model.DadosSocioeconomicosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DadosSocioeconomicosRepository extends JpaRepository<DadosSocioeconomicosModel, Long> {
}
