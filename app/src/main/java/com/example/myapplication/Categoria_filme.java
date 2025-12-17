package com.example.myapplication;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Categoria_filme extends AppCompatActivity {

    RecyclerView recyclerSecoes;
    List<Adapter_Secao> listaDeSecoes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categoria_filme);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerSecoes = findViewById(R.id.recyclerFilmesCategoria);

        carregarDados();
        configurarRecycler();
    }

    private void carregarDados() {
        List<Filme> listaFilmes = new ArrayList<>();
        listaFilmes.add(new Filme("Bob Esponja", R.drawable.bob_esponja_filme));
        listaFilmes.add(new Filme("Patrick", R.drawable.bob_esponja_filme));
        listaFilmes.add(new Filme("Sandy", R.drawable.bob_esponja_filme));
        listaFilmes.add(new Filme("Lula Molusco", R.drawable.bob_esponja_filme));
        listaFilmes.add(new Filme("Seu Sirigueijo", R.drawable.bob_esponja_filme));

        listaDeSecoes.add(new Adapter_Secao("Filmes Novos", listaFilmes));
        listaDeSecoes.add(new Adapter_Secao("Ação", listaFilmes));
        listaDeSecoes.add(new Adapter_Secao("Aventura", listaFilmes));
    }

    private void configurarRecycler() {
        recyclerSecoes.setLayoutManager(new LinearLayoutManager(this));
        recyclerSecoes.setAdapter(new Adapter_Main(this, listaDeSecoes));
    }
}