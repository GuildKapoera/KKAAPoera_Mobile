package com.guild.kaapoera.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.guild.kaapoera.R;
import com.guild.kaapoera.Util.Config_Bd;

public class NoticiasActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);
        auth = Config_Bd.AutenticacaoFirebase();
        firestore = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
            obterInformacoesDoFirestore(userId);
        } else {
            // Caso o usuário não esteja logado, redirecione para a tela de login
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }
    private void obterInformacoesDoFirestore(String userId) {
        DocumentReference documentReference = firestore.collection("Usuarios").document(userId);
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("nomePersonagem");
                            String vocation = documentSnapshot.getString("vocacao");
                            String sex = documentSnapshot.getString("sex");

                            if (name != null && vocation != null && sex != null) {
                                // Atualizar a tela com as informações do personagem
                                atualizarTela(name, vocation, sex);
                            } else {
                                // Caso as informações estejam faltando, exiba uma mensagem de erro e redirecione para a tela de login
                                Toast.makeText(NoticiasActivity.this, "Erro ao exibir informações. Tente novamente.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(NoticiasActivity.this, LoginActivity.class));
                                finish();
                            }
                        } else {
                            // Caso o documento não exista, redirecione para a tela de login
                            startActivity(new Intent(NoticiasActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Caso ocorra algum erro ao obter as informações do Firestore, exiba uma mensagem de erro e redirecione para a tela de login
                        Toast.makeText(NoticiasActivity.this, "Erro ao obter informações. Tente novamente.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NoticiasActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }

    private void atualizarTela(String name, String vocation, String sex) {
        // Atualizar a tela com as informações do personagem
        // ...

        // Exemplo de atualização da tela
        ImageView imageViewAvatar = findViewById(R.id.imageViewAvatar);
        setCharacterAvatar(imageViewAvatar, vocation, sex);

        TextView textViewCharacterName = findViewById(R.id.textViewCharacterName);
        setCharacterName(textViewCharacterName, name);
    }

    private void setCharacterAvatar(ImageView imageView, String vocation, String sex) {
        String avatarName = getAvatarName(vocation, sex);

        int resourceId = getResources().getIdentifier(avatarName, "drawable", getPackageName());
        if (resourceId != 0) {
            imageView.setImageResource(resourceId);
        } else {
            // Caso a imagem não seja encontrada, definir um avatar padrão
            imageView.setImageResource(R.drawable.default_avatar);
        }
    }

    private String getAvatarName(String vocation, String sex) {
        String avatarName;

        if (vocation.equals("Royal Paladin")) {
            if (sex.equals("male")) {
                avatarName = "rp_male";
            } else {
                avatarName = "rp_female";
            }
        } else if (vocation.equals("Elite Knight")) {
            if (sex.equals("male")) {
                avatarName = "ek_male";
            } else {
                avatarName = "ek_female";
            }
        } else if (vocation.equals("Master Sorcerer")) {
            if (sex.equals("male")) {
                avatarName = "ms_male";
            } else {
                avatarName = "ms_female";
            }
        } else if (vocation.equals("Elder Druid")) {
            if (sex.equals("male")) {
                avatarName = "ed_male";
            } else {
                avatarName = "ed_female";
            }
        } else {
            // Vocação desconhecida, definir um avatar padrão
            avatarName = "default_avatar";
        }

        return avatarName;
    }

    private void setCharacterName(TextView textView, String name) {
        SpannableString spannableName = new SpannableString(name.toUpperCase());
        spannableName.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableName.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 0, spannableName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableName);
    }


    public void abrirEditorNoticias(View view) {
        // Código para abrir a página do editor de notícias
        Intent intent = new Intent(this, EditorNoticiasActivity.class);
        startActivity(intent);
    }

    public void abrirPaginaNoticias(View view) {
        // Código para abrir a página de notícias
        Intent intent = new Intent(this, ListaNoticiasActivity.class);
        startActivity(intent);
    }

    public void voltarPaginaPrincipal(View view) {
        // Código para voltar à página principal
        finish(); // Encerra a atividade atual
    }
}
