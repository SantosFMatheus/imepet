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
        return "redirect:/tutores/listar";
    }

    @GetMapping("/novo")
    public String novoTutor(Model model) {
        model.addAttribute("tutor", new TutorModel());
        return "tutorPage";
    }

    @PostMapping("/salvar")
    public String salvarTutor(@ModelAttribute TutorModel tutor) {
        tutorService.salvar(tutor);
        return "redirect:/tutorPage";
    }

    @GetMapping("/listar")
    public String listarTutores(Model model) {
        model.addAttribute("tutores", tutorService.listarTodos());
        return "tutorPage"; // ⚠ isso só faz sentido se você mostrar os tutores lá
    }

}

