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
            UsuarioModel usuario = usuarioService.buscarPorUsername(username);
            session.setAttribute("usuarioLogado", usuario);

            // Define se é admin
            boolean isAdmin = usuario.getUsername().equalsIgnoreCase("admin");
            session.setAttribute("isAdmin", isAdmin);

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
            model.addAttribute("isAdmin", session.getAttribute("isAdmin")); // <-- Adiciona aqui
        } else {
            model.addAttribute("nomeUsuario", "Visitante");
            model.addAttribute("isAdmin", false); // valor padrão
        }

        return "usuarioPage";
    }


}
