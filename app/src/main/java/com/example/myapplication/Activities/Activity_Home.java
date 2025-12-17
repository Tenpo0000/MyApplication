package com.example.myapplication.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

import com.example.myapplication.Adapters.Adapter_Buscar;
import com.example.myapplication.Adapters.Adapter_FilmesDestaque;
import com.example.myapplication.Adapters.Adapter_Main;
import com.example.myapplication.Adapters.Adapter_Secao;
import com.example.myapplication.Models.Categoria_Series;
import com.example.myapplication.Models.Categoria_TV;
import com.example.myapplication.Models.Categoria_filme;
import com.example.myapplication.Models.EmAlta;
import com.example.myapplication.Models.Favoritos;
import com.example.myapplication.Models.Filme;
import com.example.myapplication.R;
import com.example.myapplication.Util.Espacamento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity_Home extends AppCompatActivity {

    RecyclerView recyclerDestaque, recyclerSecoes, recyclerPesquisa;
    LinearLayout layoutIndicadores;
    EditText edtBarraPesquisa;

    List<Filme> filmes = new ArrayList<>();
    List<Filme> series = new ArrayList<>();
    List<Filme> programasTV = new ArrayList<>();
    List<Filme> todosOsFilmes = new ArrayList<>();

    Adapter_Buscar adapterBusca;

    PagerSnapHelper snapHelper = new PagerSnapHelper();
    Handler sliderHandler = new Handler(Looper.getMainLooper());
    Runnable sliderRunnable;
    int velocidadeScroll = 3000;

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
        criarBancoDeDadosFilmes();
        criarBancoDeDadosSeries();
        criarBancoDeDadosProgramasTV();
        juntarTodosOsFilmes();
        configurarAdaptersOriginais();
        configurarPesquisa();
        iniciarAutoScroll();
        mostrarAviso();
    }

    private void inicializarComponentes() {
        recyclerDestaque = findViewById(R.id.recyclerDestaque);
        recyclerSecoes = findViewById(R.id.recyclerSecoes);
        recyclerPesquisa = findViewById(R.id.recyclerPesquisa);
        layoutIndicadores = findViewById(R.id.ll_indicadores);
        edtBarraPesquisa = findViewById(R.id.edtBarraPesquisa);
    }

    private void criarBancoDeDadosFilmes() {
        filmes.add(new Filme("A Felicidade Nao Se Compra", R.drawable.a_felicidade_nao_se_compra_filme));
        filmes.add(new Filme("Antes Da Lua Cheia", R.drawable.antes_da_lua_cheia_filme));
        filmes.add(new Filme("A Hora Do Pesadelo", R.drawable.a_hora_do_pesadelo_filme));
        filmes.add(new Filme("Billy The Kid", R.drawable.billy_the_kid_filme));
        filmes.add(new Filme("Bob Esponja", R.drawable.bob_esponja_filme));
        filmes.add(new Filme("Ilha Do Medo", R.drawable.ilha_do_medo_filme));
        filmes.add(new Filme("It Capitulo 2", R.drawable.it_capitula2_filme));
        filmes.add(new Filme("Lolita", R.drawable.lolita_filme));
        filmes.add(new Filme("Maze Runner", R.drawable.maze_runner_filme));
        filmes.add(new Filme("Meu Amigo Totoro", R.drawable.meu_amigo_tororo_filme));
        filmes.add(new Filme("O Poderoso Chefao", R.drawable.o_poderoso_chefao_filme));
        filmes.add(new Filme("O Resgate Do Soldado Ryan", R.drawable.o_resgate_do_soldado_ryan_filme));
        filmes.add(new Filme("O Show Da Vida", R.drawable.o_show_da_vida_filme));
        filmes.add(new Filme("Ponyo", R.drawable.ponyo_filme));
        filmes.add(new Filme("Prenda Me Se For Capaz", R.drawable.prenda_me_se_for_capaz_filme));
        filmes.add(new Filme("Tempo De Violencia", R.drawable.tempo_de_violencia_filme));
        filmes.add(new Filme("Tres Homens Em Conflito", R.drawable.tres_homens_em_conflito_filme));
        filmes.add(new Filme("Um Conto De Natal", R.drawable.um_conto_de_natal_filme));
        filmes.add(new Filme("Uma Semana A Tres", R.drawable.uma_semana_a_tres_filme));
        filmes.add(new Filme("Bob Esponja Esponja Fora D Agua", R.drawable.bob_esponja_filme));
    }

    private void criarBancoDeDadosSeries() {
        series.add(new Filme("Arqueiro", R.drawable.arqueiro_serie));
        series.add(new Filme("Avenida Brasil", R.drawable.avenida_brasil_serie));
        series.add(new Filme("Dexter", R.drawable.dexter_serie));
        series.add(new Filme("Dr House", R.drawable.dr_house_serie));
        series.add(new Filme("Greys Anatomy", R.drawable.greys_anatomy_serie));
        series.add(new Filme("He Man And Shera", R.drawable.he_man_and_shera_serie));
        series.add(new Filme("Homem X Bebe", R.drawable.homem_x_bebe_serie));
        series.add(new Filme("Lei E Ordem", R.drawable.lei_e_ordem_serie));
        series.add(new Filme("Lista Negra", R.drawable.lista_negra_serie));
        series.add(new Filme("Midsomer Murders", R.drawable.midsomer_murders_serie));
        series.add(new Filme("Ncis Investigacao Naval", R.drawable.ncis_investigacao_naval_serie));
        series.add(new Filme("O Novato", R.drawable.o_novato_serie));
        series.add(new Filme("Os Simpsons", R.drawable.os_simpsons_serie));
        series.add(new Filme("Pluribus", R.drawable.pluribus_serie));
        series.add(new Filme("Queer As Folk", R.drawable.queer_as_folk_serie));
        series.add(new Filme("Record Of Ragnarok", R.drawable.record_of_ragnarok_serie));
        series.add(new Filme("Robin Wood", R.drawable.robin_wood_serie));
        series.add(new Filme("Shadow Hunter", R.drawable.shadow_hunter_serie));
        series.add(new Filme("Shameless", R.drawable.shameless_serie));
        series.add(new Filme("Slugterra", R.drawable.slugterra_serie));
        series.add(new Filme("Spartacus", R.drawable.spartacus_serie));
        series.add(new Filme("Stranger Things", R.drawable.stranger_things_serie));
        series.add(new Filme("Super Natural", R.drawable.super_natural_serie));
    }

    private void criarBancoDeDadosProgramasTV() {
        programasTV.add(new Filme("Caldeirao Do Huck", R.drawable.caldeirao_do_huck_tv));
        programasTV.add(new Filme("Casos De Familia", R.drawable.casos_de_familia_tv));
        programasTV.add(new Filme("Fofocalizando", R.drawable.fofocalizando_tv));
        programasTV.add(new Filme("Programa Do Ratinho", R.drawable.programa_do_ratinho_tv));
        programasTV.add(new Filme("Programa Silvio Santos", R.drawable.programa_silvio_santos_tv));
        programasTV.add(new Filme("The Noite Com Danilo Gentili", R.drawable.the_noite_com_danilo_gentili_tv));
    }

    private void juntarTodosOsFilmes() {
        todosOsFilmes.clear();
        todosOsFilmes.addAll(filmes);
        todosOsFilmes.addAll(series);
        todosOsFilmes.addAll(programasTV);
    }

    private void configurarPesquisa() {
        adapterBusca = new Adapter_Buscar(todosOsFilmes);
        recyclerPesquisa.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerPesquisa.addItemDecoration(
                new Espacamento(3, 24, true)
        );

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
        // --- ALTERAÇÃO AQUI: Agora criamos uma lista de FILMES com DESCRIÇÃO ---
        List<Filme> filmesDestaque = new ArrayList<>();

        filmesDestaque.add(new Filme("Stranger Things", R.drawable.stranger_things_serie,
                "Quando um garoto desaparece, a cidade toda participa nas buscas. Mas o que encontram são segredos, forças sobrenaturais e uma menina."));

        filmesDestaque.add(new Filme("O Resgate do Soldado Ryan", R.drawable.o_resgate_do_soldado_ryan_filme,
                "O capitão Miller recebe a missão de comandar um grupo de soldados atrás das linhas inimigas para resgatar um paraquedista."));

        filmesDestaque.add(new Filme("Um Conto de Natal", R.drawable.um_conto_de_natal_filme,
                "O miserável Ebenezer Scrooge recebe a visita de três fantasmas na véspera de Natal."));

        filmesDestaque.add(new Filme("Lei e Ordem", R.drawable.lei_e_ordem_serie,
                "Investigações complexas e processos judiciais intensos nas ruas de Nova York."));

        filmesDestaque.add(new Filme("Dexter", R.drawable.dexter_serie,
                "Um especialista forense em manchas de sangue vive uma vida dupla como um serial killer de criminosos."));

        filmesDestaque.add(new Filme("Greys Anatomy", R.drawable.greys_anatomy_serie,
                "Acompanhe a vida pessoal e profissional de estagiários de cirurgia e seus supervisores."));

        filmesDestaque.add(new Filme("Supernatural", R.drawable.super_natural_serie,
                "Dois irmãos seguem os passos do pai, caçando demônios, fantasmas e monstros por todo o país."));

        // Configuração do RecyclerView de Destaque
        recyclerDestaque.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        snapHelper.attachToRecyclerView(recyclerDestaque);

        // --- ALTERAÇÃO AQUI: Passamos a lista de FILMES para o Adapter ---
        recyclerDestaque.setAdapter(new Adapter_FilmesDestaque(filmesDestaque));

        // Setup dos indicadores usando o tamanho da nova lista
        setupIndicadores(filmesDestaque.size());
        marcarIndicadorAtual(0);

        recyclerDestaque.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                atualizarIndicador();
            }
        });

        // Configuração das Seções (Mantida igual)
        List<Adapter_Secao> listaDeSecoes = Arrays.asList(
                new Adapter_Secao("Filmes", Arrays.asList(
                        R.drawable.a_felicidade_nao_se_compra_filme,
                        R.drawable.antes_da_lua_cheia_filme,
                        R.drawable.a_hora_do_pesadelo_filme,
                        R.drawable.billy_the_kid_filme,
                        R.drawable.ilha_do_medo_filme,
                        R.drawable.it_capitula2_filme,
                        R.drawable.o_poderoso_chefao_filme
                )),
                new Adapter_Secao("Séries", Arrays.asList(
                        R.drawable.dexter_serie,
                        R.drawable.dr_house_serie,
                        R.drawable.greys_anatomy_serie,
                        R.drawable.lei_e_ordem_serie,
                        R.drawable.stranger_things_serie,
                        R.drawable.super_natural_serie,
                        R.drawable.spartacus_serie
                )),
                new Adapter_Secao("TV", Arrays.asList(
                        R.drawable.caldeirao_do_huck_tv,
                        R.drawable.casos_de_familia_tv,
                        R.drawable.fofocalizando_tv,
                        R.drawable.programa_do_ratinho_tv,
                        R.drawable.programa_silvio_santos_tv,
                        R.drawable.the_noite_com_danilo_gentili_tv,
                        R.drawable.programa_do_ratinho_tv
                )),
                new Adapter_Secao("Mais Vistos", Arrays.asList(
                        R.drawable.o_resgate_do_soldado_ryan_filme,
                        R.drawable.maze_runner_filme,
                        R.drawable.meu_amigo_tororo_filme,
                        R.drawable.os_simpsons_serie,
                        R.drawable.stranger_things_serie,
                        R.drawable.programa_silvio_santos_tv,
                        R.drawable.bob_esponja_filme
                )),
                new Adapter_Secao("Para Você", Arrays.asList(
                        R.drawable.ilha_do_medo_filme,
                        R.drawable.ponyo_filme,
                        R.drawable.prenda_me_se_for_capaz_filme,
                        R.drawable.dexter_serie,
                        R.drawable.shadow_hunter_serie,
                        R.drawable.the_noite_com_danilo_gentili_tv,
                        R.drawable.bob_esponja_filme
                )),
                new Adapter_Secao("Para Assistir Com a Família", Arrays.asList(
                        R.drawable.meu_amigo_tororo_filme,
                        R.drawable.ponyo_filme,
                        R.drawable.um_conto_de_natal_filme,
                        R.drawable.os_simpsons_serie,
                        R.drawable.slugterra_serie,
                        R.drawable.caldeirao_do_huck_tv,
                        R.drawable.programa_silvio_santos_tv
                ))
        );

        recyclerSecoes.setLayoutManager(new LinearLayoutManager(this));
        recyclerSecoes.setNestedScrollingEnabled(false);
        recyclerSecoes.setAdapter(new Adapter_Main(this, listaDeSecoes));
    }

    private void atualizarIndicador() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerDestaque.getLayoutManager();
        if (layoutManager != null) {
            View view = snapHelper.findSnapView(layoutManager);
            if (view != null) {
                marcarIndicadorAtual(layoutManager.getPosition(view));
            }
        }
    }

    private void setupIndicadores(int quantidade) {
        layoutIndicadores.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 0, 8, 0);

        for (int i = 0; i < quantidade; i++) {
            ImageView img = new ImageView(this);
            img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.indicador_inativo));
            img.setLayoutParams(params);
            layoutIndicadores.addView(img);
        }
    }

    private void marcarIndicadorAtual(int index) {
        for (int i = 0; i < layoutIndicadores.getChildCount(); i++) {
            ImageView img = (ImageView) layoutIndicadores.getChildAt(i);
            img.setImageDrawable(ContextCompat.getDrawable(
                    this,
                    i == index ? R.drawable.indicador_ativo : R.drawable.indicador_inativo
            ));
        }
    }

    private void iniciarAutoScroll() {
        sliderRunnable = () -> {
            // Verificações de segurança para não travar o app
            if (recyclerDestaque == null || recyclerDestaque.getLayoutManager() == null || recyclerDestaque.getAdapter() == null) {
                return;
            }

            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerDestaque.getLayoutManager();
            int totalItens = recyclerDestaque.getAdapter().getItemCount();

            // 1. Descobre onde estamos agora usando o SnapHelper (mais preciso)
            View viewCentralizada = snapHelper.findSnapView(layoutManager);
            int posicaoAtual = 0;
            if (viewCentralizada != null) {
                posicaoAtual = layoutManager.getPosition(viewCentralizada);
            }

            int proximo = posicaoAtual + 1;

            // 2. Se chegou no fim, define o alvo como 0 (o começo)
            if (proximo >= totalItens) {
                proximo = 0;
            }

            // 3. Cria o Scroller LENTO
            LinearSmoothScroller scroller = new LinearSmoothScroller(this) {
                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    // Mude este valor para controlar a velocidade:
                    // 25f = Padrão (Rápido)
                    // 100f = Bem Lento (Ideal para o que você quer)
                    // 150f = Muito Lento
                    return 100f / displayMetrics.densityDpi;
                }
            };

            // 4. Manda ir para a próxima posição (seja ela a próxima ou a 0) COM VELOCIDADE CONTROLADA
            scroller.setTargetPosition(proximo);
            layoutManager.startSmoothScroll(scroller);

            // 5. Agenda a próxima rolagem
            sliderHandler.postDelayed(sliderRunnable, velocidadeScroll);
        };

        sliderHandler.postDelayed(sliderRunnable, velocidadeScroll);
    }



    public void categoria_filme(View v) { startActivity(new Intent(this, Categoria_filme.class)); }

    public void categoria_Tv(View v) { startActivity(new Intent(this, Categoria_TV.class)); }

    public void categoria_Serie(View v) { startActivity(new Intent(this, Categoria_Series.class)); }

    public void favoritos(View v) { startActivity(new Intent(this, Favoritos.class)); }

    public void emAlta(View v) { startActivity(new Intent(this, EmAlta.class)); }



    @Override

    protected void onPause() {

        super.onPause();

        sliderHandler.removeCallbacks(sliderRunnable);

    }



    @Override

    protected void onResume() {

        super.onResume();

        sliderHandler.postDelayed(sliderRunnable, velocidadeScroll);

    }

    private void mostrarAviso() {
        // 1. Cria o Dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 2. Define o layout que criamos
        dialog.setContentView(R.layout.dialog_aviso);

        // 3. Deixa o fundo transparente para ver as bordas arredondadas do CardView
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 4. Configura o botão de fechar
        ImageView btnFechar = dialog.findViewById(R.id.btnFechar);
        btnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Fecha o aviso
            }
        });

        dialog.show();
    }
}

