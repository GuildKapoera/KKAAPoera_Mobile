package com.guild.kaapoera.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.guild.kaapoera.R;

import java.util.ArrayList;
import java.util.List;

public class RotacaoQuinzenalListaLKAdapter extends RecyclerView.Adapter<RotacaoQuinzenalListaLKAdapter.ViewHolder> {

    private List<RotacaoQuinzenalLK> itemList;
    private Context context;
    private FirebaseAuth mAuth;

    public RotacaoQuinzenalListaLKAdapter(Context context, List<RotacaoQuinzenalLK> itemList) {
        this.context = context;
        this.itemList = itemList;
        mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rotacaoquinzenal_lista_lk_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RotacaoQuinzenalLK item = itemList.get(position);
        String dataEHora = "Dia " + item.getData() + " às " + item.getHorario() + "hs";

        holder.txtDataEHora.setText(dataEHora);
        holder.LK_EKprincipalNome.setText(item.getLk_EK_1_Nome());
        holder.LK_EKprincipalLVL.setText(String.valueOf(item.getLk_EK_1_Level()));
        holder.LK_EKsecundarioNome.setText(item.getLk_EK_2_Nome());
        holder.LK_EKsecundarioLVL.setText(String.valueOf(item.getLk_EK_2_Level()));
        holder.LK_EKterciarioNome.setText(item.getLk_EK_3_Nome());
        holder.LK_EKterciarioLVL.setText(String.valueOf(item.getLk_EK_3_Level()));

        holder.LK_EDprincipalNome.setText(item.getLk_ED_1_Nome());
        holder.LK_EDprincipalLVL.setText(String.valueOf(item.getLk_ED_1_Level()));


        holder.LK_Shooter1nome.setText(item.getLk_RP_1_Nome());
        holder.LK_Shooter1lvl.setText(String.valueOf(item.getLk_RP_1_Level()));
        holder.LK_Shooter2nome.setText(item.getLk_RP_2_Nome());
        holder.LK_Shooter2lvl.setText(String.valueOf(item.getLk_RP_2_Level()));
        holder.LK_Shooter3nome.setText(item.getLk_RP_3_Nome());
        holder.LK_Shooter3lvl.setText(String.valueOf(item.getLk_RP_3_Level()));

        holder.LK_Shooter4nome.setText(item.getLk_Shooter_1_Nome());
        holder.LK_Shooter4lvl.setText(String.valueOf(item.getLk_Shooter_1_Level()));
        holder.LK_Shooter5nome.setText(item.getLk_Shooter_2_Nome());
        holder.LK_Shooter5lvl.setText(String.valueOf(item.getLk_Shooter_2_Level()));
        holder.LK_Shooter6nome.setText(item.getLk_Shooter_3_Nome());
        holder.LK_Shooter6lvl.setText(String.valueOf(item.getLk_Shooter_3_Level()));
        holder.LK_Shooter7nome.setText(item.getLk_Shooter_4_Nome());
        holder.LK_Shooter7lvl.setText(String.valueOf(item.getLk_Shooter_4_Level()));
        holder.LK_Shooter8nome.setText(item.getLk_Shooter_5_Nome());
        holder.LK_Shooter8lvl.setText(String.valueOf(item.getLk_Shooter_5_Level()));
        holder.LK_Shooter9nome.setText(item.getLk_Shooter_6_Nome());
        holder.LK_Shooter9lvl.setText(String.valueOf(item.getLk_Shooter_6_Level()));
        holder.LK_Shooter10nome.setText((item.getLk_Shooter_7_Nome()));
        holder.LK_Shooter10lvl.setText((String.valueOf(item.getLk_Shooter_7_Level())));
        holder.LK_Shooter11nome.setText(item.getLk_Shooter_8_Nome());
        holder.LK_Shooter11lvl.setText((String.valueOf(item.getLk_Shooter_8_Level())));



        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String uidDoUsuario = user.getUid();
            DocumentReference userDocRef = FirebaseFirestore.getInstance().collection("Usuarios").document(uidDoUsuario);

            userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            String nomePersonagem = document.getString("nomePersonagem");

                            boolean usuarioJaEstaNaPT = item.usuarioEstaNaRotacaoLK(nomePersonagem);

                            if (usuarioJaEstaNaPT) {
                                holder.btnEntrarNaRotacao.setVisibility(View.GONE);
                                holder.btnSairDaRotacao.setVisibility(View.VISIBLE);
                            } else {
                                holder.btnEntrarNaRotacao.setVisibility(View.VISIBLE);
                                holder.btnSairDaRotacao.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });
        } else {
            holder.btnEntrarNaRotacao.setVisibility(View.VISIBLE);
            holder.btnSairDaRotacao.setVisibility(View.GONE);
        }

        holder.btnEntrarNaRotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obterPosicoesDisponiveisParaVocacaoDoUsuario(itemList.get(position), new PosicoesCallback() {
                    @Override
                    public void onPosicoesObtidas(List<String> posicoes) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Escolha uma posição disponível");

                        if (posicoes.isEmpty()) {
                            builder.setMessage("Não há posições disponíveis para a sua vocação.");
                            builder.setPositiveButton("OK", null);
                            builder.show();
                            return;
                        }

                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.select_dialog_singlechoice, posicoes);

                        builder.setAdapter(arrayAdapter, (dialog, which) -> {
                            String posicaoSelecionada = posicoes.get(which);
                            atualizarRotacaoComPosicaoDoUsuario(itemList.get(position), posicaoSelecionada);
                            Toast.makeText(context, "Posição " + posicaoSelecionada + " selecionada", Toast.LENGTH_SHORT).show();

                        });

                        builder.show();
                    }
                });
            }
        });
        //Função sair aqui
        holder.btnSairDaRotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    String usuarioID = user.getUid();
                    Log.d("DEBUG", "ID do usuário: " + usuarioID);
                    // Obter o ID do documento da rotação do item (usando getters e setters)
                    String rotacaoID = item.getRotacaoID();
                    Log.d("DEBUG", "ID do rotação: " + rotacaoID);

                    DocumentReference userDocRef = FirebaseFirestore.getInstance().collection("Usuarios").document(usuarioID);
                    userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document != null && document.exists()) {
                                    String nomePersonagem = document.getString("nomePersonagem");
                                    Log.d("DEBUG", "Nome do Personagem: " + nomePersonagem);
                                    // Obter a referência do documento da rotação no Firestore
                                    DocumentReference rotacaoDocRef = FirebaseFirestore.getInstance().collection("RotacaoQuinzenal").document(item.getRotacaoID());
                                    Log.d("DEBUG", "Documento de Referencia rotação: " + rotacaoDocRef);
                                    rotacaoDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> rotacaoTask) {
                                            if (rotacaoTask.isSuccessful()) {
                                                DocumentSnapshot rotacaoDocument = rotacaoTask.getResult();
                                                if (rotacaoDocument != null && rotacaoDocument.exists()) {
                                                    // Verificar e atualizar a posição correspondente com o nome do usuário
                                                    String[] posicoes = {"lk_EK_1_Nome", "lk_EK_2_Nome", "lk_EK_3_Nome", "lk_ED_1_Nome", "lk_RP_1_Nome", "lk_RP_2_Nome", "lk_RP_3_Nome", "lk_Shooter_1_Nome", "lk_Shooter_2_Nome", "lk_Shooter_3_Nome", "lk_Shooter_4_Nome", "lk_Shooter_5_Nome", "lk_Shooter_6_Nome", "lk_Shooter_7_Nome", "lk_Shooter_8_Nome"};
                                                    for (String posicao : posicoes) {
                                                        Log.d("DEBUG", "A posições é: " + posicao);
                                                        String nomeAtirador = rotacaoDocument.getString(posicao);
                                                        Log.d("DEBUG", "Nome do atirador é: " + nomeAtirador);
                                                        if (nomeAtirador != null && nomeAtirador.equals(nomePersonagem)) {
                                                            String campoLevel = posicao.replace("_Nome", "_Level");
                                                            Log.d("DEBUG", "O campo Level: " + campoLevel);
                                                            // Atualizar a posição com o nome do usuário
                                                            rotacaoDocRef.update(posicao, "", campoLevel, 0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> updateTask) {
                                                                    if (updateTask.isSuccessful()) {
                                                                        // Atualize o item na lista com as informações atualizadas
                                                                        if ((posicao.equals("lk_EK_1_Nome"))) {
                                                                            item.setLk_EK_1_Nome("");
                                                                            item.setLk_EK_1_Level(0);} else { }
                                                                        if ((posicao.equals("lk_EK_2_Nome"))) {
                                                                            item.setLk_EK_2_Nome("");
                                                                            item.setLk_EK_2_Level(0);} else { }
                                                                        if ((posicao.equals("lk_EK_3_Nome"))) {
                                                                            item.setLk_EK_3_Nome("");
                                                                            item.setLk_EK_3_Level(0);} else { }
                                                                        if ((posicao.equals("lk_ED_1_Nome"))) {
                                                                            item.setLk_ED_1_Nome("");
                                                                            item.setLk_ED_1_Level(0);} else { }
                                                                        if ((posicao.equals("lk_RP_1_Nome"))) {
                                                                            item.setLk_RP_1_Nome("");
                                                                            item.setLk_RP_1_Level(0);} else { }
                                                                        if ((posicao.equals("lk_RP_2_Nome"))) {
                                                                            item.setLk_RP_2_Nome("");
                                                                            item.setLk_RP_2_Level(0);} else { }
                                                                        if ((posicao.equals("lk_RP_3_Nome"))) {
                                                                            item.setLk_RP_3_Nome("");
                                                                            item.setLk_RP_3_Level(0);} else { }
                                                                        if ((posicao.equals("lk_Shooter_1_Nome"))) {
                                                                            item.setLk_Shooter_1_Nome("");
                                                                            item.setLk_Shooter_1_Level(0);} else { }
                                                                        if ((posicao.equals("lk_Shooter_2_Nome"))) {
                                                                            item.setLk_Shooter_2_Nome("");
                                                                            item.setLk_Shooter_2_Level(0);} else { }
                                                                        if ((posicao.equals("lk_Shooter_3_Nome"))) {
                                                                            item.setLk_Shooter_3_Nome("");
                                                                            item.setLk_Shooter_3_Level(0);} else { }
                                                                        if ((posicao.equals("lk_Shooter_4_Nome"))) {
                                                                            item.setLk_Shooter_4_Nome("");
                                                                            item.setLk_Shooter_4_Level(0);} else { }
                                                                        if ((posicao.equals("lk_Shooter_5_Nome"))) {
                                                                            item.setLk_Shooter_5_Nome("");
                                                                            item.setLk_Shooter_5_Level(0);} else { }
                                                                        if ((posicao.equals("lk_Shooter_6_Nome"))) {
                                                                            item.setLk_Shooter_6_Nome("");
                                                                            item.setLk_Shooter_6_Level(0);} else { }
                                                                        if ((posicao.equals("lk_Shooter_7_Nome"))) {
                                                                            item.setLk_Shooter_7_Nome("");
                                                                            item.setLk_Shooter_7_Level(0);} else { }
                                                                        if ((posicao.equals("lk_Shooter_8_Nome"))) {
                                                                            item.setLk_Shooter_8_Nome("");
                                                                            item.setLk_Shooter_8_Level(0);} else { }

                                                                        // Atualização bem-sucedida
                                                                        Toast.makeText(context, "Você saiu da Rotação do LK.", Toast.LENGTH_SHORT).show();
                                                                        notifyDataSetChanged();
                                                                    } else {
                                                                        // Tratar o erro na atualização
                                                                        Toast.makeText(context, "Erro ao sair da Rotação do LK.", Toast.LENGTH_SHORT).show();
                                                                    }

                                                                }
                                                            });
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });


    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDataEHora;
        TextView LK_EKprincipalNome;
        TextView LK_EKprincipalLVL;
        TextView LK_EKsecundarioNome;
        TextView LK_EKsecundarioLVL;
        TextView LK_EKterciarioNome;
        TextView LK_EKterciarioLVL;

        TextView LK_EDprincipalNome;
        TextView LK_EDprincipalLVL;

        TextView LK_Shooter1nome;
        TextView LK_Shooter1lvl;
        TextView LK_Shooter2nome;
        TextView LK_Shooter2lvl;
        TextView LK_Shooter3nome;
        TextView LK_Shooter3lvl;
        TextView LK_Shooter4nome;
        TextView LK_Shooter4lvl;
        TextView LK_Shooter5nome;
        TextView LK_Shooter5lvl;
        TextView LK_Shooter6nome;
        TextView LK_Shooter6lvl;
        TextView LK_Shooter7nome;
        TextView LK_Shooter7lvl;
        TextView LK_Shooter8nome;
        TextView LK_Shooter8lvl;
        TextView LK_Shooter9nome;
        TextView LK_Shooter9lvl;
        TextView LK_Shooter10nome;
        TextView LK_Shooter10lvl;
        TextView LK_Shooter11nome;
        TextView LK_Shooter11lvl;


        Button btnEntrarNaRotacao;
        Button btnSairDaRotacao;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDataEHora = itemView.findViewById(R.id.txtDataEHora);
            LK_EKprincipalNome = itemView.findViewById(R.id.LK_EKprincipalNome);
            LK_EKprincipalLVL = itemView.findViewById(R.id.LK_EKprincipalLVL);
            LK_EKsecundarioNome = itemView.findViewById(R.id.LK_EKsecundarioNome);
            LK_EKsecundarioLVL = itemView.findViewById(R.id.LK_EKsecundarioLVL);
            LK_EKterciarioNome = itemView.findViewById(R.id.LK_EKterciarioNome);
            LK_EKterciarioLVL = itemView.findViewById(R.id.LK_EKterciarioLVL);

            LK_EDprincipalNome = itemView.findViewById(R.id.LK_EDprincipalNome);
            LK_EDprincipalLVL = itemView.findViewById(R.id.LK_EDprincipalLVL);

            LK_Shooter1nome = itemView.findViewById(R.id.LK_Shooter1Nome);
            LK_Shooter1lvl = itemView.findViewById(R.id.LK_Shooter1LVL);
            LK_Shooter2nome = itemView.findViewById(R.id.LK_Shooter2Nome);
            LK_Shooter2lvl = itemView.findViewById(R.id.LK_Shooter2LVL);
            LK_Shooter3nome = itemView.findViewById(R.id.LK_Shooter3nome);
            LK_Shooter3lvl = itemView.findViewById(R.id.LK_Shooter3LVL);

            LK_Shooter4nome = itemView.findViewById(R.id.LK_Shooter4nome);
            LK_Shooter4lvl = itemView.findViewById(R.id.LK_Shooter4LVL);
            LK_Shooter5nome = itemView.findViewById(R.id.LK_Shooter5nome);
            LK_Shooter5lvl = itemView.findViewById(R.id.LK_Shooter5LVL);
            LK_Shooter6nome = itemView.findViewById(R.id.LK_Shooter6nome);
            LK_Shooter6lvl = itemView.findViewById(R.id.LK_Shooter6LVL);
            LK_Shooter7nome = itemView.findViewById(R.id.LK_Shooter7nome);
            LK_Shooter7lvl = itemView.findViewById(R.id.LK_Shooter7LVL);
            LK_Shooter8nome = itemView.findViewById(R.id.LK_Shooter8nome);
            LK_Shooter8lvl = itemView.findViewById(R.id.LK_Shooter8LVL);
            LK_Shooter9nome = itemView.findViewById(R.id.LK_Shooter9nome);
            LK_Shooter9lvl = itemView.findViewById(R.id.LK_Shooter9LVL);
            LK_Shooter10nome = itemView.findViewById(R.id.LK_Shooter10nome);
            LK_Shooter10lvl = itemView.findViewById(R.id.LK_Shooter10LVL);
            LK_Shooter11nome = itemView.findViewById(R.id.LK_Shooter11nome);
            LK_Shooter11lvl = itemView.findViewById(R.id.LK_Shooter11LVL);

            btnEntrarNaRotacao = itemView.findViewById(R.id.btnEntrarNaRotacao);
            btnSairDaRotacao = itemView.findViewById(R.id.btnSairDaRotacao);

        }
    }

    private void obterPosicoesDisponiveisParaVocacaoDoUsuario(RotacaoQuinzenalLK rotacao, PosicoesCallback callback) {
        List<String> posicoesDisponiveis = new ArrayList<>();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String userID = user.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference usuariosRef = db.collection("Usuarios");
            usuariosRef.document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            String vocacaoUsuario = document.getString("vocacao");
                            int levelUsuario = document.getLong("level").intValue();
                            Log.d("DEBUG", "Vocação do usuário: " + vocacaoUsuario);
                            Log.d("DEBUG", "Level do usuário: " + levelUsuario);
                            if (vocacaoUsuario != null) {
                                switch (vocacaoUsuario) {
                                    case "Elder Druid":
                                        String Healer1 = rotacao.getLk_ED_1_Nome();
                                        if (Healer1.isEmpty()) {
                                            posicoesDisponiveis.add("Healer1");
                                        }
                                        String EDShooter4 = rotacao.getLk_Shooter_1_Nome();
                                        if (EDShooter4.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter4");
                                        }
                                        String EDShooter5 = rotacao.getLk_Shooter_2_Nome();
                                        if (EDShooter5.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter5");
                                        }
                                        String EDShooter6 = rotacao.getLk_Shooter_3_Nome();
                                        if (EDShooter6.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter6");
                                        }
                                        String EDShooter7 = rotacao.getLk_Shooter_4_Nome();
                                        if (EDShooter7.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter7");
                                        }
                                        String EDShooter8 = rotacao.getLk_Shooter_5_Nome();
                                        if (EDShooter8.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter8");
                                        }
                                        String EDShooter9 = rotacao.getLk_Shooter_6_Nome();
                                        if (EDShooter9.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter9");
                                        }
                                        String EDShooter10 = rotacao.getLk_Shooter_7_Nome();
                                        if (EDShooter10.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter10");
                                        }
                                        String EDShooter11 = rotacao.getLk_Shooter_8_Nome();
                                        if (EDShooter11.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter11");
                                        }
                                        break;
                                    case "Elite Knight":
                                        String Blocker1 = rotacao.getLk_EK_1_Nome();
                                        String Blocker2 = rotacao.getLk_EK_2_Nome();
                                        String Blocker3 = rotacao.getLk_EK_3_Nome();

                                        // Verificando a condição do nível para listar posições
                                        if (levelUsuario >= 500) {
                                            if (Blocker1.isEmpty()) {
                                                posicoesDisponiveis.add("Blocker1");
                                            }
                                            if (Blocker2.isEmpty()) {
                                                posicoesDisponiveis.add("Blocker2");
                                            }
                                            if (Blocker3.isEmpty()) {
                                                posicoesDisponiveis.add("Blocker3");
                                            }
                                        } else { // Nível menor que 500
                                            if (Blocker2.isEmpty()) {
                                                posicoesDisponiveis.add("Blocker2");
                                            }
                                            if (Blocker3.isEmpty()) {
                                                posicoesDisponiveis.add("Blocker3");
                                            }
                                        }
                                        break;
                                    case "Royal Paladin":
                                        String Shooter1 = rotacao.getLk_RP_1_Nome();
                                        String Shooter2 = rotacao.getLk_RP_2_Nome();
                                        String Shooter3 = rotacao.getLk_RP_3_Nome();
                                        String Shooter9 = rotacao.getLk_Shooter_6_Nome();
                                        String Shooter10 = rotacao.getLk_Shooter_7_Nome();
                                        String Shooter11 = rotacao.getLk_Shooter_8_Nome();

                                        // Verificando a condição do nível para listar posições
                                        if (levelUsuario >= 400) {
                                            if (Shooter1.isEmpty()) {
                                                posicoesDisponiveis.add("Shooter1");
                                            }
                                            if (Shooter2.isEmpty()) {
                                                posicoesDisponiveis.add("Shooter2");
                                            }
                                            if (Shooter3.isEmpty()) {
                                                posicoesDisponiveis.add("Shooter3");
                                            }
                                            if (Shooter9.isEmpty()) {
                                                posicoesDisponiveis.add("Shooter9");
                                            }
                                            if (Shooter10.isEmpty()) {
                                                posicoesDisponiveis.add("Shooter10");
                                            }
                                            if (Shooter11.isEmpty()) {
                                                posicoesDisponiveis.add("Shooter11");
                                            }
                                        } else { // Nível menor que 400
                                            if (Shooter9.isEmpty()) {
                                                posicoesDisponiveis.add("Shooter9");
                                            }
                                            if (Shooter10.isEmpty()) {
                                                posicoesDisponiveis.add("Shooter10");
                                            }
                                            if (Shooter11.isEmpty()) {
                                                posicoesDisponiveis.add("Shooter11");
                                            }
                                        }
                                        break;
                                    case "Master Sorcerer":
                                        String MSShooter1 = rotacao.getLk_Shooter_1_Nome();
                                        if (MSShooter1.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter4");
                                        }
                                        String MSShooter2 = rotacao.getLk_Shooter_2_Nome();
                                        if (MSShooter2.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter5");
                                        }
                                        String MSShooter3 = rotacao.getLk_Shooter_3_Nome();
                                        if (MSShooter3.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter6");
                                        }
                                        String MSShooter4 = rotacao.getLk_Shooter_4_Nome();
                                        if (MSShooter4.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter7");
                                        }
                                        String MSShooter5 = rotacao.getLk_Shooter_5_Nome();
                                        if (MSShooter5.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter8");
                                        }
                                        String MSShooter6 = rotacao.getLk_Shooter_6_Nome();
                                        if (MSShooter6.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter9");
                                        }
                                        String MSShooter7 = rotacao.getLk_Shooter_7_Nome();
                                        if (MSShooter7.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter10");
                                        }
                                        String MSShooter8 = rotacao.getLk_Shooter_8_Nome();
                                        if (MSShooter8.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter11");
                                        }
                                        break;
                                }
                            }
                            callback.onPosicoesObtidas(posicoesDisponiveis);
                        }
                    }
                }
            });
        }
    }


    public interface PosicoesCallback {
        void onPosicoesObtidas(List<String> posicoes);
    }

    private void atualizarRotacaoComPosicaoDoUsuario(RotacaoQuinzenalLK rotacao, String posicaoSelecionada) {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String userID = user.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference usuarioRef = db.collection("Usuarios").document(userID);

            usuarioRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        String nomePersonagem = document.getString("nomePersonagem");
                        int levelPersonagem = document.getLong("level").intValue();

                        // Agora atualize a rotação com as informações obtidas
                        FirebaseFirestore dbRotacao = FirebaseFirestore.getInstance();
                        DocumentReference rotacaoRef = dbRotacao.collection("RotacaoQuinzenal").document(rotacao.getRotacaoID());

                        // Atualize os campos correspondentes com base na posição selecionada
                        switch (posicaoSelecionada) {
                            case "Blocker1":
                                rotacao.setLk_EK_1_Nome(nomePersonagem);
                                rotacao.setLk_EK_1_Level(levelPersonagem);
                                break;
                            case "Blocker2":
                                rotacao.setLk_EK_2_Nome(nomePersonagem);
                                rotacao.setLk_EK_2_Level(levelPersonagem);
                                break;
                            case "Blocker3":
                                rotacao.setLk_EK_3_Nome(nomePersonagem);
                                rotacao.setLk_EK_3_Level(levelPersonagem);
                                break;
                            case "Healer1":
                                rotacao.setLk_ED_1_Nome(nomePersonagem);
                                rotacao.setLk_ED_1_Level(levelPersonagem);
                                break;
                            case "Shooter1":
                                rotacao.setLk_RP_1_Nome(nomePersonagem);
                                rotacao.setLk_RP_1_Level(levelPersonagem);
                                break;
                            case "Shooter2":
                                rotacao.setLk_RP_2_Nome(nomePersonagem);
                                rotacao.setLk_RP_2_Level(levelPersonagem);
                                break;
                            case "Shooter3":
                                rotacao.setLk_RP_3_Nome(nomePersonagem);
                                rotacao.setLk_RP_3_Level(levelPersonagem);
                                break;
                            case "Shooter4":
                                rotacao.setLk_Shooter_1_Nome(nomePersonagem);
                                rotacao.setLk_Shooter_1_Level(levelPersonagem);
                                break;
                            case "Shooter5":
                                rotacao.setLk_Shooter_2_Nome(nomePersonagem);
                                rotacao.setLk_Shooter_2_Level(levelPersonagem);
                                break;
                            case "Shooter6":
                                rotacao.setLk_Shooter_3_Nome(nomePersonagem);
                                rotacao.setLk_Shooter_3_Level(levelPersonagem);
                                break;
                            case "Shooter7":
                                rotacao.setLk_Shooter_4_Nome(nomePersonagem);
                                rotacao.setLk_Shooter_4_Level(levelPersonagem);
                                break;
                            case "Shooter8":
                                rotacao.setLk_Shooter_5_Nome(nomePersonagem);
                                rotacao.setLk_Shooter_5_Level(levelPersonagem);
                                break;
                            case "Shooter9":
                                rotacao.setLk_Shooter_6_Nome(nomePersonagem);
                                rotacao.setLk_Shooter_6_Level(levelPersonagem);
                                break;
                            case "Shooter10":
                                rotacao.setLk_Shooter_7_Nome(nomePersonagem);
                                rotacao.setLk_Shooter_7_Level(levelPersonagem);
                                break;
                            case "Shooter11":
                                rotacao.setLk_Shooter_8_Nome(nomePersonagem);
                                rotacao.setLk_Shooter_8_Level(levelPersonagem);
                                break;
                        }
                        notifyDataSetChanged();
                        // Faça o update da rotação no Firestore
                        rotacaoRef.set(rotacao)
                                .addOnSuccessListener(aVoid -> Log.d("DEBUG", "Rotação atualizada com sucesso com a nova posição"))
                                .addOnFailureListener(e -> Log.e("ERROR", "Erro ao atualizar a rotação com a nova posição: " + e.getMessage()));
                    } else {
                        Log.d("DEBUG", "Document does not exist");
                    }
                } else {
                    Log.d("DEBUG", "Error getting user document: " + task.getException());
                }
            });
        }
    }
}
