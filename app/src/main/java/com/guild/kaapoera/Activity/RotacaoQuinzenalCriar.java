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
                    createRotacaoWD();
                    createRotacaoFERU();
                    createRotacaoLK();
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

    private void createRotacaoWD() {
        //Obtendo Data e Hora informada pelo usuário
        EditText editTextHora = findViewById(R.id.editTextHora);
        String hora = editTextHora.getText().toString();
        EditText editTextData = findViewById(R.id.editTextData);
        String data = editTextData.getText().toString();

        // Criar o grupo com as informações iniciais wd
        Map<String, Object> groupData = new HashMap<>();
        groupData.put("data", data);
        groupData.put("responsavel", currentUserNomePersonagem); // Substituir pelo nome do personagem do usuário
        groupData.put("horario", hora);

        // Criar Vagas vazias wd
        groupData.put("boss", "wd");
        groupData.put("wd_EK_1_Nome", "");
        groupData.put("wd_EK_1_Level", 0);
        groupData.put("wd_EK_2_Nome", "");
        groupData.put("wd_EK_2_Level", 0);
        groupData.put("wd_EK_3_Nome", "");
        groupData.put("wd_EK_3_Level", 0);
        groupData.put("wd_ED_1_Nome", "");
        groupData.put("wd_ED_1_Level", 0);
        groupData.put("wd_ED_2_Nome", "");
        groupData.put("wd_ED_2_Level", 0);
        groupData.put("wd_ED_3_Nome", "");
        groupData.put("wd_ED_3_Level", 0);
        groupData.put("wd_Shooter_1_Nome", "");
        groupData.put("wd_Shooter_1_Level", 0);
        groupData.put("wd_Shooter_2_Nome", "");
        groupData.put("wd_Shooter_2_Level", 0);
        groupData.put("wd_Shooter_3_Nome", "");
        groupData.put("wd_Shooter_3_Level", 0);
        groupData.put("wd_Shooter_4_Nome", "");
        groupData.put("wd_Shooter_4_Level", 0);
        groupData.put("wd_Shooter_5_Nome", "");
        groupData.put("wd_Shooter_5_Level", 0);
        groupData.put("wd_Shooter_6_Nome", "");
        groupData.put("wd_Shooter_6_Level", 0);
        groupData.put("wd_Shooter_7_Nome", "");
        groupData.put("wd_Shooter_7_Level", 0);
        groupData.put("wd_Shooter_8_Nome", "");
        groupData.put("wd_Shooter_8_Level", 0);
        groupData.put("wd_Shooter_9_Nome", "");
        groupData.put("wd_Shooter_9_Level", 0);

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
    private void createRotacaoFERU() {
        //Obtendo Data e Hora informada pelo usuário
        EditText editTextHora = findViewById(R.id.editTextHora);
        String hora = editTextHora.getText().toString();
        EditText editTextData = findViewById(R.id.editTextData);
        String data = editTextData.getText().toString();

        // Criar o grupo com as informações iniciais wd
        Map<String, Object> groupData = new HashMap<>();
        groupData.put("data", data);
        groupData.put("responsavel", currentUserNomePersonagem); // Substituir pelo nome do personagem do usuário
        groupData.put("horario", hora);

        // Criar Vagas vazias Feru
        groupData.put("boss", "feru");
        groupData.put("feru_EK_1_Nome", "");
        groupData.put("feru_EK_1_Level", 0);
        groupData.put("feru_EK_2_Nome", "");
        groupData.put("feru_EK_2_Level", 0);
        groupData.put("feru_EK_3_Nome", "");
        groupData.put("feru_EK_3_Level", 0);
        groupData.put("feru_ED_1_Nome", "");
        groupData.put("feru_ED_1_Level", 0);
        groupData.put("feru_ED_2_Nome", "");
        groupData.put("feru_ED_2_Level", 0);
        groupData.put("feru_ED_3_Nome", "");
        groupData.put("feru_ED_3_Level", 0);
        groupData.put("feru_Shooter_1_Nome", "");
        groupData.put("feru_Shooter_1_Level", 0);
        groupData.put("feru_Shooter_2_Nome", "");
        groupData.put("feru_Shooter_2_Level", 0);
        groupData.put("feru_Shooter_3_Nome", "");
        groupData.put("feru_Shooter_3_Level", 0);
        groupData.put("feru_Shooter_4_Nome", "");
        groupData.put("feru_Shooter_4_Level", 0);
        groupData.put("feru_Shooter_5_Nome", "");
        groupData.put("feru_Shooter_5_Level", 0);
        groupData.put("feru_Shooter_6_Nome", "");
        groupData.put("feru_Shooter_6_Level", 0);
        groupData.put("feru_Shooter_7_Nome", "");
        groupData.put("feru_Shooter_7_Level", 0);
        groupData.put("feru_Shooter_8_Nome", "");
        groupData.put("feru_Shooter_8_Level", 0);
        groupData.put("feru_Shooter_9_Nome", "");
        groupData.put("feru_Shooter_9_Level", 0);

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
    private void createRotacaoLK() {
        //Obtendo Data e Hora informada pelo usuário
        EditText editTextHora = findViewById(R.id.editTextHora);
        String hora = editTextHora.getText().toString();
        EditText editTextData = findViewById(R.id.editTextData);
        String data = editTextData.getText().toString();

        // Criar o grupo com as informações iniciais wd
        Map<String, Object> groupData = new HashMap<>();
        groupData.put("data", data);
        groupData.put("responsavel", currentUserNomePersonagem); // Substituir pelo nome do personagem do usuário
        groupData.put("horario", hora);

        // Criar Vagas vazias lk
        groupData.put("boss", "lk");
        groupData.put("lk_EK_1_Nome", "");
        groupData.put("lk_EK_1_Level", 0);
        groupData.put("lk_EK_2_Nome", "");
        groupData.put("lk_EK_2_Level", 0);
        groupData.put("lk_EK_3_Nome", "");
        groupData.put("lk_EK_3_Level", 0);
        groupData.put("lk_ED_1_Nome", "");
        groupData.put("lk_ED_1_Level", 0);
        groupData.put("lk_RP_1_Nome", "");
        groupData.put("lk_RP_1_Level", 0);
        groupData.put("lk_RP_2_Nome", "");
        groupData.put("lk_RP_2_Level", 0);
        groupData.put("lk_RP_3_Nome", "");
        groupData.put("lk_RP_3_Level", 0);
        groupData.put("lk_Shooter_1_Nome", "");
        groupData.put("lk_Shooter_1_Level", 0);
        groupData.put("lk_Shooter_2_Nome", "");
        groupData.put("lk_Shooter_2_Level", 0);
        groupData.put("lk_Shooter_3_Nome", "");
        groupData.put("lk_Shooter_3_Level", 0);
        groupData.put("lk_Shooter_4_Nome", "");
        groupData.put("lk_Shooter_4_Level", 0);
        groupData.put("lk_Shooter_5_Nome", "");
        groupData.put("lk_Shooter_5_Level", 0);
        groupData.put("lk_Shooter_6_Nome", "");
        groupData.put("lk_Shooter_6_Level", 0);
        groupData.put("lk_Shooter_7_Nome", "");
        groupData.put("lk_Shooter_7_Level", 0);
        groupData.put("lk_Shooter_8_Nome", "");
        groupData.put("lk_Shooter_8_Level", 0);

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
