package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Main_Adapter extends RecyclerView.Adapter<Main_Adapter.MainViewHolder> {

    // 1. Variável de dados (Se for lista de categorias, usaria um objeto Categoria)
    private List<Integer> RecyclerPrincipal;

    // 2. Construtor Corrigido
    public Main_Adapter(List<Integer> RecyclerPrincipal) {
        this.RecyclerPrincipal = RecyclerPrincipal;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 3. Infla o layout da LINHA HORIZONTAL
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.liinha_horizontal, parent, false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        // 4. LÓGICA DE BINDING
        // Aqui é onde você configuraria o RecyclerView horizontal dentro do item vertical

        // Exemplo: holder.configurarRecyclerViewHorizontal(RecyclerPrincipal.get(position).getListaDeFilmes());

    }

    @Override
    public int getItemCount() {
        // 5. Retorna o tamanho da lista de dados
        return RecyclerPrincipal.size();
    }

    // 6. CLASSE VIEWHOLDER CORRIGIDA
    public static class MainViewHolder extends RecyclerView.ViewHolder {
        // Aqui você declararia os componentes visuais do liinha_horizontal.xml
        // Ex: RecyclerView recyclerHorizontal, TextView titulo;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ex: titulo = itemView.findViewById(R.id.titulo_categoria);
            // Ex: recyclerHorizontal = itemView.findViewById(R.id.recyclerHorizontal);
        }

        // Você também poderia criar um metodo aqui para configurar o Adapter Horizontal
    }
}