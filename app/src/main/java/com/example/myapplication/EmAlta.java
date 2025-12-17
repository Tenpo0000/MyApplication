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

public class EmAlta extends AppCompatActivity {

    RecyclerView recyclerSecoes;
    List<Adapter_Secao> listaDeSecoes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_em_alta);

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
        listaFilmes.add(new Filme("Filme 1", R.drawable.bob_esponja_filme));
        listaFilmes.add(new Filme("Filme 2", R.drawable.bob_esponja_filme));
        listaFilmes.add(new Filme("Filme 3", R.drawable.bob_esponja_filme));
        listaFilmes.add(new Filme("Filme 4", R.drawable.bob_esponja_filme));
        listaFilmes.add(new Filme("Filme 5", R.drawable.bob_esponja_filme));

        listaDeSecoes.add(new Adapter_Secao("Top 10 Hoje", listaFilmes));
        listaDeSecoes.add(new Adapter_Secao("Tendências da Semana", listaFilmes));
        listaDeSecoes.add(new Adapter_Secao("Lançamentos Quentes", listaFilmes));
        listaDeSecoes.add(new Adapter_Secao("Aclamados pela Crítica", listaFilmes));
    }

    private void configurarRecycler() {
        recyclerSecoes.setLayoutManager(new LinearLayoutManager(this));
        recyclerSecoes.setNestedScrollingEnabled(false);
        recyclerSecoes.setAdapter(new Adapter_Main(this, listaDeSecoes));
    }

    public void home(View v) { startActivity(new Intent(this, Activity_Home.class)); }
    public void favoritos(View v) { startActivity(new Intent(this, Favoritos.class)); }
}