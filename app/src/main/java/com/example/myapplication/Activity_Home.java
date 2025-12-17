package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity_Home extends AppCompatActivity {

    RecyclerView recyclerDestaque, recyclerSecoes, recyclerPesquisa;
    LinearLayout layoutIndicadores;
    EditText edtBarraPesquisa;
    List<Filme> todosOsFilmes = new ArrayList<>();
    Adapter_Buscar adapterBusca;

    PagerSnapHelper snapHelper = new PagerSnapHelper();
    private Handler sliderHandler = new Handler(Looper.getMainLooper());
    private Runnable sliderRunnable;
    private int velocidadeScroll = 3000;
    private static final long TEMPO_LIMITE = 60 * 1000;

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

        inicializarComponentes();
        criarBancoDeDadosDeFilmes();
        configurarAdaptersOriginais();
        configurarPesquisa();
        iniciarAutoScroll();
    }

    private void inicializarComponentes() {
        recyclerDestaque = findViewById(R.id.recyclerDestaque);
        recyclerSecoes = findViewById(R.id.recyclerSecoes);
        recyclerPesquisa = findViewById(R.id.recyclerPesquisa);
        layoutIndicadores = findViewById(R.id.ll_indicadores);
        edtBarraPesquisa = findViewById(R.id.edtBarraPesquisa);
    }

    private void criarBancoDeDadosDeFilmes() {
        todosOsFilmes.add(new Filme("Bob Esponja: O Filme", R.drawable.bob_esponja_filme));
        todosOsFilmes.add(new Filme("Bob Esponja: Esponja Fora D'água", R.drawable.bob_esponja_filme));
        todosOsFilmes.add(new Filme("Patrick Estrela Show", R.drawable.bob_esponja_filme));
        todosOsFilmes.add(new Filme("Fenda do Biquíni", R.drawable.bob_esponja_filme));
        todosOsFilmes.add(new Filme("Filme Novo", R.drawable.bob_esponja_filme));
    }

    private void configurarPesquisa() {
        adapterBusca = new Adapter_Buscar(todosOsFilmes);
        recyclerPesquisa.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerPesquisa.setAdapter(adapterBusca);

        edtBarraPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarFilmes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filtrarFilmes(String texto) {
        if (texto.isEmpty()) {

            recyclerPesquisa.setVisibility(View.GONE);
            recyclerDestaque.setVisibility(View.VISIBLE);
            recyclerSecoes.setVisibility(View.VISIBLE);
            layoutIndicadores.setVisibility(View.VISIBLE);
        } else {
            recyclerPesquisa.setVisibility(View.VISIBLE);
            recyclerDestaque.setVisibility(View.GONE);
            recyclerSecoes.setVisibility(View.GONE);
            layoutIndicadores.setVisibility(View.GONE);

            List<Filme> listaFiltrada = new ArrayList<>();
            for (Filme filme : todosOsFilmes) {
                if (filme.getNome().toLowerCase().contains(texto.toLowerCase())) {
                    listaFiltrada.add(filme);
                }
            }
            adapterBusca.setListaFiltrada(listaFiltrada);
        }
    }

    private void configurarAdaptersOriginais() {
        List<Integer> imagensDestaque = Arrays.asList(
                R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme,
                R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme,
                R.drawable.bob_esponja_filme
        );

        recyclerDestaque.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        snapHelper.attachToRecyclerView(recyclerDestaque);
        recyclerDestaque.setAdapter(new Adapter_FilmesDestaque(imagensDestaque));

        setupIndicadores(imagensDestaque.size());
        marcarIndicadorAtual(0);

        recyclerDestaque.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                atualizarIndicador();
            }
        });

        List<Adapter_Secao> listaDeSecoes = Arrays.asList(
                new Adapter_Secao("Filmes Novos", Arrays.asList(R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme)),
                new Adapter_Secao("Recomendados", Arrays.asList(R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme)),
                new Adapter_Secao("Mais Vistos", Arrays.asList(R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme, R.drawable.bob_esponja_filme))
        );

        recyclerSecoes.setLayoutManager(new LinearLayoutManager(this));
        recyclerSecoes.setNestedScrollingEnabled(false);
        recyclerSecoes.setAdapter(new Adapter_Main(this, listaDeSecoes));
    }

    private void atualizarIndicador() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerDestaque.getLayoutManager();
        if (layoutManager != null) {
            View viewCentralizada = snapHelper.findSnapView(layoutManager);
            if (viewCentralizada != null) {
                int posicao = layoutManager.getPosition(viewCentralizada);
                marcarIndicadorAtual(posicao);
            }
        }
    }

    private void setupIndicadores(int quantidade) {
        layoutIndicadores.removeAllViews();
        ImageView[] indicadores = new ImageView[quantidade];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0);

        for (int i = 0; i < indicadores.length; i++) {
            indicadores[i] = new ImageView(getApplicationContext());
            indicadores[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.indicador_inativo));
            indicadores[i].setLayoutParams(layoutParams);
            layoutIndicadores.addView(indicadores[i]);
        }
    }

    private void marcarIndicadorAtual(int index) {
        int childCount = layoutIndicadores.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicadores.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.indicador_ativo));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.indicador_inativo));
            }
        }
    }

    private void iniciarAutoScroll() {
        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                if (recyclerDestaque == null || recyclerDestaque.getLayoutManager() == null) return;
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerDestaque.getLayoutManager();
                int posAtual = layoutManager.findFirstVisibleItemPosition();
                int posProxima = posAtual + 1;

                if (recyclerDestaque.getAdapter() != null) {
                    if (posProxima >= recyclerDestaque.getAdapter().getItemCount()) posProxima = 0;
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

    public void categoria_filme(View v) { startActivity(new Intent(this, Categoria_filme.class)); }
    public void categoria_Tv(View v) { startActivity(new Intent(this, Categoria_TV.class)); }
    public void categoria_Serie(View v) { startActivity(new Intent(this, Categoria_Series.class)); }

    public void favoritos(View v) { startActivity(new Intent(this, Favoritos.class)); }
    public void emAlta(View v) { startActivity(new Intent(this, EmAlta.class)); }

    @Override
    protected void onPause() { super.onPause(); sliderHandler.removeCallbacks(sliderRunnable); }
    @Override
    protected void onResume() { super.onResume(); sliderHandler.postDelayed(sliderRunnable, velocidadeScroll); }
}