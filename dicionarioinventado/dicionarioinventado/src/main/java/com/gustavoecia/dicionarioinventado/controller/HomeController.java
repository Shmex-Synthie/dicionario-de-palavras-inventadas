package com.gustavoecia.dicionarioinventado.controller;

import com.gustavoecia.dicionarioinventado.model.Palavra;
import com.gustavoecia.dicionarioinventado.repository.PalavraRepository;
import com.gustavoecia.dicionarioinventado.service.PalavraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private PalavraService service;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/definir")
    public String definir(@RequestParam("palavra") String palavra, Model model) {

        Palavra p = service.processarPalavra(palavra);

        model.addAttribute("palavra", p.getPalavra());
        model.addAttribute("definicao", p.getDefinicao());
        model.addAttribute("tipo", p.getTipo());
        model.addAttribute("genero", p.getGenero());
        model.addAttribute("origem", p.getOrigem());
        model.addAttribute("exemplo", p.getExemplodeuso());

        return "resultado";
    }

    @GetMapping("/palavras")
    public String listarPalavras(Model model) {
        model.addAttribute("lista", service.listarTodas());
        return "palavras";
    }

    @GetMapping("/deletar")
    public String deletar(@RequestParam("id") Long id) {

        service.deletarPorId(id);
        return "redirect:/palavras"; // volta pra lista atualizada
    }



}
