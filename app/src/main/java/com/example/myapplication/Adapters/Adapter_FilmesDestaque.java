package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView; // Importante importar o TextView

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.Filme; // Importante importar seu Model
import com.example.myapplication.R;

import java.util.List;

public class Adapter_FilmesDestaque extends RecyclerView.Adapter<Adapter_FilmesDestaque.FilmeDestaqueViewHolder> {

    // MUDANÇA 1: Agora a lista é de objetos 'Filme', não mais apenas Integers
    private List<Filme> listaFilmes;

    // Construtor atualizado para receber a lista de Filmes
    public Adapter_FilmesDestaque(List<Filme> listaFilmes) {
        this.listaFilmes = listaFilmes;
    }

    public static class FilmeDestaqueViewHolder extends RecyclerView.ViewHolder {
        ImageView imagem;
        TextView descricao; // MUDANÇA 2: Adicionar o TextView

        public FilmeDestaqueViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.Destaque);
            // Vincula com o ID que colocamos no XML
            descricao = itemView.findViewById(R.id.descricao);
        }
    }

    @NonNull
    @Override
    public FilmeDestaqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Certifique-se de que o nome do layout XML está correto (ex: item_destaque.xml ou lista_filmes_destaque.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_filmes_destaque, parent, false);
        return new FilmeDestaqueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmeDestaqueViewHolder holder, int position) {
        // MUDANÇA 3: Pegamos o objeto filme atual
        Filme filme = listaFilmes.get(position);

        // Define a imagem
        holder.imagem.setImageResource(filme.getImagemResId());

        // Define o texto (descrição)
        holder.descricao.setText(filme.getDescricao());
    }

    @Override
    public int getItemCount() {
        return listaFilmes.size();
    }
}