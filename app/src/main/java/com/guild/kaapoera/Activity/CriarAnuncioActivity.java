package com.guild.kaapoera.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.guild.kaapoera.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CriarAnuncioActivity extends AppCompatActivity {

    private EditText editTextTitulo, editTextDescricao, editTextImagem, editTextPreco;
    private RadioGroup radioGroupTipoAnuncio;
    private Button buttonSalvarAnuncio, buttonVoltarAnuncio;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_anuncio);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextDescricao = findViewById(R.id.editTextDescricao);
        editTextImagem = findViewById(R.id.editTextImagem);
        editTextPreco = findViewById(R.id.editTextPreco);
        radioGroupTipoAnuncio = findViewById(R.id.radioGroupTipoAnuncio);
        buttonSalvarAnuncio = findViewById(R.id.buttonSalvarAnuncio);
        buttonVoltarAnuncio = findViewById(R.id.buttonVoltarAnuncio);

        buttonSalvarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAnuncio();
            }
        });
        buttonVoltarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { voltaPaginaCompraeVenda(); }
                    });
    }
    private void voltaPaginaCompraeVenda() {
        startActivity(new Intent(this, CompraEVendaActivity.class));
        finish();
    }
    private void salvarAnuncio() {
        String titulo = editTextTitulo.getText().toString().trim();
        String descricao = editTextDescricao.getText().toString().trim();
        String imagemUrl = editTextImagem.getText().toString().trim();
        String preco = editTextPreco.getText().toString().trim();
        String tipoAnuncio = ((RadioButton) findViewById(radioGroupTipoAnuncio.getCheckedRadioButtonId())).getText().toString();

        //Obtendo Data atual e formatnaod para dd/MM/aaaa
        Date dataAtual = new Date();
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
        String data = dataFormatada.format(dataAtual);

        // Obtenha o UID do usuário atual
        String uidUsuario = mAuth.getCurrentUser().getUid();

        // Crie um mapa com os dados do anúncio
        Map<String, Object> anuncioMap = new HashMap<>();
        anuncioMap.put("data", data);
        anuncioMap.put("titulo", titulo);
        anuncioMap.put("descricao", descricao);
        anuncioMap.put("imagemUrl", imagemUrl);
        anuncioMap.put("preco", preco);
        anuncioMap.put("tipoAnuncio", tipoAnuncio);
        anuncioMap.put("nomePersonagem", ""); // Vamos preencher este campo posteriormente
        anuncioMap.put("codPais", ""); //Vai depois, junto com o nome
        anuncioMap.put("telefone", ""); //Vai depois, junto com o nome

        // Crie uma referência para a coleção "Anuncios" no Firestore
        db.collection("Anuncios").add(anuncioMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // O anúncio foi salvo com sucesso, agora vamos obter o nome do personagem do usuário
                        obterNomePersonagem(uidUsuario, documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CriarAnuncioActivity.this, "Erro ao salvar o anúncio", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void obterNomePersonagem(String uidUsuario, final String idAnuncio) {
        // Crie uma referência para o documento do usuário no Firestore
        DocumentReference usuarioRef = db.collection("Usuarios").document(uidUsuario);

        // Leia os dados do documento do usuário
        usuarioRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                // Verifique se o documento existe antes de obter o nome do personagem
                if (documentSnapshot.exists()) {
                    // Obtenha o nome, codpais e telefone do personagem do documento do usuário
                    String nomePersonagem = documentSnapshot.getString("nomePersonagem");
                    String codPais = documentSnapshot.getString("codPais");
                    String telefone = documentSnapshot.getString("telefone");

                    // Atualize o campo "nomePersonagem" do anúncio com o nome do personagem do usuário
                    db.collection("Anuncios").document(idAnuncio)
                            .update("nomePersonagem", nomePersonagem,
                            "codPais", codPais,
                            "telefone", telefone)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(CriarAnuncioActivity.this, "Anúncio criado com sucesso!", Toast.LENGTH_SHORT).show();
                                    limparCampos();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CriarAnuncioActivity.this, "Erro ao atualizar o anúncio", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CriarAnuncioActivity.this, "Erro ao obter o nome do personagem", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void limparCampos() {
        editTextTitulo.setText("");
        editTextDescricao.setText("");
        editTextImagem.setText("");
        editTextPreco.setText("");
        radioGroupTipoAnuncio.clearCheck();
    }

}
