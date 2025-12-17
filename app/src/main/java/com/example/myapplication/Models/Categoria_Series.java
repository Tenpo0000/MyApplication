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

public class Categoria_Series extends AppCompatActivity {
    RecyclerView secoes;
    ImageView home;
    ImageView emAlta;
    ImageView favoritos;


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

        secoes = findViewById(R.id.recyclerEmAltaCategoria);

        List<Adapter_Secao> listaDeSecoes = Arrays.asList(
                new Adapter_Secao("Séries em Alta", Arrays.asList(
                        R.drawable.stranger_things_serie,
                        R.drawable.dexter_serie,
                        R.drawable.dr_house_serie,
                        R.drawable.greys_anatomy_serie,
                        R.drawable.homem_x_bebe_serie,
                        R.drawable.super_natural_serie
                )),
                new Adapter_Secao("Para Maratonar", Arrays.asList(
                        R.drawable.shameless_serie,
                        R.drawable.shadow_hunter_serie,
                        R.drawable.lista_negra_serie,
                        R.drawable.lei_e_ordem_serie,
                        R.drawable.stranger_things_serie,
                        R.drawable.ncis_investigacao_naval_serie
                )),
                new Adapter_Secao("Clássicos da TV", Arrays.asList(
                        R.drawable.os_simpsons_serie,
                        R.drawable.he_man_and_shera_serie,
                        R.drawable.slugterra_serie,
                        R.drawable.ponyo_filme,
                        R.drawable.avenida_brasil_serie,
                        R.drawable.robin_wood_serie
                )),
                new Adapter_Secao("Ação e Fantasia", Arrays.asList(
                        R.drawable.spartacus_serie,
                        R.drawable.record_of_ragnarok_serie,
                        R.drawable.bob_esponja_filme,
                        R.drawable.meu_amigo_tororo_filme,
                        R.drawable.os_simpsons_serie,
                        R.drawable.homem_x_bebe_serie
                ))
        );

        secoes.setLayoutManager(new LinearLayoutManager(this));
        secoes.setNestedScrollingEnabled(false);
        secoes.setAdapter(new Adapter_Main(this, listaDeSecoes));

        home = findViewById(R.id.btnHome);
        emAlta = findViewById(R.id.btnMaisVistos);
        favoritos = findViewById(R.id.btnFavoritos);
    }
    public void home(View v) {
        Intent intent = new Intent(this, Activity_Home.class);
        startActivity(intent);
    }

    public void emAlta(View v) {
        Intent intent = new Intent(this,EmAlta.class);
        startActivity(intent);
    }

    public void favoritos(View v) {
        Intent intent = new Intent(this,Favoritos.class);
        startActivity(intent);
    }
}