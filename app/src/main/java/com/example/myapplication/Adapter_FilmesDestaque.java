package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_FilmesDestaque extends RecyclerView.Adapter<Adapter_FilmesDestaque.FilmeDestaqueViewHolder> {

    private List<Integer> imagens;

    public Adapter_FilmesDestaque(List<Integer> imagens) {
        this.imagens = imagens;
    }

    public static class FilmeDestaqueViewHolder extends RecyclerView.ViewHolder {
        ImageView imagem;
        public FilmeDestaqueViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.Destaque);
        }
    }

    @NonNull
    @Override
    public FilmeDestaqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_filmes_destaque, parent, false);
        return new FilmeDestaqueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmeDestaqueViewHolder holder, int position) {
        holder.imagem.setImageResource(imagens.get(position));
    }

    @Override
    public int getItemCount() {
        return imagens.size();
    }
}
