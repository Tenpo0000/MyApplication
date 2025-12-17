package com.example.myapplication;

public class Filme {
    private String nome;
    private int imagemResId;

    public Filme(String nome, int imagemResId) {
        this.nome = nome;
        this.imagemResId = imagemResId;
    }

    public String getNome() { return nome; }
    public int getImagemResId() { return imagemResId; }
}