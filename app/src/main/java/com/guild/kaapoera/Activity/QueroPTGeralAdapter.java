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
                                                    queroPT.setEKcontato(telefone);
                                                    queroPT.setEKlevel(level);
                                                    // Agora você pode prosseguir com a atualização no Firestore
                                                    DocumentReference ptDocRef = FirebaseFirestore.getInstance().collection("QueroPT").document(queroPT.getPTid());
                                                    ptDocRef.update(
                                                                    "EKnome", queroPT.getEKnome(),
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
                                                    queroPT.setRPcontato(telefone);
                                                    queroPT.setRPlevel(level);
                                                    // Agora você pode prosseguir com a atualização no Firestore
                                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                    DocumentReference ptDocRef = db.collection("QueroPT").document(queroPT.getPTid());
                                                    ptDocRef.update(
                                                                    "RPnome", queroPT.getRPnome(),
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
                                                    queroPT.setEDcontato(telefone);
                                                    queroPT.setEDlevel(level);
                                                    // Agora você pode prosseguir com a atualização no Firestore
                                                    DocumentReference ptDocRef = FirebaseFirestore.getInstance().collection("QueroPT").document(queroPT.getPTid());
                                                    ptDocRef.update(
                                                                    "EDnome", queroPT.getEDnome(),
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
                                                    queroPT.setMScontato(telefone);
                                                    queroPT.setMSlevel(level);
                                                    // Agora você pode prosseguir com a atualização no Firestore
                                                    DocumentReference ptDocRef = FirebaseFirestore.getInstance().collection("QueroPT").document(queroPT.getPTid());
                                                    ptDocRef.update(
                                                                    "MSnome", queroPT.getMSnome(),
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
        TextView txtEKnome;
        TextView txtEKlevel;
        TextView txtRPnome;
        TextView txtRPlevel;
        TextView txtEDnome;
        TextView txtEDlevel;
        TextView txtMSnome;
        TextView txtMSlevel;

        public ViewHolder(@NonNull View itemView) {
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

        }
    }
}
