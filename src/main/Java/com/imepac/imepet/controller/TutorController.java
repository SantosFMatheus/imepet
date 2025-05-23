package com.imepac.imepet.controller;

import com.imepac.imepet.Dto.TutorCompleto;
import com.imepac.imepet.service.DadosSocioeconomicosService;
import com.imepac.imepet.service.TutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tutores")
public class TutorController {

    private final TutorService tutorService;
    private final DadosSocioeconomicosService dadosSocioeconomicosService;

    public TutorController(TutorService tutorService, DadosSocioeconomicosService dadosSocioeconomicosService) {
        this.tutorService = tutorService;
        this.dadosSocioeconomicosService = dadosSocioeconomicosService;
    }

    @GetMapping("/novo")
    public String novoTutor(Model model) {
        model.addAttribute("tutorCompleto", new TutorCompleto());
        return "tutorPage";
    }

    @PostMapping("/salvar")
    public String salvarTutor(@ModelAttribute("form") TutorCompleto form) {
        tutorService.salvar(form.getTutor());
        dadosSocioeconomicosService.salvar(form.getDadosSocioeconomicos());
        return "redirect:/tutores/listar";
    }

    @GetMapping("/listar")
    public String listarTutores(Model model) {
        model.addAttribute("tutores", tutorService.listarTodos());
        return "usuarioPage";
    }

    @GetMapping("/resumo")
    public String listarResumoTutores(Model model) {
        model.addAttribute("tutores", tutorService.listarCamposResumidos());
        return "resumoTutoresPage"; // Esse é o nome do seu HTML para a tabela resumida
    }

}
