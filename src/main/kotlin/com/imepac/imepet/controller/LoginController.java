package com.imepac.imepet.controller;

import com.imepac.imepet.model.UsuarioModel;
import com.imepac.imepet.service.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String showRootLoginPage() {
        return "index"; // index.html será a tela de login
    }

    // Adicione este mapeamento GET para /login
    // Isso garante que se houver um redirect para /login, ele seja tratado como GET
    @GetMapping("/login")
    public String showLoginPage() {
        return "index"; // index.html será a tela de login para a URL /login
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
    public String showUsuarioPage(HttpSession session, Model model, HttpServletResponse response) {
        UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuarioLogado");

        if (usuario != null) {
            model.addAttribute("nomeUsuario", usuario.getNome());
            model.addAttribute("isAdmin", session.getAttribute("isAdmin"));
        } else {
            model.addAttribute("nomeUsuario", "Visitante");
            model.addAttribute("isAdmin", false);
        }

        // Headers para evitar cache, conforme sugerido anteriormente
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.

        return "usuarioPage";
    }


}
