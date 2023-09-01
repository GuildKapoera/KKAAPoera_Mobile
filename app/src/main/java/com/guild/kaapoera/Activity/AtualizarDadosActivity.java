package com.guild.kaapoera.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.guild.kaapoera.R;

public class AtualizarDadosActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextNomeUsuario;
    private EditText editTextTelefone;
    private Button buttonAtualizarDados;
    private Button buttonVoltar;

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_dados);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextNomeUsuario = findViewById(R.id.editTextNomeUsuario);
        editTextTelefone = findViewById(R.id.editTextTelefone);
        buttonAtualizarDados = findViewById(R.id.buttonAtualizarDados);
        buttonVoltar = findViewById(R.id.buttonVoltarAnuncio);

        buttonAtualizarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarDados();
            }
        });

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Preencher os campos com os valores atuais
        preencherCampos();
    }

    private void preencherCampos() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference documentReference = firestore.collection("Usuarios").document(userId);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String email = currentUser.getEmail();
                        String nomeUsuario = documentSnapshot.getString("nomeUsuario");
                        String telefone = documentSnapshot.getString("telefone");

                        editTextEmail.setText(email);
                        editTextNomeUsuario.setText(nomeUsuario);
                        editTextTelefone.setText(telefone);
                    }
                }
            });
        }
    }

    private void atualizarDados() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String email = editTextEmail.getText().toString().trim();
            String nomeUsuario = editTextNomeUsuario.getText().toString().trim();
            String telefone = editTextTelefone.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(nomeUsuario) || TextUtils.isEmpty(telefone)) {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Atualizar os dados no Firestore
            currentUser.updateEmail(email);
            String userId = currentUser.getUid();
            DocumentReference documentReference = firestore.collection("Usuarios").document(userId);
            documentReference.update("nomeUsuario", nomeUsuario, "telefone", telefone)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AtualizarDadosActivity.this, "Dados atualizados com sucesso.", Toast.LENGTH_SHORT).show();
                                } else {
                                Toast.makeText(AtualizarDadosActivity.this, "Erro ao atualizar os dados. Tente novamente.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
