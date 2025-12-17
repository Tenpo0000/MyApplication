package com.example.myapplication.Models;

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

import com.example.myapplication.Activities.Activity_Home;
import com.example.myapplication.Adapters.Adapter_Main;
import com.example.myapplication.Adapters.Adapter_Secao;
import com.example.myapplication.R;

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

        secoes = findViewById(R.id.recyclerEmAltaCategoria);

        List<Adapter_Secao> listaDeSecoes = Arrays.asList(
                new Adapter_Secao("Em Alta Hoje", Arrays.asList(
                        R.drawable.stranger_things_serie,
                        R.drawable.bob_esponja_filme,
                        R.drawable.o_poderoso_chefao_filme,
                        R.drawable.programa_silvio_santos_tv,
                        R.drawable.ponyo_filme,
                        R.drawable.dexter_serie
                )),
                new Adapter_Secao("Mais Assistidos da Semana", Arrays.asList(
                        R.drawable.maze_runner_filme,
                        R.drawable.o_resgate_do_soldado_ryan_filme,
                        R.drawable.greys_anatomy_serie,
                        R.drawable.shadow_hunter_serie,
                        R.drawable.he_man_and_shera_serie,
                        R.drawable.programa_do_ratinho_tv
                )),
                new Adapter_Secao("O Que Está Bombando", Arrays.asList(
                        R.drawable.it_capitula2_filme,
                        R.drawable.shameless_serie,
                        R.drawable.stranger_things_serie,
                        R.drawable.greys_anatomy_serie,
                        R.drawable.avenida_brasil_serie,
                        R.drawable.the_noite_com_danilo_gentili_tv
                ))
        );

        secoes.setLayoutManager(new LinearLayoutManager(this));
        secoes.setNestedScrollingEnabled(false);
        secoes.setAdapter(new Adapter_Main(this, listaDeSecoes));

        home = findViewById(R.id.btnHome);
        favoritos = findViewById(R.id.btnFavoritos);
    }

    public void home(View v) {
        Intent intent = new Intent(this, Activity_Home.class);
        startActivity(intent);
    }

    public void favoritos(View v) {
        Intent intent = new Intent(this,Favoritos.class);
        startActivity(intent);
    }
}