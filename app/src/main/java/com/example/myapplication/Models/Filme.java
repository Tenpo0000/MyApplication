package com.example.myapplication.Models;

public class Filme {
    private String nome;
    private int imagemResId;
    private String descricao; // 1. Novo campo para o texto

    // 2. Construtor COMPLETO (Usaremos este nos Destaques)
    public Filme(String nome, int imagemResId, String descricao) {
        this.nome = nome;
        this.imagemResId = imagemResId;
        this.descricao = descricao;
    }

    // 3. Construtor SIMPLES (Mantém esse para não dar erro nas outras listas que não têm texto)
    public Filme(String nome, int imagemResId) {
        this.nome = nome;
        this.imagemResId = imagemResId;
        this.descricao = ""; // Se não passar descrição, fica vazio
    }

    public String getNome() { return nome; }
    public int getImagemResId() { return imagemResId; }

    // 4. Novo Getter para recuperar o texto
    public String getDescricao() { return descricao; }
}