    package com.imepac.imepet.controller;

    import com.imepac.imepet.model.UsuarioModel;
    import com.imepac.imepet.service.UsuarioService;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    @Controller
    @RequestMapping("/usuarios")
    public class UsuarioController {

        private final UsuarioService usuarioService;

        public UsuarioController(UsuarioService usuarioService) {
            this.usuarioService = usuarioService;
        }

        @GetMapping
        public String listarUsuarios(Model model) {
            model.addAttribute("usuarios", usuarioService.listarTodos());
            model.addAttribute("usuarioModel", new UsuarioModel()); // necessário para o formulário funcionar
            return "usuarioPage";
        }

        @GetMapping("/novo")
        public String novoUsuario(Model model) {
            model.addAttribute("usuario", new UsuarioModel());
            return "usuario/formulario";
        }

        @PostMapping("/salvar")
        public String salvarUsuario(@ModelAttribute UsuarioModel usuario) {
            usuarioService.salvar(usuario);
            return "redirect:/usuarios";
        }

        @GetMapping("/editar/{id}")
        public String editarUsuario(@PathVariable Long id, Model model) {
            UsuarioModel usuario = usuarioService.buscarPorId(id);
            model.addAttribute("usuario", usuario);
            return "usuario/formulario";
        }

        @GetMapping("/deletar/{id}")
        public String deletarUsuario(@PathVariable Long id) {
            usuarioService.deletar(id);
            return "redirect:/usuarios";
        }
    }
