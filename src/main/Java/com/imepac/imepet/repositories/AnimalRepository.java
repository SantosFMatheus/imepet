package com.imepac.imepet.repositories;

import com.imepac.imepet.model.AnimalModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<AnimalModel, Integer> {
}
