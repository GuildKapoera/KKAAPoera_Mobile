package com.guild.kaapoera.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.guild.kaapoera.R;

import java.util.List;

public class AnuncioGerenciarAdapter extends RecyclerView.Adapter<AnuncioGerenciarAdapter.AnuncioGerenciarViewHolder> {

    private Context context;
    private List<AnuncioGerenciar> anuncioGerenciarList;
    private OnAnuncioClickListener clickListener;

    public AnuncioGerenciarAdapter(Context context, List<AnuncioGerenciar> anuncioGerenciarList, OnAnuncioClickListener clickListener) {
        this.context = context;
        this.anuncioGerenciarList = anuncioGerenciarList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public AnuncioGerenciarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_anuncio_gerenciar, parent, false);
        return new AnuncioGerenciarViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AnuncioGerenciarViewHolder holder, int position) {
        AnuncioGerenciar anuncioGerenciar = anuncioGerenciarList.get(position);

        // Exibir os dados do anúncio nos elementos do layout
        holder.textViewTitulo.setText(anuncioGerenciar.getTitulo());
        holder.textViewDescricao.setText(anuncioGerenciar.getDescricao());
        holder.textViewTipodeVenda.setText(anuncioGerenciar.getTipoAnuncio());
        holder.textViewPreco.setText(anuncioGerenciar.getPreco());
        holder.textViewNomeVendedor.setText(anuncioGerenciar.getNomePersonagem());

        // Carregar a imagem usando Picasso com placeholder
        if (anuncioGerenciar.getImagemUrl() != null && !anuncioGerenciar.getImagemUrl().isEmpty()) {
            Picasso.get()
                    .load(anuncioGerenciar.getImagemUrl())
                    .into(holder.imageViewItem, new Callback() {
                        @Override
                        public void onSuccess() {
                            // Imagem carregada com sucesso, não é necessário fazer nada aqui.
                        }

                        @Override
                        public void onError(Exception e) {
                            // Caso ocorra um erro ao carregar a imagem, definir o placeholder.
                            holder.imageViewItem.setImageResource(R.drawable.baseline_emergency_24);
                        }
                    });
        } else {
            // Caso a URL da imagem não esteja disponível, usar a imagem placeholder
            holder.imageViewItem.setImageResource(R.drawable.baseline_emergency_24);
        }
    }

    @Override
    public int getItemCount() {
        return anuncioGerenciarList.size();
    }

    public static class AnuncioGerenciarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewItem;
        TextView textViewTitulo;
        TextView textViewDescricao;
        TextView textViewTipodeVenda;
        TextView textViewPreco;
        TextView textViewNomeVendedor;
        ImageView imageViewExcluir;
        OnAnuncioClickListener clickListener;

        public AnuncioGerenciarViewHolder(@NonNull View itemView, OnAnuncioClickListener clickListener) {
            super(itemView);
            imageViewItem = itemView.findViewById(R.id.imageViewItem);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            textViewDescricao = itemView.findViewById(R.id.textViewDescricao);
            textViewTipodeVenda = itemView.findViewById(R.id.textViewTipodeVenda);
            textViewPreco = itemView.findViewById(R.id.textViewPreco);
            textViewNomeVendedor = itemView.findViewById(R.id.textViewNomeVendedor);
            imageViewExcluir = itemView.findViewById(R.id.imageViewExcluir);

            this.clickListener = clickListener;
            imageViewExcluir.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.imageViewExcluir) {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onAnuncioClick(position);
                }
            }
        }
    }

    public interface OnAnuncioClickListener {
        void onAnuncioClick(int position);
    }
}
