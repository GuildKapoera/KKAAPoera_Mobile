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

import java.util.Calendar;
import java.util.Date;

public class BemVindoActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bem_vindo);
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
                            String strike = documentSnapshot.getString("strike");

                            if (name != null && vocation != null && sex != null && strike != null) {
                                // Atualizar a tela com as informações do personagem
                                atualizarTela(name, vocation, sex, strike);
                            } else {
                                // Caso as informações estejam faltando, exiba uma mensagem de erro e redirecione para a tela de login
                                Toast.makeText(BemVindoActivity.this, "Erro ao exibir informações. Tente novamente.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(BemVindoActivity.this, LoginActivity.class));
                                finish();
                            }
                        } else {
                            // Caso o documento não exista, redirecione para a tela de login
                            startActivity(new Intent(BemVindoActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Caso ocorra algum erro ao obter as informações do Firestore, exiba uma mensagem de erro e redirecione para a tela de login
                        Toast.makeText(BemVindoActivity.this, "Erro ao obter informações. Tente novamente.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BemVindoActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }

    private void atualizarTela(String name, String vocation, String sex, String strike) {
        ImageView imageViewAvatar = findViewById(R.id.imageViewAvatar);
        setCharacterAvatar(imageViewAvatar, vocation, sex);

        TextView textViewCharacterName = findViewById(R.id.textViewCharacterName);
        setCharacterName(textViewCharacterName, name);

        ImageView imageViewRotacaoQuinzenal = findViewById(R.id.imageViewRotacaoQuinzenal);
        if (!strike.isEmpty()) {
            int day = Integer.parseInt(strike.substring(0, 2));
            int month = Integer.parseInt(strike.substring(3, 5));
            int year = Integer.parseInt(strike.substring(6));

            Calendar strikeCalendar = Calendar.getInstance();
            strikeCalendar.set(year, month - 1, day);

            Calendar limitCalendar = Calendar.getInstance();
            limitCalendar.setTime(strikeCalendar.getTime());
            limitCalendar.add(Calendar.DAY_OF_MONTH, 20);

            boolean isBeforeLimit = new Date().before(limitCalendar.getTime());

            if (isBeforeLimit) {
                imageViewRotacaoQuinzenal.setImageResource(R.drawable.baseline_event_repeat_24_strikado);
                imageViewRotacaoQuinzenal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mensagem = name + " vacilão, fica de fora da próxima rotação";
                        Toast.makeText(BemVindoActivity.this, mensagem, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                imageViewRotacaoQuinzenal.setImageResource(R.drawable.baseline_event_repeat_24);
                imageViewRotacaoQuinzenal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        abrirRotacaoQuinzenal(view);
                    }
                });
            }
        } else {
            imageViewRotacaoQuinzenal.setImageResource(R.drawable.baseline_event_repeat_24);
            imageViewRotacaoQuinzenal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    abrirRotacaoQuinzenal(view);
                }
            });
        }
    }

            private void setCharacterAvatar (ImageView imageView, String vocation, String sex){
                String avatarName = getAvatarName(vocation, sex);

                int resourceId = getResources().getIdentifier(avatarName, "drawable", getPackageName());
                if (resourceId != 0) {
                    imageView.setImageResource(resourceId);
                } else {
                    // Caso a imagem não seja encontrada, definir um avatar padrão
                    imageView.setImageResource(R.drawable.default_avatar);
                }
            }

            private String getAvatarName (String vocation, String sex){
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

            private void setCharacterName (TextView textView, String name){
                SpannableString spannableName = new SpannableString(name.toUpperCase());
                spannableName.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableName.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 0, spannableName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(spannableName);
            }

            // Método para deslogar o usuário
            public void deslogar (View view){
                try {
                    auth.signOut();
                    startActivity(new Intent(BemVindoActivity.this, LoginActivity.class));
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            public void verPerfildeUsuario (View view){
                Intent intent = new Intent(this, PerfildeUsuarioActivity.class);
                startActivity(intent);
                finish();
            }
            public void abrirCompraEVenda (View view){
                Intent intent = new Intent(this, CompraEVendaActivity.class);
                startActivity(intent);
                finish();
            }
            public void abrirQueroPT (View view){
                Intent intent = new Intent(this, QueroPTActivity.class);
                startActivity(intent);
                finish();
            }
            public void abrirRotacaoQuinzenal (View view){
                Intent intent = new Intent(this, RotacaoQuinzenal.class);
                startActivity(intent);
                finish();
            }
    @Override
    protected void onResume() {
        super.onResume();
        if (userId != null) {
            obterInformacoesDoFirestore(userId);
        }
    }

}
