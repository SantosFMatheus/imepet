package com.imepac.imepet.controller;

import com.imepac.imepet.model.UsuarioModel;
import com.imepac.imepet.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "index"; // index.html será a tela de login
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {

        if (usuarioService.autenticar(username, password)) {
            // Recupera o usuário completo pelo username para pegar o nome
            UsuarioModel usuario = usuarioService.buscarPorUsername(username);
            // Salva o usuário na sessão
            session.setAttribute("usuarioLogado", usuario);
            return "redirect:/usuarioPage";
        } else {
            model.addAttribute("error", "Usuário ou senha inválidos");
            return "index";
        }
    }

    @GetMapping("/usuarioPage")
    public String showUsuarioPage(HttpSession session, Model model) {
        UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuarioLogado");
        if (usuario != null) {
            model.addAttribute("nomeUsuario", usuario.getNome());
        } else {
            model.addAttribute("nomeUsuario", "Visitante");
        }
        return "usuarioPage"; // Renderiza o arquivo usuarioPage.html em templates
    }
}
