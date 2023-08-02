package com.guild.kaapoera.Activity;

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

public class QueroPTCriarPTActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private String currentUserUID;
    private int currentUserLevel;
    private String currentUserVocacao;
    private String currentUserContato;
    private String currentUserNomePersonagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quero_pt_criar_pt);

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
                        currentUserLevel = document.getLong("level").intValue();
                        currentUserVocacao = document.getString("vocacao");
                        currentUserContato = document.getString("telefone");
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

        Button btnQueroPT = findViewById(R.id.btnCriarPT);
        btnQueroPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criar a PT (Party) no Firestore
                createPT();
            }
        });

        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Voltar para a tela QueroPT
                onBackPressed();
            }
        });
    }

    private void updateInitialInfoOnScreen() {
        // Atualizar as informações iniciais exibidas na tela
        TextView tvNomePersonagem = findViewById(R.id.tvNomePersonagem);
        tvNomePersonagem.setText("Nome do Personagem: " + currentUserNomePersonagem); // Substituir pelo nome do personagem do usuário

        TextView tvContato = findViewById(R.id.tvContato);
        tvContato.setText("Telefone: " + currentUserContato); // Substituir pelo telefone do usuário

        TextView tvVocacao = findViewById(R.id.tvVocacao);
        tvVocacao.setText("Vocação: " + currentUserVocacao); // Substituir pela vocação do usuário

        TextView tvLevel = findViewById(R.id.tvLevel);
        tvLevel.setText("Level: " + currentUserLevel); // Convertendo currentUserLevel para String
    }

    private void createPT() {
        // Calcular os limites de level
        int maxLevel = (currentUserLevel * 3) / 2;
        int minLevel = (currentUserLevel * 2) / 3;

        //Obtendo a hora informada pelo usuário
        EditText editTextHora = findViewById(R.id.editTextHora);
        String hora = editTextHora.getText().toString();

        // Criar o grupo com as informações iniciais
        Map<String, Object> groupData = new HashMap<>();
        groupData.put("Responsavel", currentUserNomePersonagem); // Substituir pelo nome do personagem do usuário
        groupData.put("Horario", hora);
        groupData.put("Contato", currentUserContato); // Substituir pelo telefone do usuário
        groupData.put("LevelMaximo", maxLevel);
        groupData.put("LevelMinimo", minLevel);

        // Checar a vocação e atribuir os valores aos campos específicos da vocação
        if (currentUserVocacao.equals("Royal Paladin")) {
            groupData.put("RPnome", currentUserNomePersonagem);
            groupData.put("RPlevel", currentUserLevel);
            groupData.put("RPcontato", currentUserContato);
            groupData.put("EKnome", "");
            groupData.put("EKlevel", 0);
            groupData.put("EKcontato", "");
            groupData.put("MSnome", "");
            groupData.put("MSlevel", 0);
            groupData.put("MScontato", "");
            groupData.put("EDnome", "");
            groupData.put("EDlevel", 0);
            groupData.put("EDcontato", "");

        } else if (currentUserVocacao.equals("Master Sorcerer")) {
            groupData.put("MSnome", currentUserNomePersonagem);
            groupData.put("MSlevel", currentUserLevel);
            groupData.put("MScontato", currentUserContato);
            groupData.put("EDnome", "");
            groupData.put("EDlevel", 0);
            groupData.put("EDcontato", "");
            groupData.put("EKnome", "");
            groupData.put("EKlevel", 0);
            groupData.put("EKcontato", "");
            groupData.put("RPnome", "");
            groupData.put("RPlevel", 0);
            groupData.put("RPcontato", "");

        } else if (currentUserVocacao.equals("Elite Knight")) {
            groupData.put("EKnome", currentUserNomePersonagem);
            groupData.put("EKlevel", currentUserLevel);
            groupData.put("EKcontato", currentUserContato);
            groupData.put("RPnome", "");
            groupData.put("RPlevel", 0);
            groupData.put("RPcontato", "");
            groupData.put("EDnome", "");
            groupData.put("EDlevel", 0);
            groupData.put("EDcontato", "");
            groupData.put("MSnome", "");
            groupData.put("MSlevel", 0);
            groupData.put("MScontato", "");

        } else if (currentUserVocacao.equals("Elder Druid")) {
            groupData.put("EDnome", currentUserNomePersonagem);
            groupData.put("EDlevel", currentUserLevel);
            groupData.put("EDcontato", currentUserContato);
            groupData.put("EKnome", "");
            groupData.put("EKlevel", 0);
            groupData.put("EKcontato", "");
            groupData.put("MSnome", "");
            groupData.put("MSlevel", 0);
            groupData.put("MScontato", "");
            groupData.put("RPnome", "");
            groupData.put("RPlevel", 0);
            groupData.put("RPcontato", "");

        }

        // Adicionar o documento no Firestore
        db.collection("QueroPT").document()
                .set(groupData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(QueroPTCriarPTActivity.this, "PT criada com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(QueroPTCriarPTActivity.this, "Erro ao criar PT. Tente novamente.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
