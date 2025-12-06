package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FilmeDestaque_Adapter extends RecyclerView.Adapter<FilmeDestaque_Adapter.ViewHolder> {

    private List<Integer> imagens;

    public FilmeDestaque_Adapter(List<Integer> imagens) {
        this.imagens = imagens;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_filmes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imagem.setImageResource(imagens.get(position));
    }

    @Override
    public int getItemCount() {
        return imagens.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.Principal);
        }
    }
}
