package com.guild.kaapoera.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.guild.kaapoera.R;

import java.util.HashMap;
import java.util.Map;

public class CharacterDetailsActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText editTextUserName;
    private EditText editTextUserPhone;
    private Button buttonVincular;

    private String name;
    private String vocation;
    private int level;
    private String world;
    private String guildName;
    private String comment;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);

        // Recuperar os dados passados pela MainActivity
        name = getIntent().getStringExtra("name");
        sex = getIntent().getStringExtra("sex");
        vocation = getIntent().getStringExtra("vocation");
        level = getIntent().getIntExtra("level", 0);
        world = getIntent().getStringExtra("world");
        guildName = getIntent().getStringExtra("guildName");
        comment = getIntent().getStringExtra("comment");

        // Inicializar as variáveis das caixas de texto e do botão
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextUserPhone = findViewById(R.id.editTextUserPhone);
        buttonVincular = findViewById(R.id.buttonVincular);

        // Inicializar a instância do Firebase Auth
        auth = FirebaseAuth.getInstance();

        buttonVincular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarInformacoesParaFirestore();
            }
        });

        // Atualizar os TextViews do layout com os dados do personagem
        TextView textViewCharacterName = findViewById(R.id.textViewCharacterName);
        TextView textViewCharacterSex = findViewById(R.id.textViewCharacterSex);
        TextView textViewCharacterVocation = findViewById(R.id.textViewCharacterVocation);
        TextView textViewCharacterLevel = findViewById(R.id.textViewCharacterLevel);
        TextView textViewCharacterWorld = findViewById(R.id.textViewCharacterWorld);
        TextView textViewGuildName = findViewById(R.id.textViewGuildName);
        TextView textViewComment = findViewById(R.id.textViewComment);

        // Criar SpannableString para diferentes cores no texto
        SpannableString characterNameSpannable = new SpannableString("Character Name: " + name);
        characterNameSpannable.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        characterNameSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.kap_gold)), 15, characterNameSpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString characterSexSpannable = new SpannableString("Character Sex: " + sex);
        characterSexSpannable.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        characterSexSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.kap_gold)), 14, characterSexSpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString characterVocationSpannable = new SpannableString("Character Vocation: " + vocation);
        characterVocationSpannable.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        characterVocationSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.kap_gold)), 19, characterVocationSpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString characterLevelSpannable = new SpannableString("Character Level: " + level);
        characterLevelSpannable.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        characterLevelSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.kap_gold)), 17, characterLevelSpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString characterWorldSpannable = new SpannableString("Character World: " + world);
        characterWorldSpannable.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        characterWorldSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.kap_gold)), 17, characterWorldSpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString guildNameSpannable = new SpannableString("Guild Name: " + guildName);
        guildNameSpannable.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        guildNameSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.kap_gold)), 12, guildNameSpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString commentSpannable = new SpannableString("Comment: " + comment);
        commentSpannable.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        commentSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.kap_gold)), 9, commentSpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Definir o texto com SpannableString
        textViewCharacterName.setText(characterNameSpannable);
        textViewCharacterSex.setText(characterSexSpannable);
        textViewCharacterVocation.setText(characterVocationSpannable);
        textViewCharacterLevel.setText(characterLevelSpannable);
        textViewCharacterWorld.setText(characterWorldSpannable);
        textViewGuildName.setText(guildNameSpannable);
        textViewComment.setText(commentSpannable);
    }

    private void enviarInformacoesParaFirestore() {
        String userName = editTextUserName.getText().toString().trim();
        String userPhone = editTextUserPhone.getText().toString().trim();

        // Verifica se o nome de usuário e o telefone não estão vazios antes de enviar para o Firestore
        if (!userName.isEmpty() && !userPhone.isEmpty()) {
            // Crie um objeto HashMap para armazenar as informações do usuário
            Map<String, Object> usuario = new HashMap<>();
            usuario.put("nomePersonagem", name);
            usuario.put("sex", sex);
            usuario.put("vocacao", vocation);
            usuario.put("level", level);
            usuario.put("world", world);
            usuario.put("guildName", guildName);
            usuario.put("nomeUsuario", userName);
            usuario.put("telefone", userPhone);

            // Obtenha a referência do Firestore para a coleção "Usuarios"
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String uid = auth.getCurrentUser().getUid();
            DocumentReference documentReference = db.collection("Usuarios").document(uid);

            // Envie as informações para o Firestore
            documentReference.set(usuario)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(CharacterDetailsActivity.this, "Informações vinculadas com sucesso!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CharacterDetailsActivity.this, BemVindoActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Adicionar a flag para limpar as atividades anteriores da pilha
                            startActivity(intent);
                            finish(); // Finalizar a atividade atual (CharacterDetailsActivity) para que ela não fique na pilha
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CharacterDetailsActivity.this, "Erro ao vincular informações. Tente novamente.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(CharacterDetailsActivity.this, "Preencha todos os campos antes de vincular.", Toast.LENGTH_SHORT).show();
        }
    }
}

