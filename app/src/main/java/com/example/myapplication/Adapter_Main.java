package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_Main extends RecyclerView.Adapter<Adapter_Main.MainAdapterViewHolder> {

    private Context context;
    private List<Adapter_Secao> listaSecoes;

    public Adapter_Main(Context context, List<Adapter_Secao> listaSecoes) {
        this.context = context;
        this.listaSecoes = listaSecoes;
    }

    @NonNull
    @Override
    public Adapter_Main.MainAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_secoes, parent, false);
        return new MainAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Main.MainAdapterViewHolder holder, int position) {
        Adapter_Secao secaoAtual = listaSecoes.get(position);
        holder.txtSessoes.setText(secaoAtual.getTituloSecao());

        holder.recyclerSessoes.setHasFixedSize(true);
        holder.recyclerSessoes.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        Adapter_Filme adapterHorizontal = new Adapter_Filme(context, secaoAtual.getListadeFilmes());
        holder.recyclerSessoes.setAdapter(adapterHorizontal);
    }

    @Override
    public int getItemCount() {
        if (listaSecoes != null){
            return listaSecoes.size();
        }
        return 0;
    }

    public class MainAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView txtSessoes;
        RecyclerView recyclerSessoes;

        public MainAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSessoes = itemView.findViewById(R.id.txtSessoes);
            recyclerSessoes = itemView.findViewById(R.id.recyclerSecao);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}