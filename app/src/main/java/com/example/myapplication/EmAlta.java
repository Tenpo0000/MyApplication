package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class EmAlta extends AppCompatActivity {
    RecyclerView secoes;
    ImageView home;
    ImageView favoritos;


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

        secoes = findViewById(R.id.recyclerFilmesCategoria);
        List<Adapter_Secao> listaDeSecoes = Arrays.asList(
                new Adapter_Secao("Filmes Novos", Arrays.asList(R.drawable.bob_esponja_filme,R.drawable.bob_esponja_filme,R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme)),
                new Adapter_Secao("Filmes Novos", Arrays.asList(R.drawable.bob_esponja_filme,R.drawable.bob_esponja_filme,R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme)),
                new Adapter_Secao("Filmes Novos", Arrays.asList(R.drawable.bob_esponja_filme,R.drawable.bob_esponja_filme,R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme)),
                new Adapter_Secao("Filmes Novos", Arrays.asList(R.drawable.bob_esponja_filme,R.drawable.bob_esponja_filme,R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme)),
                new Adapter_Secao("Recomendados", Arrays.asList(R.drawable.bob_esponja_filme,R.drawable.bob_esponja_filme,R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme)),
                new Adapter_Secao("Mais Vistos", Arrays.asList(R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme,R.drawable.bob_esponja_filme,R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme))
        );

        secoes.setLayoutManager(new LinearLayoutManager(this));
        secoes.setNestedScrollingEnabled(false);
        Adapter_Main adapterMain = new Adapter_Main(this, listaDeSecoes);
        secoes.setAdapter(adapterMain);

        home = findViewById(R.id.btnHome);
        favoritos = findViewById(R.id.btnFavoritos);
    }
    public void home(View v) {
        Intent intent = new Intent(this,Activity_Home.class);
        startActivity(intent);
    }

    public void favoritos(View v) {
        Intent intent = new Intent(this,Favoritos.class);
        startActivity(intent);
    }
}