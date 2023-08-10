package com.guild.kaapoera.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.guild.kaapoera.R;

import java.util.HashMap;
import java.util.Map;

public class RotacaoQuinzenalCriar extends AppCompatActivity {

    private FirebaseFirestore db;
    private String currentUserUID;
    private String currentUserNomePersonagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotacaoquinzenal_criar);

        // Inicializar o Firestore
        db = FirebaseFirestore.getInstance();

        // Obter o UID do usuário atualmente logado
        currentUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Consultar as informações do usuário no Firestore
        DocumentReference userDocRef = db.collection("Usuarios").document(currentUserUID);
        userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Documento encontrado, obter os dados do usuário
                        currentUserNomePersonagem = document.getString("nomePersonagem");

                        // Atualizar as informações iniciais exibidas na tela
                        updateInitialInfoOnScreen();
                    } else {
                        // Documento não encontrado
                    }
                } else {
                    // Ocorreu um erro ao consultar os dados do usuário
                }
            }
        });

        Button btnCriarRotacao = findViewById(R.id.btnCriarRotacao);
        btnCriarRotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtendo Data e Hora informada pelo usuário
                EditText editTextHora = findViewById(R.id.editTextHora);
                String hora = editTextHora.getText().toString();
                EditText editTextData = findViewById(R.id.editTextData);
                String data = editTextData.getText().toString();

                // Verificar se os campos de Data e Hora estão preenchidos
                if (data.isEmpty() || hora.isEmpty()) {
                    Toast.makeText(RotacaoQuinzenalCriar.this, "Preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
                } else {
                    // Criar a Rotação no Firestore
                    createRotacao();
                }
            }
        });

        Button btnVoltar = findViewById(R.id.btnVoltarTelarotacao);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Voltar para a tela RotacaoQuinzenal
                Intent intent = new Intent(RotacaoQuinzenalCriar.this, RotacaoQuinzenal.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateInitialInfoOnScreen() {
        // Atualizar as informações iniciais exibidas na tela
        TextView tvNomePersonagem = findViewById(R.id.tvNomePersonagem);
        tvNomePersonagem.setText("Nome do Personagem: " + currentUserNomePersonagem); // Substituir pelo nome do personagem do usuário

    }

    private void createRotacao() {
        //Obtendo Data e Hora informada pelo usuário
        EditText editTextHora = findViewById(R.id.editTextHora);
        String hora = editTextHora.getText().toString();
        EditText editTextData = findViewById(R.id.editTextData);
        String data = editTextData.getText().toString();

        // Criar o grupo com as informações iniciais
        Map<String, Object> groupData = new HashMap<>();
        groupData.put("Data", data);
        groupData.put("Responsavel", currentUserNomePersonagem); // Substituir pelo nome do personagem do usuário
        groupData.put("Horario", hora);

        // Criar Vagas vazias WD
        groupData.put("WD_EKprincipalNome", "");
        groupData.put("WD_EKprincipalLVL", 0);
        groupData.put("WD_EKsecundarioNome", "");
        groupData.put("WD_EKsecundarioLVL", 0);
        groupData.put("WD_EKterciarioNome", "");
        groupData.put("WD_EKterciarioLVL", 0);
        groupData.put("WD_EDprincipalNome", "");
        groupData.put("WD_EDprincipalLVL", 0);
        groupData.put("WD_EDsecundarioNome", "");
        groupData.put("WD_EDsecundarioLVL", 0);
        groupData.put("WD_EDterciarioNome", "");
        groupData.put("WD_EDterciarioLVL", 0);
        groupData.put("WD_Shooter1nome", "");
        groupData.put("WD_Shooter1lvl", 0);
        groupData.put("WD_Shooter2nome", "");
        groupData.put("WD_Shooter2lvl", 0);
        groupData.put("WD_Shooter3nome", "");
        groupData.put("WD_Shooter3lvl", 0);
        groupData.put("WD_Shooter4nome", "");
        groupData.put("WD_Shooter4lvl", 0);
        groupData.put("WD_Shooter5nome", "");
        groupData.put("WD_Shooter5lvl", 0);
        groupData.put("WD_Shooter6nome", "");
        groupData.put("WD_Shooter6lvl", 0);
        groupData.put("WD_Shooter7nome", "");
        groupData.put("WD_Shooter7lvl", 0);
        groupData.put("WD_Shooter8nome", "");
        groupData.put("WD_Shooter8lvl", 0);
        groupData.put("WD_Shooter9nome", "");
        groupData.put("WD_Shooter9lvl", 0);

        // Criar Vagas vazias Feru
        groupData.put("Feru_EKprincipalNome", "");
        groupData.put("Feru_EKprincipalLVL", 0);
        groupData.put("Feru_EKsecundarioNome", "");
        groupData.put("Feru_EKsecundarioLVL", 0);
        groupData.put("Feru_EKterciarioNome", "");
        groupData.put("Feru_EKterciarioLVL", 0);
        groupData.put("Feru_EDprincipalNome", "");
        groupData.put("Feru_EDprincipalLVL", 0);
        groupData.put("Feru_EDsecundarioNome", "");
        groupData.put("Feru_EDsecundarioLVL", 0);
        groupData.put("Feru_EDterciarioNome", "");
        groupData.put("Feru_EDterciarioLVL", 0);
        groupData.put("Feru_Shooter1nome", "");
        groupData.put("Feru_Shooter1lvl", 0);
        groupData.put("Feru_Shooter2nome", "");
        groupData.put("Feru_Shooter2lvl", 0);
        groupData.put("Feru_Shooter3nome", "");
        groupData.put("Feru_Shooter3lvl", 0);
        groupData.put("Feru_Shooter4nome", "");
        groupData.put("Feru_Shooter4lvl", 0);
        groupData.put("Feru_Shooter5nome", "");
        groupData.put("Feru_Shooter5lvl", 0);
        groupData.put("Feru_Shooter6nome", "");
        groupData.put("Feru_Shooter6lvl", 0);
        groupData.put("Feru_Shooter7nome", "");
        groupData.put("Feru_Shooter7lvl", 0);
        groupData.put("Feru_Shooter8nome", "");
        groupData.put("Feru_Shooter8lvl", 0);
        groupData.put("Feru_Shooter9nome", "");
        groupData.put("Feru_Shooter9lvl", 0);

        // Criar Vagas vazias LK
        groupData.put("LK_EKprincipalNome", "");
        groupData.put("LK_EKprincipalLVL", 0);
        groupData.put("LK_EKsecundarioNome", "");
        groupData.put("LK_EKsecundarioLVL", 0);
        groupData.put("LK_EKterciarioNome", "");
        groupData.put("LK_EKterciarioLVL", 0);
        groupData.put("LK_EDprincipalNome", "");
        groupData.put("LK_EDprincipalLVL", 0);
        groupData.put("LK_RPmecanica1nome", "");
        groupData.put("LK_RPmecanica1lvl", 0);
        groupData.put("LK_RPmecanica2nome", "");
        groupData.put("LK_RPmecanica2lvl", 0);
        groupData.put("LK_RPmecanica3nome", "");
        groupData.put("LK_RPmecanica3lvl", 0);
        groupData.put("LK_Shooter1nome", "");
        groupData.put("LK_Shooter1lvl", 0);
        groupData.put("LK_Shooter2nome", "");
        groupData.put("LK_Shooter2lvl", 0);
        groupData.put("LK_Shooter3nome", "");
        groupData.put("LK_Shooter3lvl", 0);
        groupData.put("LK_Shooter4nome", "");
        groupData.put("LK_Shooter4lvl", 0);
        groupData.put("LK_Shooter5nome", "");
        groupData.put("LK_Shooter5lvl", 0);
        groupData.put("LK_Shooter6nome", "");
        groupData.put("LK_Shooter6lvl", 0);
        groupData.put("LK_Shooter7nome", "");
        groupData.put("LK_Shooter7lvl", 0);
        groupData.put("LK_Shooter8nome", "");
        groupData.put("LK_Shooter8lvl", 0);

        // Adicionar o documento no Firestore
        db.collection("RotacaoQuinzenal").document()
                .set(groupData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RotacaoQuinzenalCriar.this, "Rotação criada com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RotacaoQuinzenalCriar.this, "Erro ao criar Rotação. Tente novamente.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
