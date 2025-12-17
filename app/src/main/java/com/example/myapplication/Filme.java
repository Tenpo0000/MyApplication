package com.example.myapplication;

public class Filme {
    private String nome;
    private int imagem;

    public Filme(String nome, int imagem) {
        this.nome = nome;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public int getImagem() {
        return imagem;
    }
}