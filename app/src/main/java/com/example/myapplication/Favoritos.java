package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Favoritos extends AppCompatActivity {

    RecyclerView recyclerSecoes;
    List<Adapter_Secao> listaDeSecoes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favoritos);

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
        List<Filme> listaFavoritos = new ArrayList<>();
        listaFavoritos.add(new Filme("Favorito 1", R.drawable.bob_esponja_filme));
        listaFavoritos.add(new Filme("Favorito 2", R.drawable.bob_esponja_filme));
        listaFavoritos.add(new Filme("Favorito 3", R.drawable.bob_esponja_filme));
        listaFavoritos.add(new Filme("Favorito 4", R.drawable.bob_esponja_filme));
        listaFavoritos.add(new Filme("Favorito 5", R.drawable.bob_esponja_filme));

        listaDeSecoes.add(new Adapter_Secao("Minha Lista", listaFavoritos));
        listaDeSecoes.add(new Adapter_Secao("Assistir Novamente", listaFavoritos));
        listaDeSecoes.add(new Adapter_Secao("Baixados", listaFavoritos));
    }

    private void configurarRecycler() {
        recyclerSecoes.setLayoutManager(new LinearLayoutManager(this));
        recyclerSecoes.setNestedScrollingEnabled(false);
        recyclerSecoes.setAdapter(new Adapter_Main(this, listaDeSecoes));
    }

    public void home(View v) { startActivity(new Intent(this, Activity_Home.class)); }
    public void emAlta(View v) { startActivity(new Intent(this, EmAlta.class)); }
}