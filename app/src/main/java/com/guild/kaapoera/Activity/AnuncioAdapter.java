package com.guild.kaapoera.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.guild.kaapoera.R;

import java.util.List;

public class AnuncioAdapter extends RecyclerView.Adapter<AnuncioAdapter.AnuncioViewHolder> {
    private Context context;
    private List<Anuncio> anunciosList;

    public AnuncioAdapter(Context context, List<Anuncio> anunciosList) {
        this.context = context;
        this.anunciosList = anunciosList;
    }

    @NonNull
    @Override
    public AnuncioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_anuncio, parent, false);
        return new AnuncioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnuncioViewHolder holder, int position) {
        Anuncio anuncio = anunciosList.get(position);

        // Exibir os dados do anúncio nos elementos do layout
        holder.textViewTitulo.setText(anuncio.getTitulo());
        holder.textViewDescricao.setText(anuncio.getDescricao());
        holder.textViewTipodeVenda.setText(anuncio.getTipoAnuncio());
        holder.textViewPreco.setText(anuncio.getPreco());
        holder.textViewNomeVendedor.setText(anuncio.getNomePersonagem());

        // Carregar a imagem usando Glide com placeholder
        if (anuncio.getImagemUrl() != null && !anuncio.getImagemUrl().isEmpty()) {
            Glide.with(context)
                    .load(anuncio.getImagemUrl())
                    .placeholder(R.drawable.baseline_emergency_24) // Imagem placeholder
                    .into(holder.imageViewItem);
        } else {
            // Caso a URL da imagem não esteja disponível, usar a imagem placeholder
            holder.imageViewItem.setImageResource(R.drawable.baseline_emergency_24);
        }
    }

    @Override
    public int getItemCount() {
        return anunciosList.size();
    }

    public static class AnuncioViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewItem;
        TextView textViewTitulo;
        TextView textViewDescricao;
        TextView textViewTipodeVenda;
        TextView textViewPreco;
        TextView textViewNomeVendedor;

        public AnuncioViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewItem = itemView.findViewById(R.id.imageViewItem);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            textViewDescricao = itemView.findViewById(R.id.textViewDescricao);
            textViewTipodeVenda = itemView.findViewById(R.id.textViewTipodeVenda);
            textViewPreco = itemView.findViewById(R.id.textViewPreco);
            textViewNomeVendedor = itemView.findViewById(R.id.textViewNomeVendedor);
        }
    }
}
