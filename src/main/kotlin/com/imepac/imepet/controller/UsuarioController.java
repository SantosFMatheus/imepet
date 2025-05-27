package com.imepac.imepet.controller;

import com.imepac.imepet.model.UsuarioModel;
import com.imepac.imepet.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/novo")
    public String novoUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioModel());
        return "cadastroAdminPage";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute("usuario") UsuarioModel usuario, Model model) {
        usuarioService.salvar(usuario);
        return "popup-success"; // Página de confirmação
    }

    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        List<UsuarioModel> usuarios = usuarioService.listarTodos();
        model.addAttribute("usuarios", usuarios);
        return "usuariosListPage";
    }

    @GetMapping("/resumidos")
    @ResponseBody
    public List<UsuarioModel> listarUsuariosResumidos(@RequestParam(required = false) String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            return usuarioService.buscarPorNome(nome);
        } else {
            return usuarioService.listarTodos();
        }
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        UsuarioModel usuario = usuarioService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));
        model.addAttribute("usuario", usuario);
        return "usuarioPageEdicao";
    }

    @DeleteMapping("/excluir/{id}")
    @ResponseBody
    public ResponseEntity<String> excluirUsuario(@PathVariable Long id) {
        try {
            usuarioService.excluir(id);
            return ResponseEntity.ok("Usuário excluído com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir usuário.");
        }
    }
}
