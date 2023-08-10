package com.guild.kaapoera.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.guild.kaapoera.R;

import java.util.List;

public class RotacaoQuinzenalListaWDAdapter extends RecyclerView.Adapter<RotacaoQuinzenalListaWDAdapter.ViewHolder> {

    private List<RotacaoQuinzenalWD> itemList;
    private Context context;

    public RotacaoQuinzenalListaWDAdapter(Context context, List<RotacaoQuinzenalWD> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rotacaoquinzenal_lista_wd_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RotacaoQuinzenalWD item = itemList.get(position);
        String dataEHora = "Dia " + item.getData() + " às " + item.getHorario() + "hs";

        holder.txtDataEHora.setText(dataEHora);
        holder.WD_EKprincipalNome.setText(item.getWD_EKprincipalNome());
        holder.WD_EKprincipalLVL.setText(String.valueOf(item.getWD_EKprincipalLVL()));
        holder.WD_EKsecundarioNome.setText(item.getWD_EKsecundarioNome());
        holder.WD_EKsecundarioLVL.setText(String.valueOf(item.getWD_EKprincipalLVL()));
        holder.WD_EKterciarioNome.setText(item.getWD_EKterciarioNome());
        holder.WD_EKterciarioLVL.setText(String.valueOf(item.getWD_EKterciarioLVL()));

        holder.WD_EDprincipalNome.setText(item.getWD_EDprincipalNome());
        holder.WD_EDprincipalLVL.setText(String.valueOf(item.getWD_EDprincipalLVL()));
        holder.WD_EDsecundarioNome.setText(item.getWD_EDsecundarioNome());
        holder.WD_EDsecundarioLVL.setText(String.valueOf(item.getWD_EDprincipalLVL()));
        holder.WD_EDterciarioNome.setText(item.getWD_EDterciarioNome());
        holder.WD_EDterciarioLVL.setText(String.valueOf(item.getWD_EDterciarioLVL()));

        holder.WD_Shooter1nome.setText(item.getWD_Shooter1nome());
        holder.WD_Shooter1lLVL.setText(String.valueOf(item.getWD_Shooter1lvl()));
        holder.WD_Shooter2nome.setText(item.getWD_Shooter2nome());
        holder.WD_Shooter2lLVL.setText(String.valueOf(item.getWD_Shooter2lvl()));
        holder.WD_Shooter3nome.setText(item.getWD_Shooter3nome());
        holder.WD_Shooter3lLVL.setText(String.valueOf(item.getWD_Shooter3lvl()));
        holder.WD_Shooter4nome.setText(item.getWD_Shooter4nome());
        holder.WD_Shooter4lLVL.setText(String.valueOf(item.getWD_Shooter4lvl()));
        holder.WD_Shooter5nome.setText(item.getWD_Shooter5nome());
        holder.WD_Shooter5lLVL.setText(String.valueOf(item.getWD_Shooter5lvl()));
        holder.WD_Shooter6nome.setText(item.getWD_Shooter6nome());
        holder.WD_Shooter6lLVL.setText(String.valueOf(item.getWD_Shooter6lvl()));
        holder.WD_Shooter7nome.setText(item.getWD_Shooter7nome());
        holder.WD_Shooter7lLVL.setText(String.valueOf(item.getWD_Shooter7lvl()));
        holder.WD_Shooter8nome.setText(item.getWD_Shooter8nome());
        holder.WD_Shooter8lLVL.setText(String.valueOf(item.getWD_Shooter8lvl()));
        holder.WD_Shooter9nome.setText(item.getWD_Shooter9nome());
        holder.WD_Shooter9lLVL.setText(String.valueOf(item.getWD_Shooter9lvl()));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDataEHora;
        TextView WD_EKprincipalNome;
        TextView WD_EKprincipalLVL;
        TextView WD_EKsecundarioNome;
        TextView WD_EKsecundarioLVL;
        TextView WD_EKterciarioNome;
        TextView WD_EKterciarioLVL;

        TextView WD_EDprincipalNome;
        TextView WD_EDprincipalLVL;
        TextView WD_EDsecundarioNome;
        TextView WD_EDsecundarioLVL;
        TextView WD_EDterciarioNome;
        TextView WD_EDterciarioLVL;

        TextView WD_Shooter1nome;
        TextView WD_Shooter1lLVL;
        TextView WD_Shooter2nome;
        TextView WD_Shooter2lLVL;
        TextView WD_Shooter3nome;
        TextView WD_Shooter3lLVL;
        TextView WD_Shooter4nome;
        TextView WD_Shooter4lLVL;
        TextView WD_Shooter5nome;
        TextView WD_Shooter5lLVL;
        TextView WD_Shooter6nome;
        TextView WD_Shooter6lLVL;
        TextView WD_Shooter7nome;
        TextView WD_Shooter7lLVL;
        TextView WD_Shooter8nome;
        TextView WD_Shooter8lLVL;
        TextView WD_Shooter9nome;
        TextView WD_Shooter9lLVL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDataEHora = itemView.findViewById(R.id.txtDataEHora);
            WD_EKprincipalNome = itemView.findViewById(R.id.WD_EKprincipalNome);
            WD_EKprincipalLVL = itemView.findViewById(R.id.WD_EKprincipalLVL);
            WD_EKsecundarioNome = itemView.findViewById(R.id.WD_EKsecundarioNome);
            WD_EKsecundarioLVL = itemView.findViewById(R.id.WD_EKsecundarioLVL);
            WD_EKterciarioNome = itemView.findViewById(R.id.WD_EKterciarioNome);
            WD_EKterciarioLVL = itemView.findViewById(R.id.WD_EKterciarioLVL);

            WD_EDprincipalNome = itemView.findViewById(R.id.WD_EDprincipalNome);
            WD_EDprincipalLVL = itemView.findViewById(R.id.WD_EDprincipalLVL);
            WD_EDsecundarioNome = itemView.findViewById(R.id.WD_EDsecundarioNome);
            WD_EDsecundarioLVL = itemView.findViewById(R.id.WD_EDsecundarioLVL);
            WD_EDterciarioNome = itemView.findViewById(R.id.WD_EDterciarioNome);
            WD_EDterciarioLVL = itemView.findViewById(R.id.WD_EDterciarioLVL);

            WD_Shooter1nome = itemView.findViewById(R.id.WD_Shooter1nome);
            WD_Shooter1lLVL = itemView.findViewById(R.id.WD_Shooter1LVL);
            WD_Shooter2nome = itemView.findViewById(R.id.WD_Shooter2nome);
            WD_Shooter2lLVL = itemView.findViewById(R.id.WD_Shooter2LVL);
            WD_Shooter3nome = itemView.findViewById(R.id.WD_Shooter3nome);
            WD_Shooter3lLVL = itemView.findViewById(R.id.WD_Shooter3LVL);
            WD_Shooter4nome = itemView.findViewById(R.id.WD_Shooter4nome);
            WD_Shooter4lLVL = itemView.findViewById(R.id.WD_Shooter4LVL);
            WD_Shooter5nome = itemView.findViewById(R.id.WD_Shooter5nome);
            WD_Shooter5lLVL = itemView.findViewById(R.id.WD_Shooter5LVL);
            WD_Shooter6nome = itemView.findViewById(R.id.WD_Shooter6nome);
            WD_Shooter6lLVL = itemView.findViewById(R.id.WD_Shooter6LVL);
            WD_Shooter7nome = itemView.findViewById(R.id.WD_Shooter7nome);
            WD_Shooter7lLVL = itemView.findViewById(R.id.WD_Shooter7LVL);
            WD_Shooter8nome = itemView.findViewById(R.id.WD_Shooter8nome);
            WD_Shooter8lLVL = itemView.findViewById(R.id.WD_Shooter8LVL);
            WD_Shooter9nome = itemView.findViewById(R.id.WD_Shooter9nome);
            WD_Shooter9lLVL = itemView.findViewById(R.id.WD_Shooter9LVL);
        }
    }
}
