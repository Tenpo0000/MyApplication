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

public class Categoria_Series extends AppCompatActivity {

    RecyclerView recyclerSecoes;
    List<Adapter_Secao> listaDeSecoes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categoria_series);

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
        // Cria a lista de Séries (usando o objeto Filme)
        List<Filme> listaSeries = new ArrayList<>();
        listaSeries.add(new Filme("Série 1", R.drawable.bob_esponja_filme));
        listaSeries.add(new Filme("Série 2", R.drawable.bob_esponja_filme));
        listaSeries.add(new Filme("Série 3", R.drawable.bob_esponja_filme));
        listaSeries.add(new Filme("Série 4", R.drawable.bob_esponja_filme));
        listaSeries.add(new Filme("Série 5", R.drawable.bob_esponja_filme));

        // Adiciona as seções usando a lista correta
        listaDeSecoes.add(new Adapter_Secao("Séries Novas", listaSeries));
        listaDeSecoes.add(new Adapter_Secao("Mais Assistidas", listaSeries));
        listaDeSecoes.add(new Adapter_Secao("Ação", listaSeries));
        listaDeSecoes.add(new Adapter_Secao("Comédia", listaSeries));
    }

    private void configurarRecycler() {
        recyclerSecoes.setLayoutManager(new LinearLayoutManager(this));
        recyclerSecoes.setNestedScrollingEnabled(false);
        recyclerSecoes.setAdapter(new Adapter_Main(this, listaDeSecoes));
    }

    // Botões do Menu
    public void home(View v) { startActivity(new Intent(this, Activity_Home.class)); }
    public void emAlta(View v) { startActivity(new Intent(this, EmAlta.class)); }
    public void favoritos(View v) { startActivity(new Intent(this, Favoritos.class)); }
}