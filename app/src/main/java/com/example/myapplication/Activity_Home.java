package com.example.myapplication;

import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Activity_Home extends AppCompatActivity {

    RecyclerView recyclerDestaque, recyclerSecoes, recyclerPesquisa;
    LinearLayout layoutIndicadores, llConteudoPrincipal;
    EditText edtBarraPesquisa;

    List<Filme> todosOsFilmes = new ArrayList<>();
    List<Adapter_Secao> listaDeSecoes = new ArrayList<>();
    List<Filme> listaDestaques = new ArrayList<>();

    Adapter_Filme adapterPesquisa;
    PagerSnapHelper snapHelper = new PagerSnapHelper();

    // Handler e Runnable para o AutoScroll
    private Handler sliderHandler = new Handler(Looper.getMainLooper());
    private Runnable sliderRunnable;
    private int velocidadeScroll = 4000; // Aumentei um pouco para não ser muito rápido

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        inicializarComponentes();
        carregarDados();
        configurarHome();
        configurarPesquisa();

        // Só inicia o scroll se houver destaques
        if (!listaDestaques.isEmpty()) {
            iniciarAutoScroll();
        }
    }

    private void inicializarComponentes() {
        recyclerDestaque = findViewById(R.id.recyclerDestaque);
        recyclerSecoes = findViewById(R.id.recyclerSecoes);
        recyclerPesquisa = findViewById(R.id.recyclerPesquisa);
        layoutIndicadores = findViewById(R.id.ll_indicadores);
        edtBarraPesquisa = findViewById(R.id.edtBarraPesquisa);
        // Referência para esconder o conteúdo principal durante a pesquisa
        llConteudoPrincipal = findViewById(R.id.ll_conteudo_principal);
    }

    private void carregarDados() {
        // (Seus dados continuam os mesmos aqui, omiti para economizar espaço)
        List<Filme> listaTerror = new ArrayList<>();
        listaTerror.add(new Filme("It: A Coisa", R.drawable.bob_esponja_filme)); // Exemplo
        listaTerror.add(new Filme("A Freira", R.drawable.bob_esponja_filme));

        List<Filme> listaInfantil = new ArrayList<>();
        listaInfantil.add(new Filme("Barbie", R.drawable.bob_esponja_filme));
        listaInfantil.add(new Filme("Frozen", R.drawable.bob_esponja_filme));

        List<Filme> listaRomance = new ArrayList<>();
        listaRomance.add(new Filme("Titanic", R.drawable.bob_esponja_filme));

        todosOsFilmes.addAll(listaTerror);
        todosOsFilmes.addAll(listaInfantil);
        todosOsFilmes.addAll(listaRomance);

        listaDeSecoes.add(new Adapter_Secao("Terror e Suspense", listaTerror));
        listaDeSecoes.add(new Adapter_Secao("Para as Crianças", listaInfantil));
        listaDeSecoes.add(new Adapter_Secao("Romance", listaRomance));

        listaDestaques = listaInfantil;
    }

    private void configurarHome() {
        // Configurações das Seções
        recyclerSecoes.setLayoutManager(new LinearLayoutManager(this));
        recyclerSecoes.setNestedScrollingEnabled(false);
        recyclerSecoes.setAdapter(new Adapter_Main(this, listaDeSecoes));

        // Configurações do Destaque (Carrossel)
        recyclerDestaque.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerDestaque.setAdapter(new Adapter_Filme(this, listaDestaques));

        // SnapHelper (efeito de travar a imagem no centro)
        recyclerDestaque.setOnFlingListener(null); // Evita erro de "already attached"
        snapHelper.attachToRecyclerView(recyclerDestaque);

        setupIndicadores(listaDestaques.size());
        marcarIndicadorAtual(0);

        recyclerDestaque.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // Opcional: Pausar o auto-scroll quando o usuário toca
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    sliderHandler.removeCallbacks(sliderRunnable);
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    sliderHandler.postDelayed(sliderRunnable, velocidadeScroll);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerDestaque.getLayoutManager();
                if (lm != null) {
                    View view = snapHelper.findSnapView(lm);
                    if (view != null) marcarIndicadorAtual(lm.getPosition(view));
                }
            }
        });
    }

    private void configurarPesquisa() {
        recyclerPesquisa.setLayoutManager(new GridLayoutManager(this, 3));
        adapterPesquisa = new Adapter_Filme(this, new ArrayList<>());
        recyclerPesquisa.setAdapter(adapterPesquisa);

        edtBarraPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrar(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filtrar(String texto) {
        if (texto.isEmpty()) {
            recyclerPesquisa.setVisibility(View.GONE);
            // Mostra o layout principal (Destaques + Seções)
            if(llConteudoPrincipal != null) llConteudoPrincipal.setVisibility(View.VISIBLE);
        } else {
            recyclerPesquisa.setVisibility(View.VISIBLE);
            // Esconde o layout principal para focar na pesquisa
            if(llConteudoPrincipal != null) llConteudoPrincipal.setVisibility(View.GONE);

            List<Filme> filtrados = new ArrayList<>();
            for (Filme f : todosOsFilmes) {
                if (f.getNome().toLowerCase().contains(texto.toLowerCase())) {
                    filtrados.add(f);
                }
            }
            // Atualiza o adapter existente ao invés de criar um novo (melhor performance)
            adapterPesquisa = new Adapter_Filme(this, filtrados);
            recyclerPesquisa.setAdapter(adapterPesquisa);
        }
    }

    private void setupIndicadores(int qtd) {
        layoutIndicadores.removeAllViews();
        if (qtd <= 1) return; // Não precisa de indicadores se tiver apenas 1 item

        ImageView[] imgs = new ImageView[qtd];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 0, 8, 0);

        for (int i = 0; i < qtd; i++) {
            imgs[i] = new ImageView(this);
            imgs[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.indicador_inativo));
            imgs[i].setLayoutParams(params);
            layoutIndicadores.addView(imgs[i]);
        }
    }

    private void marcarIndicadorAtual(int index) {
        if (layoutIndicadores.getChildCount() == 0) return;
        for (int i = 0; i < layoutIndicadores.getChildCount(); i++) {
            ImageView img = (ImageView) layoutIndicadores.getChildAt(i);
            img.setImageDrawable(ContextCompat.getDrawable(this, i == index ? R.drawable.indicador_ativo : R.drawable.indicador_inativo));
        }
    }

    private void iniciarAutoScroll() {
        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                if (recyclerDestaque == null || recyclerDestaque.getLayoutManager() == null) return;

                LinearLayoutManager lm = (LinearLayoutManager) recyclerDestaque.getLayoutManager();
                int totalItemCount = lm.getItemCount();
                if (totalItemCount <= 1) return;

                int currentPosition = lm.findFirstVisibleItemPosition();
                int nextPosition = currentPosition + 1;

                if (nextPosition >= totalItemCount) {
                    nextPosition = 0; // Volta para o início
                }

                recyclerDestaque.smoothScrollToPosition(nextPosition);
                sliderHandler.postDelayed(this, velocidadeScroll);
            }
        };
        sliderHandler.postDelayed(sliderRunnable, velocidadeScroll);
    }

    // Navegação (mantida igual)
    public void categoria_filme(View v) { startActivity(new Intent(this, Categoria_filme.class)); }
    public void categoria_Tv(View v) { startActivity(new Intent(this, Categoria_TV.class)); }
    public void categoria_Serie(View v) { startActivity(new Intent(this, Categoria_Series.class)); }
    public void favoritos(View v) { startActivity(new Intent(this, Favoritos.class)); }
    public void emAlta(View v) { startActivity(new Intent(this, EmAlta.class)); }

    @Override
    protected void onPause() {
        super.onPause();
        // Para o scroll quando o app sai da tela (economiza bateria e evita crash)
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Retoma o scroll
        sliderHandler.postDelayed(sliderRunnable, velocidadeScroll);
    }
}