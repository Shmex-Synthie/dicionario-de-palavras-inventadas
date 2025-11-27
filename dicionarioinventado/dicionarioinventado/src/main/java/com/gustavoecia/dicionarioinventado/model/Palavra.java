package com.gustavoecia.dicionarioinventado.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "palavras")
public class Palavra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String palavra;

    @Column(length = 1) // M ou F
    private String genero;

    @Column(nullable = false)
    private String tipo; // substantivo, adjetivo, verbo, outro

    @Column(columnDefinition = "TEXT")
    private String definicao;

    @Column(columnDefinition = "TEXT")
    private String exemplodeuso;

    @Column(nullable = false)
    private String origem;

    @Column(nullable = false)
    private String dataCriacao;

    public Palavra() {}

    // ---------- GETTERS E SETTERS ----------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getDefinicao() {
        return definicao;
    }

    public void setDefinicao(String definicao) {
        this.definicao = definicao;
    }

    public String getExemplodeuso() {
        return exemplodeuso;
    }

    public void setExemplodeuso(String exemplodeuso) {
        this.exemplodeuso = exemplodeuso;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
