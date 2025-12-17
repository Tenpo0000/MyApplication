package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class Adapter_Filme extends RecyclerView.Adapter<Adapter_Filme.FilmeAdapterViewHolder> {
    private List <Integer> imagens;

    public Adapter_Filme(List<Integer> imagens) {
        this.imagens = imagens;
    }
    public static class FilmeAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView imagem;
        public FilmeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.Filmes);
        }
    }

    @NonNull
    @Override
    public Adapter_Filme.FilmeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_filmes, parent, false);
        return new FilmeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmeAdapterViewHolder holder, int position) {
        holder.imagem.setImageResource(imagens.get(position));
    }

    @Override
    public int getItemCount() {
        return imagens.size();
    }
}