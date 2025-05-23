package com.imepac.imepet.service;

import com.imepac.imepet.model.AnimalModel;
import com.imepac.imepet.repositories.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public void salvar(AnimalModel animal) {
        animalRepository.save(animal);
    }

    public List<AnimalModel> listarTodos() {
        return animalRepository.findAll();
    }

    public AnimalModel buscarPorId(int id) {
        return animalRepository.findById(id).orElse(null);
    }

    public void excluir(int id) {
        animalRepository.deleteById(id);
    }
}
