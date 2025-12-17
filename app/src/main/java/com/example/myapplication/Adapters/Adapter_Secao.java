package com.example.myapplication.Adapters;

import java.util.List;

public class Adapter_Secao {
    private String tituloSecao;
    private List<Integer> listadeFilmes;

    public Adapter_Secao(String tituloSecao, List<Integer> listadeFilmes) {
        this.tituloSecao = tituloSecao;
        this.listadeFilmes = listadeFilmes;
    }

    public String getTituloSecao() {
        return tituloSecao;
    }

    public List<Integer> getListadeFilmes() {
        return listadeFilmes;
    }
}
