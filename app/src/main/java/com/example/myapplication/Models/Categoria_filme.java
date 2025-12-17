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

public class Categoria_filme extends AppCompatActivity {

    RecyclerView secoes;
    ImageView home;
    ImageView emAlta;
    ImageView favoritos;


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

        secoes = findViewById(R.id.recyclerEmAltaCategoria);

        List<Adapter_Secao> listaDeSecoes = Arrays.asList(
                new Adapter_Secao("Lançamentos", Arrays.asList(
                        R.drawable.a_felicidade_nao_se_compra_filme,
                        R.drawable.antes_da_lua_cheia_filme,
                        R.drawable.a_hora_do_pesadelo_filme,
                        R.drawable.billy_the_kid_filme,
                        R.drawable.ilha_do_medo_filme,
                        R.drawable.it_capitula2_filme
                )),
                new Adapter_Secao("Clássicos Imperdíveis", Arrays.asList(
                        R.drawable.o_poderoso_chefao_filme,
                        R.drawable.tres_homens_em_conflito_filme,
                        R.drawable.tempo_de_violencia_filme,
                        R.drawable.o_resgate_do_soldado_ryan_filme,
                        R.drawable.bob_esponja_filme,
                        R.drawable.o_show_da_vida_filme
                )),
                new Adapter_Secao("Para Assistir Hoje", Arrays.asList(
                        R.drawable.maze_runner_filme,
                        R.drawable.prenda_me_se_for_capaz_filme,
                        R.drawable.lolita_filme,
                        R.drawable.a_felicidade_nao_se_compra_filme,
                        R.drawable.um_conto_de_natal_filme,
                        R.drawable.uma_semana_a_tres_filme
                )),
                new Adapter_Secao("Para Toda a Família", Arrays.asList(
                        R.drawable.meu_amigo_tororo_filme,
                        R.drawable.ponyo_filme,
                        R.drawable.arqueiro_serie,
                        R.drawable.he_man_and_shera_serie,
                        R.drawable.o_novato_serie,
                        R.drawable.bob_esponja_filme
                ))
        );

        secoes.setLayoutManager(new LinearLayoutManager(this));
        secoes.setNestedScrollingEnabled(false);
        secoes.setAdapter(new Adapter_Main(this, listaDeSecoes));

        home = findViewById(R.id.btnHome);
        emAlta = findViewById(R.id.btnMaisVistos);
        favoritos = findViewById(R.id.btnFavoritos);
        secoes = findViewById(R.id.recyclerEmAltaCategoria);
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