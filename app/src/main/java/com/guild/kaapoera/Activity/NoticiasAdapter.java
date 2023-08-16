package com.guild.kaapoera.Activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.guild.kaapoera.R;

import java.util.List;

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.NoticiasViewHolder> {

    private List<Noticia> listaNoticias;

    public NoticiasAdapter(List<Noticia> listaNoticias) {
        this.listaNoticias = listaNoticias;
    }

    @NonNull
    @Override
    public NoticiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.noticias_adapter, parent, false);
        return new NoticiasViewHolder(itemView);
    }

    public void setListaNoticias(List<Noticia> listaNoticias) {
        this.listaNoticias = listaNoticias;
        notifyDataSetChanged(); // Notificar o adaptador sobre as mudanças
    }

    @Override
    public void onBindViewHolder(@NonNull NoticiasViewHolder holder, int position) {
        Noticia noticiaAtual = listaNoticias.get(position);

        holder.tituloTextView.setText(noticiaAtual.getTitulo());
        holder.dataTextView.setText(noticiaAtual.getData());

        // Define um clique para o item da lista
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetalhesNoticiaActivity.class);
            intent.putExtra("titulo", noticiaAtual.getTitulo());
            intent.putExtra("conteudo", noticiaAtual.getConteudo());
            intent.putExtra("autor", noticiaAtual.getAutor());
            intent.putExtra("data", noticiaAtual.getData());
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }

    public class NoticiasViewHolder extends RecyclerView.ViewHolder {
        public TextView tituloTextView;
        public TextView dataTextView;

        public NoticiasViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.textTitulo);
            dataTextView = itemView.findViewById(R.id.textData);
        }
    }
}
