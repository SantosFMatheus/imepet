package com.imepac.imepet.controller;

import com.imepac.imepet.model.TutorModel;
import com.imepac.imepet.service.TutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tutores")
public class TutorController {

    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @GetMapping("")
    public String redirecionarParaLista() {
        return "tutorFormulario";
    }

    @GetMapping("/novo")
    public String novoTutor(Model model) {
        model.addAttribute("tutor", new TutorModel());
        return "tutorFormulario";
    }

    @PostMapping("/salvar")
    public String salvarTutor(@ModelAttribute TutorModel tutor) {
        if ("Outro".equalsIgnoreCase(tutor.getEstadoCivil()) &&
                tutor.getEstadoCivilOutro() != null &&
                !tutor.getEstadoCivilOutro().isBlank()) {

            tutor.setEstadoCivil(tutor.getEstadoCivilOutro());
        }

        tutorService.salvar(tutor);
        return "redirect:/tutores/listar";
    }

    @GetMapping("/listar")
    public String listarTutotes(Model model){
        model.addAttribute("tutores", tutorService.listarTodos());
        return "tutorPage";
    }
}
