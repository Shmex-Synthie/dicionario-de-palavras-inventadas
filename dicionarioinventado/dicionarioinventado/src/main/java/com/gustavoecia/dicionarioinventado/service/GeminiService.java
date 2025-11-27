package com.gustavoecia.dicionarioinventado.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${api.gemini.key}")
    private String apiKey;

    private final WebClient webClient;

    public GeminiService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://generativelanguage.googleapis.com/v1beta")
                .build();
    }

    public String verificarSeReal(String palavra) {
        String prompt = "A palavra \"" + palavra + "\" é REAL ou INVENTADA? Responda apenas REAL ou INVENTADA.";
        return callGemini(prompt).trim().toUpperCase();
    }

    public String gerarGenero(String palavra) {
        String prompt =
                "A palavra \"" + palavra + "\" é masculina ou feminina? RESPONDA APENAS com a letra M OU F. NÃO explique, NÃO adicione texto, NÃO adicione palavras extras.";
        return limparGenero(callGemini(prompt));
    }

    public String gerarTipo(String palavra) {
        String prompt =
                "Indique se a palavra \"" + palavra + "\" é SUBSTANTIVO, ADJETIVO, VERBO ou OUTRO. Responda APENAS com uma dessas palavras exatas. Nada além disso.";

        return limparTipo(callGemini(prompt));
    }

    public String gerarDefinicao(String palavra) {
        String prompt = "Gere uma definição simples para a palavra \"" + palavra + "\", no formato de dicionário. Não adicione a palavra em si, nem formate seu texto, apenas entregue a definição e mais nada.";
        return callGemini(prompt);
    }

    public String gerarExemploDeUso(String palavra, String definicao) {
        String prompt = """
        A palavra "%s" possui a seguinte definição: "%s".
        
        Crie uma frase de exemplo que use a palavra corretamente
        de acordo com essa definição.
        
        NÃO explique nada.
        NÃO descreva a definição.
        Responda apenas com a frase.
        """.formatted(palavra, definicao);

        return callGemini(prompt);
    }



    private String callGemini(String prompt) {

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", prompt)))
                )
        );

        Map response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/models/gemini-flash-lite-latest:generateContent")
                        .queryParam("key", apiKey)   // <-- AQUI! Google exige ?key=
                        .build())
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        try {
            List candidates = (List) response.get("candidates");
            Map content = (Map) ((Map) candidates.get(0)).get("content");
            List parts = (List) content.get("parts");
            return (String) ((Map) parts.get(0)).get("text");
        } catch (Exception e) {
            return "Erro ao interpretar a resposta da IA.";
        }
    }

    private String limparGenero(String texto) {
        if (texto == null) return "";

        texto = texto.trim().toUpperCase();

        if (texto.contains("M")) return "M";
        if (texto.contains("F")) return "F";

        return "";
    }


    private String limparTipo(String texto) {
        if (texto == null) return "outro";

        texto = texto.trim().toLowerCase();

        if (texto.contains("sub")) return "substantivo";
        if (texto.contains("adj")) return "adjetivo";
        if (texto.contains("verb")) return "verbo";

        return "outro";
    }



}
