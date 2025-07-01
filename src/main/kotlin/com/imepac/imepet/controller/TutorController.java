package com.imepac.imepet.controller;

import com.imepac.imepet.Dto.AtualizarStatusDTO;
import com.imepac.imepet.Dto.TutorCompleto;
import com.imepac.imepet.model.DadosSocioeconomicosModel;
import com.imepac.imepet.service.DadosSocioeconomicosService;
import com.imepac.imepet.service.TutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.imepac.imepet.Dto.DadosTutorResumidoDTO;
import com.imepac.imepet.model.TutorModel;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.stream.Collectors;

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
        TutorModel tutor = form.getTutor();
        DadosSocioeconomicosModel dados = form.getDadosSocioeconomicos();

        // Conecta os dois lados da relação
        dados.setTutor(tutor);
        tutor.setDadosSocioeconomicos(dados);

        // Salva apenas o tutor — o JPA cuida do resto via cascade
        tutorService.salvar(tutor);

        return "popup-success"; // Página para fechar o popup via JS
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

    // NOVO ENDPOINT PARA EXPORTAR TODOS OS DADOS
    @GetMapping("/todos-dados")
    @ResponseBody
    public List<TutorCompleto> listarTodosOsDadosDeTutores() {
        return tutorService.listarTodos().stream()
                .map(TutorCompleto::new) // Mapeia cada TutorModel para TutorCompleto
                .collect(Collectors.toList());
    }

    @GetMapping("/editar/{id}")
    public String editarTutor(@PathVariable Long id, Model model) {
        TutorModel tutor = tutorService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado com id: " + id));

        model.addAttribute("tutorCompleto", new TutorCompleto(tutor));
        return "tutorPageEdicao";
    }

    @PostMapping("/atualizar")
    public String atualizarTutor(@ModelAttribute TutorCompleto form) {
        TutorModel tutorAtualizado = form.getTutor();
        DadosSocioeconomicosModel dadosAtualizados = form.getDadosSocioeconomicos();

        dadosAtualizados.setTutor(tutorAtualizado);
        tutorAtualizado.setDadosSocioeconomicos(dadosAtualizados);

        tutorService.atualizarTutorExistente(tutorAtualizado);
        return "popup-success";  // aqui vai renderizar a página popup-success.html
    }

    @PostMapping("/{id}/status")
    @ResponseBody
    public ResponseEntity<String> atualizarStatus(@PathVariable Long id, @ModelAttribute AtualizarStatusDTO dto) {
        try {
            tutorService.atualizarStatus(id, dto.getStatus());
            return ResponseEntity.ok("Status atualizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar status.");
        }
    }

    @PostMapping("/deletar")
    @ResponseBody
    public ResponseEntity<String> deletarTutor(@RequestParam Long id) {
        try {
            tutorService.deletarPorId(id); // Vai apagar tutor e tudo relacionado via cascade
            return ResponseEntity.ok("Tutor deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar tutor.");
        }
    }
}