package com.guild.kaapoera.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.guild.kaapoera.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QueroPTGeralAdapter extends RecyclerView.Adapter<QueroPTGeralAdapter.ViewHolder> {

    private Context context;
    private List<QueroPTGeral> queroPTList;
    private FirebaseAuth mAuth;


    public QueroPTGeralAdapter(Context context, List<QueroPTGeral> queroPTList) {
        this.context = context;
        this.queroPTList = queroPTList;
        mAuth = FirebaseAuth.getInstance();

    }

    private void configureImageView(TextView nameTextView, ImageView checkImageView) {
        if (nameTextView.getText().toString().isEmpty()) {
            checkImageView.setVisibility(View.GONE);
        } else {
            checkImageView.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quero_pt_geral_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QueroPTGeral queroPT = queroPTList.get(position);

        // Aqui, você deve definir os valores nos elementos da view do item da RecyclerView
        // com base nos dados do objeto QueroPTGeral

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

        //Checks de vaga no Status
        configureImageView(holder.txtEKnome, holder.checkEKicon);
        configureImageView(holder.txtEDnome, holder.checkEDicon);
        configureImageView(holder.txtRPnome, holder.checkRPicon);
        configureImageView(holder.txtMSnome, holder.checkMSicon);

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

                            // Verificar se o usuário é o responsável pela PT
                            boolean usuarioEhResponsavel = queroPT.getResponsavel().equals(nomePersonagem);

                            // Verificar se o usuário já está na PT
                            boolean usuarioJaEstaNaPT = queroPT.usuarioEstaNaPT(nomePersonagem);

                            // Configurar a visibilidade dos botões com base nas verificações acima
                            if (usuarioEhResponsavel) {
                                holder.btnEntrarNaPT.setVisibility(View.GONE);
                                holder.btnSairDaPT.setVisibility(View.GONE);
                            } else if (usuarioJaEstaNaPT) {
                                holder.btnEntrarNaPT.setVisibility(View.GONE);
                                holder.btnSairDaPT.setVisibility(View.VISIBLE);
                            } else {
                                holder.btnEntrarNaPT.setVisibility(View.VISIBLE);
                                holder.btnSairDaPT.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });
        } else {
            // Usuário não está logado, defina a visibilidade padrão dos botões
            holder.btnEntrarNaPT.setVisibility(View.VISIBLE);
            holder.btnSairDaPT.setVisibility(View.GONE);
        }

        holder.btnSairDaPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar se o usuário está logado
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    // O usuário está logado, podemos prosseguir com a obtenção do UID
                    String uidDoUsuario = user.getUid();
                    // Obter a referência do documento do usuário no Firestore
                    DocumentReference userDocRef = FirebaseFirestore.getInstance().collection("Usuarios").document(uidDoUsuario);

                    // Realizar a leitura do documento do usuário
                    userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document != null && document.exists()) {
                                    // O documento do usuário foi encontrado, agora podemos obter as informações necessárias
                                    String nomePersonagem = document.getString("nomePersonagem");
                                    String vocacao = document.getString("vocacao");
                                    int level = document.getLong("level").intValue(); // Supondo que o campo level é armazenado como Long no Firestore.

                                    // Verificar a vocação do usuário e as condições para sair da PT
                                    if (vocacao != null) {
                                        switch (vocacao) {
                                            case "Elite Knight":
                                                // Verificar se o usuário está na PT
                                                if (queroPT.usuarioEstaNaPT(nomePersonagem)) {
                                                    // Atualizar os campos da PT com as informações do usuário
                                                    queroPT.setEKnome("");
                                                    queroPT.setEKcontato("");
                                                    queroPT.setEKlevel(0);
                                                    queroPT.setEKcod("");

                                                    // Atualizar campos de níveis máximo e mínimo
                                                    int menorLevelRestante = Integer.MAX_VALUE;
                                                    int maiorLevelRestante = 0;

                                                    if (queroPT.getRPlevel() > 0) {
                                                        menorLevelRestante = Math.min(menorLevelRestante, queroPT.getRPlevel());
                                                        maiorLevelRestante = Math.max(maiorLevelRestante, queroPT.getRPlevel());
                                                    } else { }

                                                    if (queroPT.getMSlevel() > 0) {
                                                        menorLevelRestante = Math.min(menorLevelRestante, queroPT.getMSlevel());
                                                        maiorLevelRestante = Math.max(maiorLevelRestante, queroPT.getMSlevel());
                                                    } else { }

                                                    if (queroPT.getEDlevel() > 0) {
                                                        menorLevelRestante = Math.min(menorLevelRestante, queroPT.getEDlevel());
                                                        maiorLevelRestante = Math.max(maiorLevelRestante, queroPT.getEDlevel());
                                                    } else { }

                                                    // Calcular os novos valores para levelMinimo e levelMaximo
                                                    int novoLevelMinimo = (maiorLevelRestante * 2) / 3;
                                                    int novoLevelMaximo = (menorLevelRestante * 3) / 2;

                                                    // Atualizar os campos de LevelMinimo e LevelMaximo no objeto queroPT
                                                    queroPT.setLevelMinimo(novoLevelMinimo);
                                                    queroPT.setLevelMaximo(novoLevelMaximo);

                                                    // Atualizar a PT no Firestore
                                                    DocumentReference ptDocRef = FirebaseFirestore.getInstance().collection("QueroPT").document(queroPT.getPTid());
                                                    ptDocRef.update(
                                                            "EKnome", queroPT.getEKnome(),
                                                            "EKcod",queroPT.getEKcod(),
                                                            "EKcontato", queroPT.getEKcontato(),
                                                            "EKlevel", queroPT.getEKlevel(),
                                                            "LevelMinimo", queroPT.getLevelMinimo(),
                                                            "LevelMaximo", queroPT.getLevelMaximo()
                                                    ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            // Sucesso ao sair da PT
                                                            Toast.makeText(context, "Você saiu da PT!", Toast.LENGTH_SHORT).show();
                                                            notifyItemChanged(position);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            // Erro ao sair da PT
                                                            Toast.makeText(context, "Erro ao sair da PT. Tente novamente.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                } else {
                                                    // O usuário não está na PT, exibir mensagem apropriada
                                                    Toast.makeText(context, "Você não está na PT.", Toast.LENGTH_SHORT).show();
                                                }
                                                break;

                                            case "Royal Paladin":
                                                // Verificar se o usuário está na PT
                                                if (queroPT.usuarioEstaNaPT(nomePersonagem)) {
                                                    // Atualizar os campos da PT com as informações do usuário
                                                    queroPT.setRPnome("");
                                                    queroPT.setRPcod("");
                                                    queroPT.setRPcontato("");
                                                    queroPT.setRPlevel(0);

                                                    // Atualizar campos de níveis máximo e mínimo
                                                    int menorLevelRestante = Integer.MAX_VALUE;
                                                    int maiorLevelRestante = 0;

                                                    // Verificar os níveis dos participantes restantes
                                                    if (queroPT.getEKlevel() > 0) {
                                                        menorLevelRestante = Math.min(menorLevelRestante, queroPT.getEKlevel());
                                                        maiorLevelRestante = Math.max(maiorLevelRestante, queroPT.getEKlevel());
                                                    } else { }

                                                    if (queroPT.getMSlevel() > 0) {
                                                        menorLevelRestante = Math.min(menorLevelRestante, queroPT.getMSlevel());
                                                        maiorLevelRestante = Math.max(maiorLevelRestante, queroPT.getMSlevel());
                                                    } else { }

                                                    if (queroPT.getEDlevel() > 0) {
                                                        menorLevelRestante = Math.min(menorLevelRestante, queroPT.getEDlevel());
                                                        maiorLevelRestante = Math.max(maiorLevelRestante, queroPT.getEDlevel());
                                                    } else { }

                                                    // Calcular os novos valores para levelMinimo e levelMaximo
                                                    int novoLevelMinimo = (maiorLevelRestante * 2) / 3;
                                                    int novoLevelMaximo = (menorLevelRestante * 3) / 2;

                                                    // Atualizar os campos de LevelMinimo e LevelMaximo no objeto queroPT
                                                    queroPT.setLevelMinimo(novoLevelMinimo);
                                                    queroPT.setLevelMaximo(novoLevelMaximo);

                                                    // Atualizar a PT no Firestore
                                                    DocumentReference ptDocRef = FirebaseFirestore.getInstance().collection("QueroPT").document(queroPT.getPTid());
                                                    ptDocRef.update(
                                                            "RPnome", queroPT.getRPnome(),
                                                            "RPcod", queroPT.getRPcod(),
                                                            "RPcontato", queroPT.getRPcontato(),
                                                            "RPlevel", queroPT.getRPlevel(),
                                                            "LevelMinimo", queroPT.getLevelMinimo(),
                                                            "LevelMaximo", queroPT.getLevelMaximo()
                                                    ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            // Sucesso ao sair da PT
                                                            Toast.makeText(context, "Você saiu da PT!", Toast.LENGTH_SHORT).show();
                                                            notifyItemChanged(position);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            // Erro ao sair da PT
                                                            Toast.makeText(context, "Erro ao sair da PT. Tente novamente.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                } else {
                                                    // O usuário não está na PT, exibir mensagem apropriada
                                                    Toast.makeText(context, "Você não está na PT.", Toast.LENGTH_SHORT).show();
                                                }
                                                break;

                                            case "Elder Druid":
                                                // Verificar se o usuário está na PT
                                                if (queroPT.usuarioEstaNaPT(nomePersonagem)) {
                                                    // Atualizar os campos da PT com as informações do usuário
                                                    queroPT.setEDnome("");
                                                    queroPT.setEDcod("");
                                                    queroPT.setEDcontato("");
                                                    queroPT.setEDlevel(0);

                                                    // Atualizar campos de níveis máximo e mínimo
                                                    int menorLevelRestante = Integer.MAX_VALUE;
                                                    int maiorLevelRestante = 0;

                                                    // Verificar os níveis dos participantes restantes
                                                    if (queroPT.getEKlevel() > 0) {
                                                        menorLevelRestante = Math.min(menorLevelRestante, queroPT.getEKlevel());
                                                        maiorLevelRestante = Math.max(maiorLevelRestante, queroPT.getEKlevel());
                                                    } else { }

                                                    if (queroPT.getMSlevel() > 0) {
                                                        menorLevelRestante = Math.min(menorLevelRestante, queroPT.getMSlevel());
                                                        maiorLevelRestante = Math.max(maiorLevelRestante, queroPT.getMSlevel());
                                                    } else { }

                                                    if (queroPT.getRPlevel() > 0) {
                                                        menorLevelRestante = Math.min(menorLevelRestante, queroPT.getRPlevel());
                                                        maiorLevelRestante = Math.max(maiorLevelRestante, queroPT.getRPlevel());
                                                    } else { }

                                                    // Calcular os novos valores para levelMinimo e levelMaximo
                                                    int novoLevelMinimo = (maiorLevelRestante * 2) / 3;
                                                    int novoLevelMaximo = (menorLevelRestante * 3) / 2;

                                                    // Atualizar os campos de LevelMinimo e LevelMaximo no objeto queroPT
                                                    queroPT.setLevelMinimo(novoLevelMinimo);
                                                    queroPT.setLevelMaximo(novoLevelMaximo);

                                                    // Atualizar a PT no Firestore
                                                    DocumentReference ptDocRef = FirebaseFirestore.getInstance().collection("QueroPT").document(queroPT.getPTid());
                                                    ptDocRef.update(
                                                            "EDnome", queroPT.getEDnome(),
                                                            "EDcod", queroPT.getEDcod(),
                                                            "EDcontato", queroPT.getEDcontato(),
                                                            "EDlevel", queroPT.getEDlevel(),
                                                            "LevelMinimo", queroPT.getLevelMinimo(),
                                                            "LevelMaximo", queroPT.getLevelMaximo()
                                                    ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            // Sucesso ao sair da PT
                                                            Toast.makeText(context, "Você saiu da PT!", Toast.LENGTH_SHORT).show();
                                                            notifyItemChanged(position);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            // Erro ao sair da PT
                                                            Toast.makeText(context, "Erro ao sair da PT. Tente novamente.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                } else {
                                                    // O usuário não está na PT, exibir mensagem apropriada
                                                    Toast.makeText(context, "Você não está na PT.", Toast.LENGTH_SHORT).show();
                                                }
                                                break;

                                            case "Master Sorcerer":
                                                // Verificar se o usuário está na PT
                                                if (queroPT.usuarioEstaNaPT(nomePersonagem)) {
                                                    // Atualizar os campos da PT com as informações do usuário
                                                    queroPT.setMSnome("");
                                                    queroPT.setMScod("");
                                                    queroPT.setMScontato("");
                                                    queroPT.setMSlevel(0);

                                                    // Atualizar campos de níveis máximo e mínimo
                                                    int menorLevelRestante = Integer.MAX_VALUE;
                                                    int maiorLevelRestante = 0;

                                                    // Verificar os níveis dos participantes restantes
                                                    if (queroPT.getEKlevel() > 0) {
                                                        menorLevelRestante = Math.min(menorLevelRestante, queroPT.getEKlevel());
                                                        maiorLevelRestante = Math.max(maiorLevelRestante, queroPT.getEKlevel());
                                                    } else { }

                                                    if (queroPT.getMSlevel() > 0) {
                                                        menorLevelRestante = Math.min(menorLevelRestante, queroPT.getRPlevel());
                                                        maiorLevelRestante = Math.max(maiorLevelRestante, queroPT.getRPlevel());
                                                    } else { }

                                                    if (queroPT.getEDlevel() > 0) {
                                                        menorLevelRestante = Math.min(menorLevelRestante, queroPT.getEDlevel());
                                                        maiorLevelRestante = Math.max(maiorLevelRestante, queroPT.getEDlevel());
                                                    } else { }

                                                    // Calcular os novos valores para levelMinimo e levelMaximo
                                                    int novoLevelMinimo = (maiorLevelRestante * 2) / 3;
                                                    int novoLevelMaximo = (menorLevelRestante * 3) / 2;

                                                    // Atualizar os campos de LevelMinimo e LevelMaximo no objeto queroPT
                                                    queroPT.setLevelMinimo(novoLevelMinimo);
                                                    queroPT.setLevelMaximo(novoLevelMaximo);

                                                    // Atualizar a PT no Firestore
                                                    DocumentReference ptDocRef = FirebaseFirestore.getInstance().collection("QueroPT").document(queroPT.getPTid());
                                                    ptDocRef.update(
                                                            "MSnome", queroPT.getMSnome(),
                                                            "MScod", queroPT.getMScod(),
                                                            "MScontato", queroPT.getMScontato(),
                                                            "MSlevel", queroPT.getMSlevel(),
                                                            "LevelMinimo", queroPT.getLevelMinimo(),
                                                            "LevelMaximo", queroPT.getLevelMaximo()
                                                    ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            // Sucesso ao sair da PT
                                                            Toast.makeText(context, "Você saiu da PT!", Toast.LENGTH_SHORT).show();
                                                            notifyItemChanged(position);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            // Erro ao sair da PT
                                                            Toast.makeText(context, "Erro ao sair da PT. Tente novamente.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                } else {
                                                    // O usuário não está na PT, exibir mensagem apropriada
                                                    Toast.makeText(context, "Você não está na PT.", Toast.LENGTH_SHORT).show();
                                                }
                                                break;

                                            default:
                                                // A vocação do usuário não corresponde a nenhuma das opções válidas
                                                // Exibir mensagem de erro
                                                Toast.makeText(context, "Vocação não suportada.", Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });

        /// Configurar o clique no botão "Entrar na PT"
        holder.btnEntrarNaPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar se o usuário está logado
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    // O usuário está logado, podemos prosseguir com a obtenção do UID
                    String uidDoUsuario = user.getUid();
                    // Agora você tem o UID do usuário e pode prosseguir com suas operações
                    // Obter a referência do documento do usuário no Firestore
                    DocumentReference userDocRef = FirebaseFirestore.getInstance().collection("Usuarios").document(uidDoUsuario);

                    // Realizar a leitura do documento do usuário
                    userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document != null && document.exists()) {
                                    // O documento do usuário foi encontrado, agora podemos obter as informações necessárias
                                    String nomePersonagem = document.getString("nomePersonagem");
                                    String vocacao = document.getString("vocacao");
                                    String codPais = document.getString("codPais");
                                    String telefone = document.getString("telefone");
                                    int level = document.getLong("level").intValue(); // Supondo que o campo level é armazenado como Long no Firestore.

                                    // Verificar a vocação do usuário e as condições para entrar na PT
                                    if (vocacao != null) {
                                        switch (vocacao) {
                                            case "Elite Knight":
                                                // Verificar se há vaga para Elite Knight na PT
                                                if (queroPT.getEKnome().isEmpty() && level >= queroPT.getLevelMinimo() && level <= queroPT.getLevelMaximo()) {
                                                    // Calcular os novos valores para levelMinimo e levelMaximo
                                                    int novoLevelMinimo = (level * 2) / 3;
                                                    int novoLevelMaximo = (level * 3) / 2;

                                                    // Verificar se os novos valores são maiores que os atuais e, se sim, atualizar os campos no objeto queroPT
                                                    if (novoLevelMinimo > queroPT.getLevelMinimo()) {
                                                        queroPT.setLevelMinimo(novoLevelMinimo);
                                                    } else {  }
                                                    if (novoLevelMaximo < queroPT.getLevelMaximo()) {
                                                        queroPT.setLevelMaximo(novoLevelMaximo);
                                                    } else {  }

                                                    // Atualizar os campos da PT com as informações do usuário
                                                    queroPT.setEKnome(nomePersonagem);
                                                    queroPT.setEKcod(codPais);
                                                    queroPT.setEKcontato(telefone);
                                                    queroPT.setEKlevel(level);

                                                    //Obtendo Data atual e formatnaod para dd/MM/aaaa
                                                    Date dataAtual = new Date();
                                                    SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
                                                    String data = dataFormatada.format(dataAtual);
                                                    queroPT.setData(data);

                                                    // Agora você pode prosseguir com a atualização no Firestore
                                                    DocumentReference ptDocRef = FirebaseFirestore.getInstance().collection("QueroPT").document(queroPT.getPTid());
                                                    ptDocRef.update(
                                                                    "data", queroPT.getData(),
                                                                    "EKnome", queroPT.getEKnome(),
                                                                    "EKcod", queroPT.getEKcod(),
                                                                    "EKcontato", queroPT.getEKcontato(),
                                                                    "EKlevel", queroPT.getEKlevel(),
                                                                    "LevelMinimo", queroPT.getLevelMinimo(),
                                                                    "LevelMaximo", queroPT.getLevelMaximo()
                                                                    // Adicione aqui os outros campos para as outras vocações, se necessário.
                                                            )
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    // Sucesso ao atualizar os dados da PT
                                                                    Toast.makeText(context, "Você entrou na PT!", Toast.LENGTH_SHORT).show();
                                                                    notifyItemChanged(position);

                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    // Erro ao atualizar os dados da PT
                                                                    Toast.makeText(context, "Erro ao entrar na PT. Tente novamente.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                } else {
                                                    // Não há vaga para Elite Knight na PT ou o usuário não atende às condições
                                                    // Exiba uma mensagem ao usuário informando que ele não pode entrar na PT.
                                                    Toast.makeText(context, "Não há vaga para sua vocação ou level fora do limite", Toast.LENGTH_SHORT).show();
                                                }
                                                break;
                                            case "Royal Paladin":
                                                // Verificar se há vaga para Royal Paladin na PT
                                                if (queroPT.getRPnome().isEmpty() && level >= queroPT.getLevelMinimo() && level <= queroPT.getLevelMaximo()) {
                                                    // Calcular os novos valores para levelMinimo e levelMaximo
                                                    int novoLevelMinimo = (level * 2) / 3;
                                                    int novoLevelMaximo = (level * 3) / 2;

                                                    // Verificar se os novos valores são maiores que os atuais e, se sim, atualizar os campos no objeto queroPT
                                                    if (novoLevelMinimo > queroPT.getLevelMinimo()) {
                                                        queroPT.setLevelMinimo(novoLevelMinimo);
                                                    } else {  }
                                                    if (novoLevelMaximo < queroPT.getLevelMaximo()) {
                                                        queroPT.setLevelMaximo(novoLevelMaximo);
                                                    } else { }

                                                    // Atualizar os campos da PT com as informações do usuário
                                                    queroPT.setRPnome(nomePersonagem);
                                                    queroPT.setRPcod(codPais);
                                                    queroPT.setRPcontato(telefone);
                                                    queroPT.setRPlevel(level);

                                                    //Obtendo Data atual e formatnaod para dd/MM/aaaa
                                                    Date dataAtual = new Date();
                                                    SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
                                                    String data = dataFormatada.format(dataAtual);
                                                    queroPT.setData(data);

                                                    // Agora você pode prosseguir com a atualização no Firestore
                                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                    DocumentReference ptDocRef = db.collection("QueroPT").document(queroPT.getPTid());
                                                    ptDocRef.update(
                                                                    "data", queroPT.getData(),
                                                                    "RPnome", queroPT.getRPnome(),
                                                                    "RPcod", queroPT.getRPcod(),
                                                                    "RPcontato", queroPT.getRPcontato(),
                                                                    "RPlevel", queroPT.getRPlevel(),
                                                                    "LevelMinimo", queroPT.getLevelMinimo(),
                                                                    "LevelMaximo", queroPT.getLevelMaximo()
                                                                    // Adicione aqui os outros campos para as outras vocações, se necessário.
                                                            )
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    // Sucesso ao atualizar os dados da PT
                                                                    Toast.makeText(context, "Você entrou na PT!", Toast.LENGTH_SHORT).show();
                                                                    notifyItemChanged(position);

                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    // Erro ao atualizar os dados da PT
                                                                    Toast.makeText(context, "Erro ao entrar na PT. Tente novamente.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                } else {
                                                    // Não há vaga para Royal Paladin na PT ou o usuário não atende às condições
                                                    // Exiba uma mensagem ao usuário informando que ele não pode entrar na PT.
                                                    Toast.makeText(context, "Não há vaga para sua vocação ou level fora do limite", Toast.LENGTH_SHORT).show();
                                                }
                                                break;

                                            case "Elder Druid":
                                                // Verificar se há vaga para Elder Druid na PT
                                                if (queroPT.getEDnome().isEmpty() && level >= queroPT.getLevelMinimo() && level <= queroPT.getLevelMaximo()) {
                                                    // Calcular os novos valores para levelMinimo e levelMaximo
                                                    int novoLevelMinimo = (level * 2) / 3;
                                                    int novoLevelMaximo = (level * 3) / 2;

                                                    // Verificar se os novos valores são maiores que os atuais e, se sim, atualizar os campos no objeto queroPT
                                                    if (novoLevelMinimo > queroPT.getLevelMinimo()) {
                                                        queroPT.setLevelMinimo(novoLevelMinimo);
                                                    } else {  }
                                                    if (novoLevelMaximo < queroPT.getLevelMaximo()) {
                                                        queroPT.setLevelMaximo(novoLevelMaximo);
                                                    } else {  }

                                                    // Atualizar os campos da PT com as informações do usuário
                                                    queroPT.setEDnome(nomePersonagem);
                                                    queroPT.setEDcod(codPais);
                                                    queroPT.setEDcontato(telefone);
                                                    queroPT.setEDlevel(level);

                                                    //Obtendo Data atual e formatnaod para dd/MM/aaaa
                                                    Date dataAtual = new Date();
                                                    SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
                                                    String data = dataFormatada.format(dataAtual);
                                                    queroPT.setData(data);

                                                    // Agora você pode prosseguir com a atualização no Firestore
                                                    DocumentReference ptDocRef = FirebaseFirestore.getInstance().collection("QueroPT").document(queroPT.getPTid());
                                                    ptDocRef.update(
                                                                    "data", queroPT.getData(),
                                                                    "EDnome", queroPT.getEDnome(),
                                                                    "EDcod", queroPT.getEDcod(),
                                                                    "EDcontato", queroPT.getEDcontato(),
                                                                    "EDlevel", queroPT.getEDlevel(),
                                                                    "LevelMinimo", queroPT.getLevelMinimo(),
                                                                    "LevelMaximo", queroPT.getLevelMaximo()
                                                                    // Adicione aqui os outros campos para as outras vocações, se necessário.
                                                            )
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    // Sucesso ao atualizar os dados da PT
                                                                    Toast.makeText(context, "Você entrou na PT!", Toast.LENGTH_SHORT).show();
                                                                    notifyItemChanged(position);

                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    // Erro ao atualizar os dados da PT
                                                                    Toast.makeText(context, "Erro ao entrar na PT. Tente novamente.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                } else {
                                                    // Não há vaga para Elder Druid na PT ou o usuário não atende às condições
                                                    // Exiba uma mensagem ao usuário informando que ele não pode entrar na PT.
                                                    Toast.makeText(context, "Não há vaga para sua vocação ou level fora do limite", Toast.LENGTH_SHORT).show();
                                                }
                                                break;

                                            case "Master Sorcerer":
                                                // Verificar se há vaga para Master Sorcerer na PT
                                                if (queroPT.getMSnome().isEmpty() && level >= queroPT.getLevelMinimo() && level <= queroPT.getLevelMaximo()) {
                                                    // Calcular os novos valores para levelMinimo e levelMaximo
                                                    int novoLevelMinimo = (level * 2) / 3;
                                                    int novoLevelMaximo = (level * 3) / 2;

                                                    // Verificar se os novos valores são maiores que os atuais e, se sim, atualizar os campos no objeto queroPT
                                                    if (novoLevelMinimo > queroPT.getLevelMinimo()) {
                                                        queroPT.setLevelMinimo(novoLevelMinimo);
                                                    } else {  }
                                                    if (novoLevelMaximo < queroPT.getLevelMaximo()) {
                                                        queroPT.setLevelMaximo(novoLevelMaximo);
                                                    } else {  }

                                                    // Atualizar os campos da PT com as informações do usuário
                                                    queroPT.setMSnome(nomePersonagem);
                                                    queroPT.setMScod(codPais);
                                                    queroPT.setMScontato(telefone);
                                                    queroPT.setMSlevel(level);

                                                    //Obtendo Data atual e formatnaod para dd/MM/aaaa
                                                    Date dataAtual = new Date();
                                                    SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
                                                    String data = dataFormatada.format(dataAtual);
                                                    queroPT.setData(data);

                                                    // Agora você pode prosseguir com a atualização no Firestore
                                                    DocumentReference ptDocRef = FirebaseFirestore.getInstance().collection("QueroPT").document(queroPT.getPTid());
                                                    ptDocRef.update(
                                                                    "data", queroPT.getData(),
                                                                    "MSnome", queroPT.getMSnome(),
                                                                    "MScod", queroPT.getMScod(),
                                                                    "MScontato", queroPT.getMScontato(),
                                                                    "MSlevel", queroPT.getMSlevel(),
                                                                    "LevelMinimo", queroPT.getLevelMinimo(),
                                                                    "LevelMaximo", queroPT.getLevelMaximo()
                                                                    // Adicione aqui os outros campos para as outras vocações, se necessário.
                                                            )
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    // Sucesso ao atualizar os dados da PT
                                                                    Toast.makeText(context, "Você entrou na PT!", Toast.LENGTH_SHORT).show();
                                                                    notifyItemChanged(position);

                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    // Erro ao atualizar os dados da PT
                                                                    Toast.makeText(context, "Erro ao entrar na PT. Tente novamente.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                } else {
                                                    // Não há vaga para Master Sorcerer na PT ou o usuário não atende às condições
                                                    // Exiba uma mensagem ao usuário informando que ele não pode entrar na PT.
                                                    Toast.makeText(context, "Não há vaga para sua vocação ou level fora do limite", Toast.LENGTH_SHORT).show();
                                                }
                                                break;

                                            default:
                                                // A vocação do usuário não corresponde a nenhuma das opções válidas
                                                // Exiba uma mensagem ao usuário informando que a vocação não é suportada ou que é inválida.
                                                break;
                                        }
                                    } else {
                                        // A vocação do usuário não foi encontrada ou é nula
                                        // Exiba uma mensagem ao usuário informando que a vocação não está disponível.
                                    }
                                } else {
                                    // O documento do usuário não foi encontrado ou não existe
                                    // Exiba uma mensagem ao usuário informando que os dados do usuário não foram encontrados.
                                }
                            } else {
                                // Falha ao ler o documento do usuário
                                Toast.makeText(context, "Erro ao ler dados do usuário.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return queroPTList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtHorario;
        TextView txtResponsavel;
        TextView txtContato;
        TextView txtLevelMaximo;
        TextView txtLevelMinimo;
        Button btnEntrarNaPT;
        Button btnSairDaPT;

        TextView txtEKnome;
        TextView txtEKlevel;
        TextView txtRPnome;
        TextView txtRPlevel;
        TextView txtEDnome;
        TextView txtEDlevel;
        TextView txtMSnome;
        TextView txtMSlevel;
        ImageView checkEKicon;
        ImageView checkEDicon;
        ImageView checkRPicon;
        ImageView checkMSicon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHorario = itemView.findViewById(R.id.txtHorario);
            txtResponsavel = itemView.findViewById(R.id.txtResponsavel);
            txtContato = itemView.findViewById(R.id.txtContato);
            txtLevelMaximo = itemView.findViewById(R.id.txtLevelMaximo);
            txtLevelMinimo = itemView.findViewById(R.id.txtLevelMinimo);
            btnEntrarNaPT = itemView.findViewById(R.id.btnEntrarNaPT);
            btnSairDaPT = itemView.findViewById(R.id.btnSairDaPT);
            txtEKnome = itemView.findViewById(R.id.txtEKNome);
            txtEKlevel = itemView.findViewById(R.id.txtEKLevel);
            txtEDnome = itemView.findViewById(R.id.txtEDNome);
            txtEDlevel = itemView.findViewById(R.id.txtEDLevel);
            txtRPnome = itemView.findViewById(R.id.txtRPNome);
            txtRPlevel = itemView.findViewById(R.id.txtRPLevel);
            txtMSnome = itemView.findViewById(R.id.txtMSNome);
            txtMSlevel = itemView.findViewById(R.id.txtMSLevel);
            checkRPicon = itemView.findViewById(R.id.checkRPicon);
            checkEDicon = itemView.findViewById(R.id.checkEDicon);
            checkEKicon = itemView.findViewById(R.id.checkEKicon);
            checkMSicon = itemView.findViewById(R.id.checkMSicon);

        }
    }

}
