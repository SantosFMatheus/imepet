package com.imepac.imepet.controller;

import com.imepac.imepet.model.AnimalModel;
import com.imepac.imepet.service.AnimalService;
import com.imepac.imepet.service.TutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/animais")
public class AnimalController {

    private final AnimalService animalService;
    private final TutorService tutorService;

    public AnimalController(AnimalService animalService, TutorService tutorService) {
        this.animalService = animalService;
        this.tutorService = tutorService;
    }

    @GetMapping("/cadastro")
    public String formularioAnimal(Model model) {
        model.addAttribute("animalModel", new AnimalModel());
        model.addAttribute("tutores", tutorService.listarTodos());
        model.addAttribute("animais", animalService.listarTodos());
        return "animalPage"; // deve ter um HTML com esse nome em templates
    }

    @PostMapping("/salvar")
    public String salvarAnimal(@ModelAttribute AnimalModel animalModel) {
        animalService.salvar(animalModel);
        return "redirect:/animais/cadastro";
    }

    @GetMapping("/excluir/{id}")
    public String excluirAnimal(@PathVariable int id) {
        animalService.excluir(id);
        return "redirect:/animais/cadastro";
    }
}
