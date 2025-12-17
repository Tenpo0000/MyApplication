package com.example.myapplication;

import java.util.List;

public class Adapter_Secao {
    private String tituloSecao;
    // Mudou de Integer para Filme
    private List<Filme> listadeFilmes;

    public Adapter_Secao(String tituloSecao, List<Filme> listadeFilmes) {
        this.tituloSecao = tituloSecao;
        this.listadeFilmes = listadeFilmes;
    }

    public String getTituloSecao() {
        return tituloSecao;
    }

    // Mudou o retorno aqui também
    public List<Filme> getListadeFilmes() {
        return listadeFilmes;
    }
}