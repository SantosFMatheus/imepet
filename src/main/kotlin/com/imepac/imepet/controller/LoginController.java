package com.imepac.imepet.controller;

import com.imepac.imepet.service.UsuarioService;
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
                              Model model) {

        if (usuarioService.autenticar(username, password)) {
            return "redirect:/usuarioPage"; // Redireciona para /usuarioPage (GET)
        } else {
            model.addAttribute("error", "Usuário ou senha inválidos");
            return "index";
        }
    }

    @GetMapping("/usuarioPage")
    public String showUsuarioPage() {
        return "usuarioPage"; // Renderiza o arquivo usuarioPage.html em templates
    }
}
