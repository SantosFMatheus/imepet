package com.imepac.imepet.controller;

import com.imepac.imepet.Dto.TutorCompleto;
import com.imepac.imepet.service.DadosSocioeconomicosService;
import com.imepac.imepet.service.TutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.imepac.imepet.Dto.DadosTutorResumidoDTO;
import com.imepac.imepet.model.TutorModel;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.stream.Collectors;

import com.imepac.imepet.Dto.DadosTutorResumidoDTO;
import com.imepac.imepet.model.TutorModel;

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
    public String salvarTutor(@ModelAttribute("form") TutorCompleto form, Model model) {
        tutorService.salvar(form.getTutor());
        dadosSocioeconomicosService.salvar(form.getDadosSocioeconomicos());
        return "popup-success"; // ← uma nova página só com JS para fechar o popup
    }

    @GetMapping("/listar")
    public String listarTutores(Model model) {
        model.addAttribute("tutores", tutorService.listarCamposResumidos());
        return "mainPage";
    }

    @GetMapping("/resumidos")
    @ResponseBody
    public List<DadosTutorResumidoDTO> listarTutoresResumidos(@RequestParam(required = false) String nome) {
        List<TutorModel> tutores;

        if (nome != null && !nome.trim().isEmpty()) {
            tutores = tutorService.buscarPorNome(nome);
        } else {
            tutores = tutorService.listarTodos();
        }

        return tutores.stream()
                .map(DadosTutorResumidoDTO::new)
                .collect(Collectors.toList());
    }
    @GetMapping("/editar/{id}")
    public String editarTutor(@PathVariable Long id, Model model) {
        TutorModel tutor = tutorService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado com id: " + id));        model.addAttribute("tutorCompleto", new TutorCompleto(tutor));
        return "tutorPageEdicao";
    }


}
