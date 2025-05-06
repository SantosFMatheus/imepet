package com.imepac.imepet.controller;

import com.imepac.imepet.model.DadosSocioeconomicosModel;
import com.imepac.imepet.service.DadosSocioeconomicosService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/socioeconomico")
public class DadosSocioeconomicosController {

    private final DadosSocioeconomicosService service;

    public DadosSocioeconomicosController(DadosSocioeconomicosService service) {
        this.service = service;
    }

    @GetMapping
    public String exibirFormulario(Model model) {
        model.addAttribute("dados", new DadosSocioeconomicosModel());
        return "socioeconomicoForm";
    }

    @PostMapping("/salvar")
    public String salvarFormulario(@ModelAttribute DadosSocioeconomicosModel dados) {
        service.salvar(dados);
        return "redirect:/sucesso"; // ou próxima etapa
    }
}
