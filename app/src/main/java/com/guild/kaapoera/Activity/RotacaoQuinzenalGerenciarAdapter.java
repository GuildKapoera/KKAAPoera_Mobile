package com.guild.kaapoera.Activity;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.guild.kaapoera.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RotacaoQuinzenalGerenciarAdapter extends RecyclerView.Adapter<RotacaoQuinzenalGerenciarAdapter.RotacaoQuinzenalGerenciarViewHolder> {

    private List<RotacaoQuinzenalGerenciar> itemList;
    private Context context;
    private String bossValue;

    public RotacaoQuinzenalGerenciarAdapter(List<RotacaoQuinzenalGerenciar> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public RotacaoQuinzenalGerenciarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rotacaoquinzenal_gerenciar_adapter_layout, parent, false);
        return new RotacaoQuinzenalGerenciarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RotacaoQuinzenalGerenciarViewHolder holder, int position) {
        RotacaoQuinzenalGerenciar item = itemList.get(position);

        // Inicialize o Firebase
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // Obtenha o UID do usuário logado
        String uid = auth.getCurrentUser().getUid();

        // Consulte o documento do usuário para obter o nome do personagem
        firestore.collection("Usuarios").document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String nomePersonagem = task.getResult().getString("nomePersonagem");
                firestore.collection("RotacaoQuinzenal").whereEqualTo("responsavel", nomePersonagem).get().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task1.getResult()) {
                            String bossValue = item.getBoss();
                            Log.d("DEBUG", "Valor do bossValue: " + bossValue);

                            // Defina o título com base no valor do bossValue
                            String bossTitle;
                            switch (bossValue) {
                                case "wd":
                                    bossTitle = "Rotação World Devourer";
                                    Log.d("DEBUG","Valor do switch case para o caso de wd: " + bossTitle);
                                    break;
                                case "lk":
                                    bossTitle = "Rotação Lore Keeper";
                                    Log.d("DEBUG","Valor do switch case para o caso de lk: " + bossTitle);
                                    break;
                                case "feru":
                                    bossTitle = "Rotação Ferumbras";
                                    Log.d("DEBUG","Valor do switch case para o caso de feru: " + bossTitle);
                                    break;
                                default:
                                    bossTitle = "Título Padrão";
                                    break;
                            }
                            holder.BossTitle.setText(bossTitle);
                            Log.d("DEBUG","Titulo adotado no holder para montar o layout: " + bossTitle);
                            holder.Data.setText(item.getData());

                            // Config de visibilidade dos campos de Participantes
                            if (TextUtils.isEmpty(item.getP1_Nome())){
                                holder.P1.setVisibility(View.GONE); holder.StrikeP1.setVisibility(View.GONE);
                            } else {holder.P1.setVisibility(View.VISIBLE); holder.StrikeP1.setVisibility(View.VISIBLE); holder.P1.setText(item.getP1_Nome());}
                            if (TextUtils.isEmpty(item.getP2_Nome())) {
                                holder.P2.setVisibility(View.GONE); holder.StrikeP2.setVisibility(View.GONE);
                            } else {holder.P2.setVisibility(View.VISIBLE); holder.StrikeP2.setVisibility(View.VISIBLE); holder.P2.setText(item.getP2_Nome());}
                            if (TextUtils.isEmpty(item.getP3_Nome())) {
                                holder.P3.setVisibility(View.GONE); holder.StrikeP3.setVisibility(View.GONE);
                            } else {holder.P3.setVisibility(View.VISIBLE); holder.StrikeP3.setVisibility(View.VISIBLE); holder.P3.setText(item.getP3_Nome());}
                            if (TextUtils.isEmpty(item.getP4_Nome())) {
                                holder.P4.setVisibility(View.GONE); holder.StrikeP4.setVisibility(View.GONE);
                            } else {holder.P4.setVisibility(View.VISIBLE); holder.StrikeP4.setVisibility(View.VISIBLE); holder.P4.setText(item.getP4_Nome());}
                            if (TextUtils.isEmpty(item.getP5_Nome())) {
                                holder.P5.setVisibility(View.GONE); holder.StrikeP5.setVisibility(View.GONE);
                            } else {holder.P5.setVisibility(View.VISIBLE); holder.StrikeP5.setVisibility(View.VISIBLE); holder.P5.setText(item.getP5_Nome());}
                            if (TextUtils.isEmpty(item.getP6_Nome())) {
                                holder.P6.setVisibility(View.GONE); holder.StrikeP6.setVisibility(View.GONE);
                            } else {holder.P6.setVisibility(View.VISIBLE); holder.StrikeP6.setVisibility(View.VISIBLE); holder.P6.setText(item.getP6_Nome());}
                            if (TextUtils.isEmpty(item.getP7_Nome())) {
                                holder.P7.setVisibility(View.GONE); holder.StrikeP7.setVisibility(View.GONE);
                            } else {holder.P7.setVisibility(View.VISIBLE); holder.StrikeP7.setVisibility(View.VISIBLE); holder.P7.setText(item.getP7_Nome());}
                            if (TextUtils.isEmpty(item.getP8_Nome())) {
                                holder.P8.setVisibility(View.GONE); holder.StrikeP8.setVisibility(View.GONE);
                            } else {holder.P8.setVisibility(View.VISIBLE); holder.StrikeP8.setVisibility(View.VISIBLE); holder.P8.setText(item.getP8_Nome());}
                            if (TextUtils.isEmpty(item.getP9_Nome())) {
                                holder.P9.setVisibility(View.GONE); holder.StrikeP9.setVisibility(View.GONE);
                            } else {holder.P9.setVisibility(View.VISIBLE); holder.StrikeP9.setVisibility(View.VISIBLE); holder.P9.setText(item.getP9_Nome());}
                            if (TextUtils.isEmpty(item.getP10_Nome())) {
                                holder.P10.setVisibility(View.GONE); holder.StrikeP10.setVisibility(View.GONE);
                            } else {holder.P10.setVisibility(View.VISIBLE); holder.StrikeP10.setVisibility(View.VISIBLE); holder.P10.setText(item.getP10_Nome());}
                            if (TextUtils.isEmpty(item.getP11_Nome())) {
                                holder.P11.setVisibility(View.GONE); holder.StrikeP11.setVisibility(View.GONE);
                            } else {holder.P11.setVisibility(View.VISIBLE); holder.StrikeP11.setVisibility(View.VISIBLE); holder.P11.setText(item.getP11_Nome());}
                            if (TextUtils.isEmpty(item.getP12_Nome())) {
                                holder.P12.setVisibility(View.GONE); holder.StrikeP12.setVisibility(View.GONE);
                            } else {holder.P12.setVisibility(View.VISIBLE); holder.StrikeP12.setVisibility(View.VISIBLE); holder.P12.setText(item.getP12_Nome());}
                            if (TextUtils.isEmpty(item.getP13_Nome())) {
                                holder.P13.setVisibility(View.GONE); holder.StrikeP13.setVisibility(View.GONE);
                            } else {holder.P13.setVisibility(View.VISIBLE); holder.StrikeP13.setVisibility(View.VISIBLE); holder.P13.setText(item.getP13_Nome());}
                            if (TextUtils.isEmpty(item.getP14_Nome())) {
                                holder.P14.setVisibility(View.GONE); holder.StrikeP14.setVisibility(View.GONE);
                            } else {holder.P14.setVisibility(View.VISIBLE); holder.StrikeP14.setVisibility(View.VISIBLE); holder.P14.setText(item.getP14_Nome());}
                            if (TextUtils.isEmpty(item.getP15_Nome())) {
                                holder.P15.setVisibility(View.GONE); holder.StrikeP15.setVisibility(View.GONE);
                            } else {holder.P15.setVisibility(View.VISIBLE); holder.StrikeP15.setVisibility(View.VISIBLE); holder.P15.setText(item.getP15_Nome());}
                        }
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class RotacaoQuinzenalGerenciarViewHolder extends RecyclerView.ViewHolder {
        TextView BossTitle;
        TextView Data;
        TextView P1;
        Button StrikeP1;
        TextView P2;
        Button StrikeP2;
        TextView P3;
        Button StrikeP3;
        TextView P4;
        Button StrikeP4;
        TextView P5;
        Button StrikeP5;
        TextView P6;
        Button StrikeP6;
        TextView P7;
        Button StrikeP7;
        TextView P8;
        Button StrikeP8;
        TextView P9;
        Button StrikeP9;
        TextView P10;
        Button StrikeP10;
        TextView P11;
        Button StrikeP11;
        TextView P12;
        Button StrikeP12;
        TextView P13;
        Button StrikeP13;
        TextView P14;
        Button StrikeP14;
        TextView P15;
        Button StrikeP15;

        public RotacaoQuinzenalGerenciarViewHolder(@NonNull View itemView) {
            super(itemView);
            Data = itemView.findViewById(R.id.Data);
            BossTitle = itemView.findViewById(R.id.BossTitle);

            P1 = itemView.findViewById(R.id.P1);
            StrikeP1 = itemView.findViewById(R.id.StrikeP1);
            P2 = itemView.findViewById(R.id.P2);
            StrikeP2 = itemView.findViewById(R.id.StrikeP2);
            P3 = itemView.findViewById(R.id.P3);
            StrikeP3 = itemView.findViewById(R.id.StrikeP3);
            P4 = itemView.findViewById(R.id.P4);
            StrikeP4 = itemView.findViewById(R.id.StrikeP4);
            P5 = itemView.findViewById(R.id.P5);
            StrikeP5 = itemView.findViewById(R.id.StrikeP5);
            P6 = itemView.findViewById(R.id.P6);
            StrikeP6 = itemView.findViewById(R.id.StrikeP6);
            P7 = itemView.findViewById(R.id.P7);
            StrikeP7 = itemView.findViewById(R.id.StrikeP7);
            P8 = itemView.findViewById(R.id.P8);
            StrikeP8 = itemView.findViewById(R.id.StrikeP8);
            P9 = itemView.findViewById(R.id.P9);
            StrikeP9 = itemView.findViewById(R.id.StrikeP9);
            P10 = itemView.findViewById(R.id.P10);
            StrikeP10 = itemView.findViewById(R.id.StrikeP10);
            P11 = itemView.findViewById(R.id.P11);
            StrikeP11 = itemView.findViewById(R.id.StrikeP11);
            P12 = itemView.findViewById(R.id.P12);
            StrikeP12 = itemView.findViewById(R.id.StrikeP12);
            P13 = itemView.findViewById(R.id.P13);
            StrikeP13 = itemView.findViewById(R.id.StrikeP13);
            P14 = itemView.findViewById(R.id.P14);
            StrikeP14 = itemView.findViewById(R.id.StrikeP14);
            P15 = itemView.findViewById(R.id.P15);
            StrikeP15 = itemView.findViewById(R.id.StrikeP15);

            // Ouvinte de clique aos botoes de strike
            StrikeP1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerName = P1.getText().toString();
                    updateFirestoreWithStrike(playerName);
                }
            });
            StrikeP2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerName = P2.getText().toString();
                    updateFirestoreWithStrike(playerName);
                }
            });
            StrikeP3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerName = P3.getText().toString();
                    updateFirestoreWithStrike(playerName);
                }
            });
            StrikeP4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerName = P4.getText().toString();
                    updateFirestoreWithStrike(playerName);
                }
            });
            StrikeP5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerName = P5.getText().toString();
                    updateFirestoreWithStrike(playerName);
                }
            });
            StrikeP6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerName = P6.getText().toString();
                    updateFirestoreWithStrike(playerName);
                }
            });
            StrikeP7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerName = P7.getText().toString();
                    updateFirestoreWithStrike(playerName);
                }
            });
            StrikeP8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerName = P8.getText().toString();
                    updateFirestoreWithStrike(playerName);
                }
            });
            StrikeP9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerName = P9.getText().toString();
                    updateFirestoreWithStrike(playerName);
                }
            });StrikeP10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerName = P10.getText().toString();
                    updateFirestoreWithStrike(playerName);
                }
            });
            StrikeP11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerName = P11.getText().toString();
                    updateFirestoreWithStrike(playerName);
                }
            });StrikeP12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerName = P12.getText().toString();
                    updateFirestoreWithStrike(playerName);
                }
            });
            StrikeP13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerName = P13.getText().toString();
                    updateFirestoreWithStrike(playerName);
                }
            });StrikeP14.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerName = P14.getText().toString();
                    updateFirestoreWithStrike(playerName);
                }
            });StrikeP15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playerName = P15.getText().toString();
                    updateFirestoreWithStrike(playerName);
                }
            });

        }
    }
    private void updateFirestoreWithStrike(String playerName) {
        // Lógica para atualizar o Firestore com o strike para o jogador playerName
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Usuarios")
                .whereEqualTo("nomePersonagem", playerName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String formattedDate = dateFormat.format(new Date());
                            document.getReference().update("strike", formattedDate);
                            String toastMessage = playerName + " com Strike a partir de " + formattedDate;
                            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}