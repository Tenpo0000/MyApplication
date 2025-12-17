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

public class Categoria_TV extends AppCompatActivity {
    RecyclerView secoes;
    ImageView home;
    ImageView emAlta;
    ImageView favoritos;


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

        secoes = findViewById(R.id.recyclerEmAltaCategoria);

        List<Adapter_Secao> listaDeSecoes = Arrays.asList(
                new Adapter_Secao("Programas Populares", Arrays.asList(
                        R.drawable.programa_silvio_santos_tv,
                        R.drawable.programa_do_ratinho_tv,
                        R.drawable.the_noite_com_danilo_gentili_tv,
                        R.drawable.caldeirao_do_huck_tv
                )),
                new Adapter_Secao("Talk Shows", Arrays.asList(
                        R.drawable.the_noite_com_danilo_gentili_tv
                )),
                new Adapter_Secao("Entretenimento", Arrays.asList(
                        R.drawable.fofocalizando_tv,
                        R.drawable.casos_de_familia_tv,
                        R.drawable.programa_do_ratinho_tv
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
