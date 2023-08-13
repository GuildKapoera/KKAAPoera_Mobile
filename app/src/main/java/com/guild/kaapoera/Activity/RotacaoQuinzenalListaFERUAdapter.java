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

public class RotacaoQuinzenalListaFERUAdapter extends RecyclerView.Adapter<RotacaoQuinzenalListaFERUAdapter.ViewHolder> {

    private List<RotacaoQuinzenalFERU> itemList;
    private Context context;
    private FirebaseAuth mAuth;

    public RotacaoQuinzenalListaFERUAdapter(Context context, List<RotacaoQuinzenalFERU> itemList) {
        this.context = context;
        this.itemList = itemList;
        mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rotacaoquinzenal_lista_feru_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RotacaoQuinzenalFERU item = itemList.get(position);
        String dataEHora = "Dia " + item.getData() + " às " + item.getHorario() + "hs";

        holder.txtDataEHora.setText(dataEHora);
        holder.FERU_EKprincipalNome.setText(item.getFeru_EK_1_Nome());
        holder.FERU_EKprincipalLVL.setText(String.valueOf(item.getFeru_EK_1_Level()));
        holder.FERU_EKsecundarioNome.setText(item.getFeru_EK_2_Nome());
        holder.FERU_EKsecundarioLVL.setText(String.valueOf(item.getFeru_EK_2_Level()));
        holder.FERU_EKterciarioNome.setText(item.getFeru_EK_3_Nome());
        holder.FERU_EKterciarioLVL.setText(String.valueOf(item.getFeru_EK_3_Level()));

        holder.FERU_EDprincipalNome.setText(item.getFeru_ED_1_Nome());
        holder.FERU_EDprincipalLVL.setText(String.valueOf(item.getFeru_ED_1_Level()));
        holder.FERU_EDsecundarioNome.setText(item.getFeru_ED_2_Nome());
        holder.FERU_EDsecundarioLVL.setText(String.valueOf(item.getFeru_ED_2_Level()));
        holder.FERU_EDterciarioNome.setText(item.getFeru_ED_3_Nome());
        holder.FERU_EDterciarioLVL.setText(String.valueOf(item.getFeru_ED_3_Level()));

        holder.FERU_Shooter1nome.setText(item.getFeru_Shooter_1_Nome());
        holder.FERU_Shooter1lvl.setText(String.valueOf(item.getFeru_Shooter_1_Level()));
        holder.FERU_Shooter2nome.setText(item.getFeru_Shooter_2_Nome());
        holder.FERU_Shooter2lvl.setText(String.valueOf(item.getFeru_Shooter_2_Level()));
        holder.FERU_Shooter3nome.setText(item.getFeru_Shooter_3_Nome());
        holder.FERU_Shooter3lvl.setText(String.valueOf(item.getFeru_Shooter_3_Level()));
        holder.FERU_Shooter4nome.setText(item.getFeru_Shooter_4_Nome());
        holder.FERU_Shooter4lvl.setText(String.valueOf(item.getFeru_Shooter_4_Level()));
        holder.FERU_Shooter5nome.setText(item.getFeru_Shooter_5_Nome());
        holder.FERU_Shooter5lvl.setText(String.valueOf(item.getFeru_Shooter_5_Level()));
        holder.FERU_Shooter6nome.setText(item.getFeru_Shooter_6_Nome());
        holder.FERU_Shooter6lvl.setText(String.valueOf(item.getFeru_Shooter_6_Level()));
        holder.FERU_Shooter7nome.setText(item.getFeru_Shooter_7_Nome());
        holder.FERU_Shooter7lvl.setText(String.valueOf(item.getFeru_Shooter_7_Level()));
        holder.FERU_Shooter8nome.setText(item.getFeru_Shooter_8_Nome());
        holder.FERU_Shooter8lvl.setText(String.valueOf(item.getFeru_Shooter_8_Level()));
        holder.FERU_Shooter9nome.setText(item.getFeru_Shooter_9_Nome());
        holder.FERU_Shooter9lvl.setText(String.valueOf(item.getFeru_Shooter_9_Level()));



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

                            boolean usuarioJaEstaNaPT = item.usuarioEstaNaRotacaoFERU(nomePersonagem);

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
                                                    String[] posicoes = {"feru_EK_1_Nome", "feru_EK_2_Nome", "feru_EK_3_Nome", "feru_ED_1_Nome", "feru_ED_2_Nome", "feru_ED_3_Nome", "feru_Shooter_1_Nome", "feru_Shooter_2_Nome", "feru_Shooter_3_Nome", "feru_Shooter_4_Nome", "feru_Shooter_5_Nome", "feru_Shooter_6_Nome", "feru_Shooter_7_Nome", "feru_Shooter_8_Nome", "feru_Shooter_9_Nome"};
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
                                                                        if ((posicao.equals("feru_EK_1_Nome"))) {
                                                                            item.setFeru_EK_1_Nome("");
                                                                            item.setFeru_EK_1_Level(0);} else { }
                                                                        if ((posicao.equals("feru_EK_2_Nome"))) {
                                                                            item.setFeru_EK_2_Nome("");
                                                                            item.setFeru_EK_2_Level(0);} else { }
                                                                        if ((posicao.equals("feru_EK_3_Nome"))) {
                                                                            item.setFeru_EK_3_Nome("");
                                                                            item.setFeru_EK_3_Level(0);} else { }
                                                                        if ((posicao.equals("feru_ED_1_Nome"))) {
                                                                            item.setFeru_ED_1_Nome("");
                                                                            item.setFeru_ED_1_Level(0);} else { }
                                                                        if ((posicao.equals("feru_ED_2_Nome"))) {
                                                                            item.setFeru_ED_2_Nome("");
                                                                            item.setFeru_ED_2_Level(0);} else { }
                                                                        if ((posicao.equals("feru_ED_3_Nome"))) {
                                                                            item.setFeru_ED_3_Nome("");
                                                                            item.setFeru_ED_3_Level(0);} else { }
                                                                        if ((posicao.equals("feru_Shooter_1_Nome"))) {
                                                                            item.setFeru_Shooter_1_Nome("");
                                                                            item.setFeru_Shooter_1_Level(0);} else { }
                                                                        if ((posicao.equals("feru_Shooter_2_Nome"))) {
                                                                            item.setFeru_Shooter_2_Nome("");
                                                                            item.setFeru_Shooter_2_Level(0);} else { }
                                                                        if ((posicao.equals("feru_Shooter_3_Nome"))) {
                                                                            item.setFeru_Shooter_3_Nome("");
                                                                            item.setFeru_Shooter_3_Level(0);} else { }
                                                                        if ((posicao.equals("feru_Shooter_4_Nome"))) {
                                                                            item.setFeru_Shooter_4_Nome("");
                                                                            item.setFeru_Shooter_4_Level(0);} else { }
                                                                        if ((posicao.equals("feru_Shooter_5_Nome"))) {
                                                                            item.setFeru_Shooter_5_Nome("");
                                                                            item.setFeru_Shooter_5_Level(0);} else { }
                                                                        if ((posicao.equals("feru_Shooter_6_Nome"))) {
                                                                            item.setFeru_Shooter_6_Nome("");
                                                                            item.setFeru_Shooter_6_Level(0);} else { }
                                                                        if ((posicao.equals("feru_Shooter_7_Nome"))) {
                                                                            item.setFeru_Shooter_7_Nome("");
                                                                            item.setFeru_Shooter_7_Level(0);} else { }
                                                                        if ((posicao.equals("feru_Shooter_8_Nome"))) {
                                                                            item.setFeru_Shooter_8_Nome("");
                                                                            item.setFeru_Shooter_8_Level(0);} else { }
                                                                        if ((posicao.equals("feru_Shooter_9_Nome"))) {
                                                                            item.setFeru_Shooter_9_Nome("");
                                                                            item.setFeru_Shooter_9_Level(0);} else { }


                                                                        // Atualização bem-sucedida
                                                                        Toast.makeText(context, "Você saiu da Rotação do Feru.", Toast.LENGTH_SHORT).show();
                                                                        notifyDataSetChanged();
                                                                    } else {
                                                                        // Tratar o erro na atualização
                                                                        Toast.makeText(context, "Erro ao sair da Rotação do Feru.", Toast.LENGTH_SHORT).show();
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
        TextView FERU_EKprincipalNome;
        TextView FERU_EKprincipalLVL;
        TextView FERU_EKsecundarioNome;
        TextView FERU_EKsecundarioLVL;
        TextView FERU_EKterciarioNome;
        TextView FERU_EKterciarioLVL;

        TextView FERU_EDprincipalNome;
        TextView FERU_EDprincipalLVL;
        TextView FERU_EDsecundarioNome;
        TextView FERU_EDsecundarioLVL;
        TextView FERU_EDterciarioNome;
        TextView FERU_EDterciarioLVL;

        TextView FERU_Shooter1nome;
        TextView FERU_Shooter1lvl;
        TextView FERU_Shooter2nome;
        TextView FERU_Shooter2lvl;
        TextView FERU_Shooter3nome;
        TextView FERU_Shooter3lvl;
        TextView FERU_Shooter4nome;
        TextView FERU_Shooter4lvl;
        TextView FERU_Shooter5nome;
        TextView FERU_Shooter5lvl;
        TextView FERU_Shooter6nome;
        TextView FERU_Shooter6lvl;
        TextView FERU_Shooter7nome;
        TextView FERU_Shooter7lvl;
        TextView FERU_Shooter8nome;
        TextView FERU_Shooter8lvl;
        TextView FERU_Shooter9nome;
        TextView FERU_Shooter9lvl;


        Button btnEntrarNaRotacao;
        Button btnSairDaRotacao;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDataEHora = itemView.findViewById(R.id.txtDataEHora);
            FERU_EKprincipalNome = itemView.findViewById(R.id.FERU_EKprincipalNome);
            FERU_EKprincipalLVL = itemView.findViewById(R.id.FERU_EKprincipalLVL);
            FERU_EKsecundarioNome = itemView.findViewById(R.id.FERU_EKsecundarioNome);
            FERU_EKsecundarioLVL = itemView.findViewById(R.id.FERU_EKsecundarioLVL);
            FERU_EKterciarioNome = itemView.findViewById(R.id.FERU_EKterciarioNome);
            FERU_EKterciarioLVL = itemView.findViewById(R.id.FERU_EKterciarioLVL);

            FERU_EDprincipalNome = itemView.findViewById(R.id.FERU_EDprincipalNome);
            FERU_EDprincipalLVL = itemView.findViewById(R.id.FERU_EDprincipalLVL);
            FERU_EDsecundarioNome = itemView.findViewById(R.id.FERU_EDsecundarioNome);
            FERU_EDsecundarioLVL = itemView.findViewById(R.id.FERU_EDsecundarioLVL);
            FERU_EDterciarioNome = itemView.findViewById(R.id.FERU_EDterciarioNome);
            FERU_EDterciarioLVL = itemView.findViewById(R.id.FERU_EDterciarioLVL);

            FERU_Shooter1nome = itemView.findViewById(R.id.FERU_Shooter1nome);
            FERU_Shooter1lvl = itemView.findViewById(R.id.FERU_Shooter1LVL);
            FERU_Shooter2nome = itemView.findViewById(R.id.FERU_Shooter2nome);
            FERU_Shooter2lvl = itemView.findViewById(R.id.FERU_Shooter2LVL);
            FERU_Shooter3nome = itemView.findViewById(R.id.FERU_Shooter3nome);
            FERU_Shooter3lvl = itemView.findViewById(R.id.FERU_Shooter3LVL);
            FERU_Shooter4nome = itemView.findViewById(R.id.FERU_Shooter4nome);
            FERU_Shooter4lvl = itemView.findViewById(R.id.FERU_Shooter4LVL);
            FERU_Shooter5nome = itemView.findViewById(R.id.FERU_Shooter5nome);
            FERU_Shooter5lvl = itemView.findViewById(R.id.FERU_Shooter5LVL);
            FERU_Shooter6nome = itemView.findViewById(R.id.FERU_Shooter6nome);
            FERU_Shooter6lvl = itemView.findViewById(R.id.FERU_Shooter6LVL);
            FERU_Shooter7nome = itemView.findViewById(R.id.FERU_Shooter7nome);
            FERU_Shooter7lvl = itemView.findViewById(R.id.FERU_Shooter7LVL);
            FERU_Shooter8nome = itemView.findViewById(R.id.FERU_Shooter8nome);
            FERU_Shooter8lvl = itemView.findViewById(R.id.FERU_Shooter8LVL);
            FERU_Shooter9nome = itemView.findViewById(R.id.FERU_Shooter9nome);
            FERU_Shooter9lvl = itemView.findViewById(R.id.FERU_Shooter9LVL);


            btnEntrarNaRotacao = itemView.findViewById(R.id.btnEntrarNaRotacao);
            btnSairDaRotacao = itemView.findViewById(R.id.btnSairDaRotacao);

        }
    }

    private void obterPosicoesDisponiveisParaVocacaoDoUsuario(RotacaoQuinzenalFERU rotacao, PosicoesCallback callback) {
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
                            Log.d("DEBUG", "Vocação do usuário: " + vocacaoUsuario);

                            if (vocacaoUsuario != null) {
                                switch (vocacaoUsuario) {
                                    case "Elder Druid":
                                        String Healer1 = rotacao.getFeru_ED_1_Nome();
                                        if (Healer1.isEmpty()) {
                                            posicoesDisponiveis.add("Healer1");
                                        }
                                        String Healer2 = rotacao.getFeru_ED_2_Nome();
                                        if (Healer2.isEmpty()) {
                                            posicoesDisponiveis.add("Healer2");
                                        }
                                        String Healer3 = rotacao.getFeru_ED_3_Nome();
                                        if (Healer3.isEmpty()) {
                                            posicoesDisponiveis.add("Healer3");
                                        }
                                        String EDShooter1 = rotacao.getFeru_Shooter_1_Nome();
                                        if (EDShooter1.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter1");
                                        }
                                        String EDShooter2 = rotacao.getFeru_Shooter_2_Nome();
                                        if (EDShooter2.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter2");
                                        }
                                        String EDShooter3 = rotacao.getFeru_Shooter_3_Nome();
                                        if (EDShooter3.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter3");
                                        }
                                        String EDShooter4 = rotacao.getFeru_Shooter_4_Nome();
                                        if (EDShooter4.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter4");
                                        }
                                        String EDShooter5 = rotacao.getFeru_Shooter_5_Nome();
                                        if (EDShooter5.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter5");
                                        }
                                        String EDShooter6 = rotacao.getFeru_Shooter_6_Nome();
                                        if (EDShooter6.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter6");
                                        }
                                        String EDShooter7 = rotacao.getFeru_Shooter_7_Nome();
                                        if (EDShooter7.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter7");
                                        }
                                        String EDShooter8 = rotacao.getFeru_Shooter_8_Nome();
                                        if (EDShooter8.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter8");
                                        }
                                        String EDShooter9 = rotacao.getFeru_Shooter_9_Nome();
                                        if (EDShooter9.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter9");
                                        }
                                        break;
                                    case "Elite Knight":
                                        String Blocker1 = rotacao.getFeru_EK_1_Nome();
                                        if (Blocker1.isEmpty()) {
                                            posicoesDisponiveis.add("Blocker1");
                                        }
                                        String Blocker2 = rotacao.getFeru_EK_2_Nome();
                                        if (Blocker2.isEmpty()) {
                                            posicoesDisponiveis.add("Blocker2");
                                        }
                                        String Blocker3 = rotacao.getFeru_EK_3_Nome();
                                        if (Blocker3.isEmpty()) {
                                            posicoesDisponiveis.add("Blocker3");
                                        }
                                        String EKShooter1 = rotacao.getFeru_Shooter_1_Nome();
                                        if (EKShooter1.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter1");
                                        }
                                        String EKShooter2 = rotacao.getFeru_Shooter_2_Nome();
                                        if (EKShooter2.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter2");
                                        }
                                        String EKShooter3 = rotacao.getFeru_Shooter_3_Nome();
                                        if (EKShooter3.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter3");
                                        }
                                        break;
                                    case "Royal Paladin":
                                    case "Master Sorcerer":
                                        String RPShooter1 = rotacao.getFeru_Shooter_1_Nome();
                                        if (RPShooter1.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter1");
                                        }
                                        String RPShooter2 = rotacao.getFeru_Shooter_2_Nome();
                                        if (RPShooter2.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter2");
                                        }
                                        String RPShooter3 = rotacao.getFeru_Shooter_3_Nome();
                                        if (RPShooter3.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter3");
                                        }
                                        String RPShooter4 = rotacao.getFeru_Shooter_4_Nome();
                                        if (RPShooter4.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter4");
                                        }
                                        String RPShooter5 = rotacao.getFeru_Shooter_5_Nome();
                                        if (RPShooter5.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter5");
                                        }
                                        String RPShooter6 = rotacao.getFeru_Shooter_6_Nome();
                                        if (RPShooter6.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter6");
                                        }
                                        String RPShooter7 = rotacao.getFeru_Shooter_7_Nome();
                                        if (RPShooter7.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter7");
                                        }
                                        String RPShooter8 = rotacao.getFeru_Shooter_8_Nome();
                                        if (RPShooter8.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter8");
                                        }
                                        String RPShooter9 = rotacao.getFeru_Shooter_9_Nome();
                                        if (RPShooter9.isEmpty()) {
                                            posicoesDisponiveis.add("Shooter9");

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

    private void atualizarRotacaoComPosicaoDoUsuario(RotacaoQuinzenalFERU rotacao, String posicaoSelecionada) {
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
                                rotacao.setFeru_EK_1_Nome(nomePersonagem);
                                rotacao.setFeru_EK_1_Level(levelPersonagem);
                                break;
                            case "Blocker2":
                                rotacao.setFeru_EK_2_Nome(nomePersonagem);
                                rotacao.setFeru_EK_2_Level(levelPersonagem);
                                break;
                            case "Blocker3":
                                rotacao.setFeru_EK_3_Nome(nomePersonagem);
                                rotacao.setFeru_EK_3_Level(levelPersonagem);
                                break;
                            case "Healer1":
                                rotacao.setFeru_ED_1_Nome(nomePersonagem);
                                rotacao.setFeru_ED_1_Level(levelPersonagem);
                                break;
                            case "Healer2":
                                rotacao.setFeru_ED_2_Nome(nomePersonagem);
                                rotacao.setFeru_ED_2_Level(levelPersonagem);
                                break;
                            case "Healer3":
                                rotacao.setFeru_ED_3_Nome(nomePersonagem);
                                rotacao.setFeru_ED_3_Level(levelPersonagem);
                                break;
                            case "Shooter1":
                                rotacao.setFeru_Shooter_1_Nome(nomePersonagem);
                                rotacao.setFeru_Shooter_1_Level(levelPersonagem);
                                break;
                            case "Shooter2":
                                rotacao.setFeru_Shooter_2_Nome(nomePersonagem);
                                rotacao.setFeru_Shooter_2_Level(levelPersonagem);
                                break;
                            case "Shooter3":
                                rotacao.setFeru_Shooter_3_Nome(nomePersonagem);
                                rotacao.setFeru_Shooter_3_Level(levelPersonagem);
                                break;
                            case "Shooter4":
                                rotacao.setFeru_Shooter_4_Nome(nomePersonagem);
                                rotacao.setFeru_Shooter_4_Level(levelPersonagem);
                                break;
                            case "Shooter5":
                                rotacao.setFeru_Shooter_5_Nome(nomePersonagem);
                                rotacao.setFeru_Shooter_5_Level(levelPersonagem);
                                break;
                            case "Shooter6":
                                rotacao.setFeru_Shooter_6_Nome(nomePersonagem);
                                rotacao.setFeru_Shooter_6_Level(levelPersonagem);
                                break;
                            case "Shooter7":
                                rotacao.setFeru_Shooter_7_Nome(nomePersonagem);
                                rotacao.setFeru_Shooter_7_Level(levelPersonagem);
                                break;
                            case "Shooter8":
                                rotacao.setFeru_Shooter_8_Nome(nomePersonagem);
                                rotacao.setFeru_Shooter_8_Level(levelPersonagem);
                                break;
                            case "Shooter9":
                                rotacao.setFeru_Shooter_9_Nome(nomePersonagem);
                                rotacao.setFeru_Shooter_9_Level(levelPersonagem);


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

