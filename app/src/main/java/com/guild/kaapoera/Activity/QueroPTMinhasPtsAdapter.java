package com.guild.kaapoera.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.guild.kaapoera.R;

import java.util.List;

public class QueroPTMinhasPtsAdapter extends RecyclerView.Adapter<QueroPTMinhasPtsAdapter.ViewHolder> {

    private Context context;
    private List<QueroPTMinhasPts> queroPTList;
    private FirebaseAuth mAuth;
    private onPTClickListener clickListener;

    public QueroPTMinhasPtsAdapter(Context context, List<QueroPTMinhasPts> queroPTList, onPTClickListener clickListener) {
        this.context = context;
        this.queroPTList = queroPTList;
        this.clickListener = clickListener;
        mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quero_pt_minhas_pts_adapter, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QueroPTMinhasPts queroPT = queroPTList.get(position);

        // Aqui, você deve definir os valores nos elementos da view do item da RecyclerView
        // com base nos dados do objeto QueroPTMinhasPts

        holder.txtHorario.setText(queroPT.getHorario());
        holder.txtResponsavel.setText(queroPT.getResponsavel());
        holder.txtContato.setText(queroPT.getContato());
        holder.txtLevelMaximo.setText(String.valueOf(queroPT.getLevelMaximo()));
        holder.txtLevelMinimo.setText(String.valueOf(queroPT.getLevelMinimo()));
        holder.txtEKnome.setText(String.valueOf(queroPT.getEKnome()));
        holder.txtEKlevel.setText(String.valueOf(queroPT.getEKlevel()));
        holder.txtEDnome.setText(String.valueOf(queroPT.getEDnome()));
        holder.txtEDlevel.setText(String.valueOf(queroPT.getEDlevel()));
        holder.txtRPnome.setText(String.valueOf(queroPT.getRPnome()));
        holder.txtRPlevel.setText(String.valueOf(queroPT.getRPlevel()));
        holder.txtMSnome.setText(String.valueOf(queroPT.getMSnome()));
        holder.txtMSlevel.setText(String.valueOf(queroPT.getMSlevel()));

        // Configurar o clique para a imagem de contato RP
        holder.imageViewRPContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contatoRP = queroPT.getRPcontato();
                String RPcod = queroPT.getRPcod();
                if (contatoRP != null && !contatoRP.isEmpty()) {
                    String whatsappUrl = "https://api.whatsapp.com/send?phone=" + RPcod + contatoRP;
                    Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
                    whatsappIntent.setData(Uri.parse(whatsappUrl));
                    context.startActivity(whatsappIntent);
                } else {
                    Toast.makeText(context, "Contato de RP indisponível.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Configurar o clique para a imagem de contato EK
        holder.imageViewEKContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contatoEK = queroPT.getEKcontato();
                String EKcod = queroPT.getEKcod();
                if (contatoEK != null && !contatoEK.isEmpty()) {
                    String whatsappUrl = "https://api.whatsapp.com/send?phone=" + EKcod + contatoEK;
                    Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
                    whatsappIntent.setData(Uri.parse(whatsappUrl));
                    context.startActivity(whatsappIntent);
                } else {
                    Toast.makeText(context, "Contato de EK indisponível.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar o clique para a imagem de contato ED
        holder.imageViewEDContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contatoED = queroPT.getEDcontato();
                String EDcod = queroPT.getEDcod();
                if (contatoED != null && !contatoED.isEmpty()) {
                    String whatsappUrl = "https://api.whatsapp.com/send?phone=" + EDcod + contatoED;
                    Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
                    whatsappIntent.setData(Uri.parse(whatsappUrl));
                    context.startActivity(whatsappIntent);
                } else {
                    Toast.makeText(context, "Contato de ED indisponível.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar o clique para a imagem de contato MS
        holder.imageViewMSContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contatoMS = queroPT.getMScontato();
                String MScod = queroPT.getMScod();
                if (contatoMS != null && !contatoMS.isEmpty()) {
                    String whatsappUrl = "https://api.whatsapp.com/send?phone=" + MScod + contatoMS;
                    Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
                    whatsappIntent.setData(Uri.parse(whatsappUrl));
                    context.startActivity(whatsappIntent);
                } else {
                    Toast.makeText(context, "Contato de MS indisponível.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return queroPTList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtHorario;
        TextView txtResponsavel;
        TextView txtContato;
        TextView txtLevelMaximo;
        TextView txtLevelMinimo;
        Button btnEntrarNaPT;
        TextView txtEKnome;
        TextView txtEKlevel;
        TextView txtRPnome;
        TextView txtRPlevel;
        TextView txtEDnome;
        TextView txtEDlevel;
        TextView txtMSnome;
        TextView txtMSlevel;
        ImageView imageViewExcluir;
        onPTClickListener clickListener;
        ImageView imageViewRPContact;
        ImageView imageViewEKContact;
        ImageView imageViewEDContact;
        ImageView imageViewMSContact;

        public ViewHolder(@NonNull View itemView, onPTClickListener clickListener) {
            super(itemView);
            txtHorario = itemView.findViewById(R.id.txtHorario);
            txtResponsavel = itemView.findViewById(R.id.txtResponsavel);
            txtContato = itemView.findViewById(R.id.txtContato);
            txtLevelMaximo = itemView.findViewById(R.id.txtLevelMaximo);
            txtLevelMinimo = itemView.findViewById(R.id.txtLevelMinimo);
            btnEntrarNaPT = itemView.findViewById(R.id.btnEntrarNaPT);
            txtEKnome = itemView.findViewById(R.id.txtEKNome);
            txtEKlevel = itemView.findViewById(R.id.txtEKLevel);
            txtEDnome = itemView.findViewById(R.id.txtEDNome);
            txtEDlevel = itemView.findViewById(R.id.txtEDLevel);
            txtRPnome = itemView.findViewById(R.id.txtRPNome);
            txtRPlevel = itemView.findViewById(R.id.txtRPLevel);
            txtMSnome = itemView.findViewById(R.id.txtMSNome);
            txtMSlevel = itemView.findViewById(R.id.txtMSLevel);
            imageViewExcluir = itemView.findViewById(R.id.imageViewExcluir);
            imageViewRPContact = itemView.findViewById(R.id.imageViewRPContact);
            imageViewEKContact = itemView.findViewById(R.id.imageViewEKContact);
            imageViewEDContact = itemView.findViewById(R.id.imageViewEDContact);
            imageViewMSContact = itemView.findViewById(R.id.imageViewMSContact);

            this.clickListener = clickListener;
            imageViewExcluir.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.imageViewExcluir) {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (clickListener != null) {
                        clickListener.onPTclick(position);
                    }
                }
            }
        }
    }

    public interface onPTClickListener {
        void onPTclick(int position);
    }
}
