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

public class Categoria_TV extends AppCompatActivity {

    RecyclerView secoes;
    List<Adapter_Secao> listaDeSecoes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categoria_tv);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        secoes = findViewById(R.id.recyclerFilmesCategoria);

        carregarDados();
        configurarRecycler();
    }

    private void carregarDados() {
        List<Filme> listaTV = new ArrayList<>();
        listaTV.add(new Filme("Programa 1", R.drawable.bob_esponja_filme));
        listaTV.add(new Filme("Programa 2", R.drawable.bob_esponja_filme));
        listaTV.add(new Filme("Programa 3", R.drawable.bob_esponja_filme));
        listaTV.add(new Filme("Programa 4", R.drawable.bob_esponja_filme));

        listaDeSecoes.add(new Adapter_Secao("Canais ao Vivo", listaTV));
        listaDeSecoes.add(new Adapter_Secao("Esportes", listaTV));
        listaDeSecoes.add(new Adapter_Secao("Notícias", listaTV));
        listaDeSecoes.add(new Adapter_Secao("Desenhos", listaTV));
    }

    private void configurarRecycler() {
        secoes.setLayoutManager(new LinearLayoutManager(this));
        secoes.setNestedScrollingEnabled(false);
        secoes.setAdapter(new Adapter_Main(this, listaDeSecoes));
    }

    public void home(View v) { startActivity(new Intent(this, Activity_Home.class)); }
    public void emAlta(View v) { startActivity(new Intent(this, EmAlta.class)); }
    public void favoritos(View v) { startActivity(new Intent(this, Favoritos.class)); }
}