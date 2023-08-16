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
                } else if (!isValidDateFormat(data)) {
                    Toast.makeText(RotacaoQuinzenalCriar.this, "Formato de data inválido. Use dd/mm.", Toast.LENGTH_SHORT).show();
                } else if (!isValidTimeFormat(hora)) {
                    Toast.makeText(RotacaoQuinzenalCriar.this, "Formato de horário inválido. Use hh:mm.", Toast.LENGTH_SHORT).show();
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

    // Função para validar o formato da data (dd/mm)
    private boolean isValidDateFormat(String date) {
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])$";
        return date.matches(regex);
    }

    // Função para validar o formato do horário (hh:mm)
    private boolean isValidTimeFormat(String time) {
        String regex = "^(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$";
        return time.matches(regex);
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
        groupData.put("p1_Nome", "");
        groupData.put("p1_Level", 0);
        groupData.put("p2_Nome", "");
        groupData.put("p2_Level", 0);
        groupData.put("p3_Nome", "");
        groupData.put("p3_Level", 0);
        groupData.put("p4_Nome", "");
        groupData.put("p4_Level", 0);
        groupData.put("p5_Nome", "");
        groupData.put("p5_Level", 0);
        groupData.put("p6_Nome", "");
        groupData.put("p6_Level", 0);
        groupData.put("p7_Nome", "");
        groupData.put("p7_Level", 0);
        groupData.put("p8_Nome", "");
        groupData.put("p8_Level", 0);
        groupData.put("p9_Nome", "");
        groupData.put("p9_Level", 0);
        groupData.put("p10_Nome", "");
        groupData.put("p10_Level", 0);
        groupData.put("p11_Nome", "");
        groupData.put("p11_Level", 0);
        groupData.put("p12_Nome", "");
        groupData.put("p12_Level", 0);
        groupData.put("p13_Nome", "");
        groupData.put("p13_Level", 0);
        groupData.put("p14_Nome", "");
        groupData.put("p14_Level", 0);
        groupData.put("p15_Nome", "");
        groupData.put("p15_Level", 0);


        // Adicionar o documento no Firestore
        db.collection("RotacaoQuinzenal")
                .add(groupData)  // Usar .add() em vez de .document()
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            DocumentReference documentReference = task.getResult();
                            String documentID = documentReference.getId();
                            // Adicionar o ID do documento ao campo rotacaoID
                            documentReference.update("rotacaoID", documentID);
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
        groupData.put("p1_Nome", "");
        groupData.put("p1_Level", 0);
        groupData.put("p2_Nome", "");
        groupData.put("p2_Level", 0);
        groupData.put("p3_Nome", "");
        groupData.put("p3_Level", 0);
        groupData.put("p4_Nome", "");
        groupData.put("p4_Level", 0);
        groupData.put("p5_Nome", "");
        groupData.put("p5_Level", 0);
        groupData.put("p6_Nome", "");
        groupData.put("p6_Level", 0);
        groupData.put("p7_Nome", "");
        groupData.put("p7_Level", 0);
        groupData.put("p8_Nome", "");
        groupData.put("p8_Level", 0);
        groupData.put("p9_Nome", "");
        groupData.put("p9_Level", 0);
        groupData.put("p10_Nome", "");
        groupData.put("p10_Level", 0);
        groupData.put("p11_Nome", "");
        groupData.put("p11_Level", 0);
        groupData.put("p12_Nome", "");
        groupData.put("p12_Level", 0);
        groupData.put("p13_Nome", "");
        groupData.put("p13_Level", 0);
        groupData.put("p14_Nome", "");
        groupData.put("p14_Level", 0);
        groupData.put("p15_Nome", "");
        groupData.put("p15_Level", 0);

        /// Adicionar o documento no Firestore
        db.collection("RotacaoQuinzenal")
                .add(groupData)  // Usar .add() em vez de .document()
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            DocumentReference documentReference = task.getResult();
                            String documentID = documentReference.getId();
                            // Adicionar o ID do documento ao campo rotacaoID
                            documentReference.update("rotacaoID", documentID);
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
        groupData.put("p1_Nome", "");
        groupData.put("p1_Level", 0);
        groupData.put("p2_Nome", "");
        groupData.put("p2_Level", 0);
        groupData.put("p3_Nome", "");
        groupData.put("p3_Level", 0);
        groupData.put("p4_Nome", "");
        groupData.put("p4_Level", 0);
        groupData.put("p5_Nome", "");
        groupData.put("p5_Level", 0);
        groupData.put("p6_Nome", "");
        groupData.put("p6_Level", 0);
        groupData.put("p7_Nome", "");
        groupData.put("p7_Level", 0);
        groupData.put("p8_Nome", "");
        groupData.put("p8_Level", 0);
        groupData.put("p9_Nome", "");
        groupData.put("p9_Level", 0);
        groupData.put("p10_Nome", "");
        groupData.put("p10_Level", 0);
        groupData.put("p11_Nome", "");
        groupData.put("p11_Level", 0);
        groupData.put("p12_Nome", "");
        groupData.put("p12_Level", 0);
        groupData.put("p13_Nome", "");
        groupData.put("p13_Level", 0);
        groupData.put("p14_Nome", "");
        groupData.put("p14_Level", 0);
        groupData.put("p15_Nome", "");
        groupData.put("p15_Level", 0);

        // Adicionar o documento no Firestore
        db.collection("RotacaoQuinzenal")
                .add(groupData)  // Usar .add() em vez de .document()
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            DocumentReference documentReference = task.getResult();
                            String documentID = documentReference.getId();
                            // Adicionar o ID do documento ao campo rotacaoID
                            documentReference.update("rotacaoID", documentID);
                            Toast.makeText(RotacaoQuinzenalCriar.this, "Rotação criada com sucesso!", Toast.LENGTH_SHORT).show();
                            editTextData.setText("");
                            editTextHora.setText("");
                        } else {
                            Toast.makeText(RotacaoQuinzenalCriar.this, "Erro ao criar Rotação. Tente novamente.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
