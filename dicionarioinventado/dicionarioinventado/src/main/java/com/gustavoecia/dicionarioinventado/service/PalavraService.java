package com.gustavoecia.dicionarioinventado.service;

import com.gustavoecia.dicionarioinventado.model.Palavra;
import com.gustavoecia.dicionarioinventado.repository.PalavraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PalavraService {

    @Autowired
    private PalavraRepository repository;

    @Autowired
    private GeminiService gemini;

    public Palavra processarPalavra(String input) {

        Optional<Palavra> existente = repository.findByPalavraIgnoreCase(input);
        if (existente.isPresent()) {
            return existente.get();
        }

        Palavra p = new Palavra();
        p.setPalavra(input);
        p.setOrigem(gemini.verificarSeReal(input));
        p.setGenero(gemini.gerarGenero(input));
        p.setTipo(gemini.gerarTipo(input));
        String definicao = gemini.gerarDefinicao(input);
        p.setDefinicao(definicao);
        String exemplo = gemini.gerarExemploDeUso(input, definicao);
        p.setExemplodeuso(exemplo);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = LocalDateTime.now().format(formatter);
        p.setDataCriacao(dataFormatada);


        return repository.save(p);
    }

    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }



    public List<Palavra> listarTodas() {
        return repository.findAll();
    }

}
