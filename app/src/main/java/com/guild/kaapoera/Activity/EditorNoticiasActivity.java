package com.guild.kaapoera.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.guild.kaapoera.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditorNoticiasActivity extends AppCompatActivity {

    private EditText editTextTitulo, editTextConteudo;
    private Button buttonSalvar, buttonCancelar;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_noticias);

        // Inicialização do Firebase Auth e Firestore
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextConteudo = findViewById(R.id.editTextConteudo);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonCancelar = findViewById(R.id.buttonCancelar);

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarNoticia();
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void salvarNoticia() {
        String titulo = editTextTitulo.getText().toString().trim();
        String conteudo = editTextConteudo.getText().toString().trim();

        if (!titulo.isEmpty() && !conteudo.isEmpty()) {
            String uid = auth.getCurrentUser().getUid();

            // Buscar o nome do autor na coleção "Usuarios"
            db.collection("Usuarios").document(uid)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String nomeAutor = documentSnapshot.getString("nomePersonagem");
                            String conteudoHTML = conteudo.replace("\n", "<br>");

                            // Criar um novo documento na coleção "Noticias"
                            Map<String, Object> noticia = new HashMap<>();
                            noticia.put("titulo", titulo);
                            noticia.put("conteudo", conteudoHTML);
                            noticia.put("autor", nomeAutor);

                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String dataAtual = dateFormat.format(new Date());
                            noticia.put("data", dataAtual);

                            db.collection("Noticias")
                                    .add(noticia)
                                    .addOnSuccessListener(documentReference -> {
                                        Toast.makeText(EditorNoticiasActivity.this, "Notícia salva com sucesso!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(EditorNoticiasActivity.this, "Erro ao salvar notícia.", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(EditorNoticiasActivity.this, "Erro ao buscar nome do autor.", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }
}
