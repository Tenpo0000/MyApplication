package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class Activity_Home extends AppCompatActivity {

    RecyclerView recyclerDestaque;
    RecyclerView recyclerSecoes;

    // Configurações do Auto-Scroll (Carrossel)
    PagerSnapHelper snapHelper = new PagerSnapHelper();
    private Handler sliderHandler = new Handler(Looper.getMainLooper());
    private Runnable sliderRunnable;
    private int velocidadeScroll = 4000;

    // Configurações de Sessão
    private static final long TEMPO_LIMITE = 60 * 1000;

    @Override
    protected void onStart() {
        super.onStart();
        verificarSeAindaEstaLogado();
    }

    @Override
    protected void onStop() {
        super.onStop();
        salvarHoraDeSaida();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reinicia o scroll automático apenas se o runnable foi criado
        if (sliderRunnable != null) {
            sliderHandler.postDelayed(sliderRunnable, velocidadeScroll);
        }
    }

    private void salvarHoraDeSaida() {
        SharedPreferences prefs = getSharedPreferences("users", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("ultimo_acesso", System.currentTimeMillis());
        editor.apply();
    }

    private void verificarSeAindaEstaLogado() {
        SharedPreferences prefs = getSharedPreferences("users", MODE_PRIVATE);
        boolean estaLogado = prefs.getBoolean("estaLogado", false);
        long ultimoAcesso = prefs.getLong("ultimo_acesso", 0);
        long agora = System.currentTimeMillis();

        if (estaLogado) {
            if ((agora - ultimoAcesso) > TEMPO_LIMITE) {
                prefs.edit().putBoolean("estaLogado", false).apply();
                Toast.makeText(this, "Tempo expirado! Faça login novamente.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, Activity_Login.class); // Verifique se é LoginActivity ou Activity_Login
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // VINCULANDO OS IDs DO XML
        recyclerDestaque = findViewById(R.id.recyclerDestaque);
        recyclerSecoes = findViewById(R.id.recyclerSecoes);

        // CONFIGURANDO CARROSSEL DE DESTAQUES
        List<Integer> imagensDestaque = Arrays.asList(
                R.drawable.bob_esponja_filme,
                R.drawable.bob_esponja_filme,
                R.drawable.bob_esponja_filme
        );

        recyclerDestaque.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        snapHelper.attachToRecyclerView(recyclerDestaque);

        Adapter_FilmesDestaque adapterDestaque = new Adapter_FilmesDestaque(imagensDestaque);
        recyclerDestaque.setAdapter(adapterDestaque);

        // CONFIGURANDO AS SEÇÕES (LISTAS DE BAIXO)
        List<Adapter_Secao> listaDeSecoes = Arrays.asList(
                new Adapter_Secao("Filmes Novos", Arrays.asList(R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme)),
                new Adapter_Secao("Recomendados", Arrays.asList(R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme)),
                new Adapter_Secao("Mais Vistos", Arrays.asList(R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme))
        );

        recyclerSecoes.setLayoutManager(new LinearLayoutManager(this));
        recyclerSecoes.setNestedScrollingEnabled(false);
        Adapter_Main adapterMain = new Adapter_Main(this, listaDeSecoes);
        recyclerSecoes.setAdapter(adapterMain);

        iniciarAutoScroll();
    }

    // --- MÉTODOS DE CLIQUE (ONCLICK DO XML) ---

    public void categoria_filme(View v) {
        Intent intent = new Intent(this, Categoria_filme.class);
        startActivity(intent);
    }

    public void categoria_Tv(View v) {
        Intent intent = new Intent(this, Categoria_TV.class); // Certifique-se que esta classe existe
        startActivity(intent);
    }

    public void categoria_Serie(View v) {
        Intent intent = new Intent(this, Categoria_Series.class); // Certifique-se que esta classe existe
        startActivity(intent);
    }

    public void favoritos(View v) {
        Intent intent = new Intent(this, Favoritos.class); // Certifique-se que esta classe existe
        startActivity(intent);
    }

    public void emAlta(View v) {
        Intent intent = new Intent(this, EmAlta.class); // Certifique-se que esta classe existe
        startActivity(intent);
    }

    // --- LÓGICA DO AUTO SCROLL ---

    private void iniciarAutoScroll() {
        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                if (recyclerDestaque == null || recyclerDestaque.getLayoutManager() == null) return;

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerDestaque.getLayoutManager();
                int posAtual = layoutManager.findFirstVisibleItemPosition();
                int posProxima = posAtual + 1;

                if (recyclerDestaque.getAdapter() != null) {
                    if (posProxima >= recyclerDestaque.getAdapter().getItemCount()) {
                        posProxima = 0;
                    }

                    LinearSmoothScroller smoothScroller = new LinearSmoothScroller(Activity_Home.this) {
                        @Override
                        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                            return 100f / displayMetrics.densityDpi;
                        }
                    };

                    smoothScroller.setTargetPosition(posProxima);
                    layoutManager.startSmoothScroll(smoothScroller);
                }

                sliderHandler.postDelayed(this, velocidadeScroll);
            }
        };
        sliderHandler.postDelayed(sliderRunnable, velocidadeScroll);
    }
}