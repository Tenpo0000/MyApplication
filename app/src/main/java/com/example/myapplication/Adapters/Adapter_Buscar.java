package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.Filme;
import com.example.myapplication.R;

import java.util.List;

public class Adapter_Buscar extends RecyclerView.Adapter<Adapter_Buscar.BuscaViewHolder> {

    private List<Filme> listaFilmes;

    public Adapter_Buscar(List<Filme> listaFilmes) {
        this.listaFilmes = listaFilmes;
    }

    public void setListaFiltrada(List<Filme> listaFiltrada) {
        this.listaFilmes = listaFiltrada;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BuscaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_filmes, parent, false);
        return new BuscaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuscaViewHolder holder, int position) {
        Filme filme = listaFilmes.get(position);
        holder.imagem.setImageResource(filme.getImagemResId());
    }

    @Override
    public int getItemCount() {
        return listaFilmes.size();
    }

    public static class BuscaViewHolder extends RecyclerView.ViewHolder {
        ImageView imagem;
        public BuscaViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.Filmes);
        }
    }
}