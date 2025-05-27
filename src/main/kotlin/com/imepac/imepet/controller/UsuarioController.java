package com.imepac.imepet.controller;

import com.imepac.imepet.Dto.UsuarioResumoDTO;
import com.imepac.imepet.model.UsuarioModel;
import com.imepac.imepet.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Encerra a sessão
        return "redirect:/login"; // Redireciona para a página de login
    }

    // ========================
    // === PÁGINA: CADASTRO ===
    // ========================

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("usuario", new UsuarioModel());
        return "cadastroAdminPage"; // Página com o formulário de novo administrador
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute("usuario") UsuarioModel usuario) {
        usuarioService.salvar(usuario);
        return "popup-success"; // Página de sucesso após salvar
    }

    // ========================
    // === LISTAGEM COMPLETA ==
    // ========================

    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        List<UsuarioModel> usuarios = usuarioService.listarTodos();
        model.addAttribute("admins", usuarios); // <-- nome compatível com o HTML
        return "usuariosListPage";
    }

    // ====================================
    // === LISTAGEM VIA JSON (RESUMIDA) ===
    // ====================================

    @GetMapping("/resumidos")
    @ResponseBody
    public ResponseEntity<List<UsuarioResumoDTO>> listarUsuariosResumidos(@RequestParam(required = false) String nome) {
        List<UsuarioModel> usuarios = (nome != null && !nome.trim().isEmpty())
                ? usuarioService.buscarPorNome(nome)
                : usuarioService.listarTodos();

        // Mapeia para DTO
        List<UsuarioResumoDTO> dtos = usuarios.stream()
                .map(UsuarioResumoDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // ========================
    // === EXCLUSÃO DE USUÁRIO ==
    // ========================

    @DeleteMapping("/excluir/{id}")
    @ResponseBody
    public ResponseEntity<String> excluirUsuario(@PathVariable Long id) {
        try {
            usuarioService.excluir(id);
            return ResponseEntity.ok("Usuário excluído com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir usuário: " + e.getMessage());
        }
    }
}
