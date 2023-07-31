package com.guild.kaapoera.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.guild.kaapoera.R;

public class PerfildeUsuarioActivity extends AppCompatActivity {

    private ImageView imageViewAvatar;
    private TextView textViewCharacterName;
    private TextView textViewVocation;
    private TextView textViewLevel;
    private TextView textViewUserEmail;
    private TextView textViewUserPhone;
    private TextView textViewUserName;
    private Button buttonAtualizarDadosPessoais;
    private Button buttonDesvincular;
    private Button buttonPaginaPrincipal;

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_de_usuario);

        // Inicializar as referências aos elementos visuais
        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        textViewCharacterName = findViewById(R.id.textViewCharacterName);
        textViewVocation = findViewById(R.id.textViewVocation);
        textViewLevel = findViewById(R.id.textViewLevel);
        textViewUserEmail = findViewById(R.id.textViewUserEmail);
        textViewUserPhone = findViewById(R.id.textViewUserPhone);
        textViewUserName = findViewById(R.id.textViewUserName);
        buttonAtualizarDadosPessoais = findViewById(R.id.buttonAtualizarDadosPessoais);
        buttonDesvincular = findViewById(R.id.buttonDesvincular);
        buttonPaginaPrincipal = findViewById(R.id.buttonPaginaPrincipal);

        // Inicializar a instância do Firestore e do Firebase Auth
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Recuperar o usuário atualmente autenticado
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // Recuperar o email do usuário
            String userEmail = currentUser.getEmail();

            // Exibir o email do usuário na tela
            textViewUserEmail.setText("E-mail: " + userEmail);
        } else {
            Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
        }

        // Recuperar o ID do usuário logado
        String uid = auth.getCurrentUser().getUid();

        // Recuperar as informações do usuário no Firestore
        firestore.collection("Usuarios").document(uid).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            // Recuperar as informações do documento do usuário
                            String characterName = document.getString("nomePersonagem");
                            String vocation = document.getString("vocacao");
                            String sex = document.getString("sex");
                            int level = document.getLong("level").intValue();
                            String userPhone = document.getString("telefone");
                            String userName = document.getString("nomeUsuario");

                            // Atualizar as informações na tela
                            textViewCharacterName.setText(characterName);
                            textViewVocation.setText("Vocação: " + vocation);
                            textViewLevel.setText("Level: " + level);
                            textViewUserPhone.setText("Telefone: " + userPhone);
                            textViewUserName.setText("Nome do Usuário: " + userName);

                            // Definir o avatar com base na vocação e sexo
                            definirAvatar(vocation, sex);
                        } else {
                            Toast.makeText(this, "Documento não encontrado.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Erro ao recuperar informações do usuário.", Toast.LENGTH_SHORT).show();
                    }
                });

        // Definir o clique do botão "Desvincular Char", "pagina principal" e "atualizar dados"
        buttonDesvincular.setOnClickListener(view -> showConfirmationDialog());
        //buttonAtualizarDadosPessoais.setOnClickListener(view -> abrirAtualizarDadosPessoais());
        buttonPaginaPrincipal.setOnClickListener(view -> voltarParaPaginaPrincipal());
        //Definindo clique para abrir Atualizar Dados
        buttonAtualizarDadosPessoais.setOnClickListener(view -> abrirAtualizarDados());

    }

    private void voltarParaPaginaPrincipal() {
        startActivity(new Intent(this, BemVindoActivity.class));
        finish();
    }
    private void abrirAtualizarDados() {
        startActivity(new Intent(this, AtualizarDadosActivity.class));
        finish();
    }
    private void definirAvatar(String vocation, String sex) {
        String avatarResourceName = getAvatarResourceName(vocation, sex);
        int avatarResourceId = getResources().getIdentifier(
                avatarResourceName,
                "drawable",
                getPackageName()
        );

        if (avatarResourceId != 0) {
            imageViewAvatar.setImageResource(avatarResourceId);
        } else {
            imageViewAvatar.setImageResource(R.drawable.default_avatar);
        }
    }

    private String getAvatarResourceName(String vocation, String sex) {
        String avatarResourceName = "";

        if (vocation != null && sex != null) {
            if (sex.equals("male")) {
                if (vocation.equals("Royal Paladin")) {
                    avatarResourceName = "rp_male";
                } else if (vocation.equals("Elite Knight")) {
                    avatarResourceName = "ek_male";
                } else if (vocation.equals("Elder Druid")) {
                    avatarResourceName = "ed_male";
                } else if (vocation.equals("Master Sorcerer")) {
                    avatarResourceName = "ms_male";
                }
            } else if (sex.equals("female")) {
                if (vocation.equals("Royal Paladin")) {
                    avatarResourceName = "rp_female";
                } else if (vocation.equals("Elite Knight")) {
                    avatarResourceName = "ek_female";
                } else if (vocation.equals("Elder Druid")) {
                    avatarResourceName = "ed_female";
                } else if (vocation.equals("Master Sorcerer")) {
                    avatarResourceName = "ms_female";
                }
            }
        }

        return avatarResourceName;
    }

    // Método para exibir o diálogo de confirmação
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Desvincular Personagem");
        builder.setMessage("Tem certeza que deseja desvincular o personagem? Essa ação não pode ser desfeita.");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Chamamos a função para apagar o documento
                apagarDocumentoDoUsuario();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Caso o usuário clique em "Não", não fazemos nada
            }
        });
        builder.show();
    }

    // Método para apagar o documento do usuário no Firestore
    private void apagarDocumentoDoUsuario() {
        String uid = auth.getCurrentUser().getUid();
        firestore.collection("Usuarios").document(uid).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PerfildeUsuarioActivity.this, "Documento apagado com sucesso.", Toast.LENGTH_SHORT).show();
                        // Após apagar o documento, retornamos para a tela de VerificarCharacterActivity
                        startActivity(new Intent(PerfildeUsuarioActivity.this, VerificarCharacterActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Caso ocorra algum erro, exibir mensagem de erro
                        Toast.makeText(PerfildeUsuarioActivity.this, "Erro ao apagar o documento. Tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // TODO: Implementar as ações dos botões "Atualizar Dados Pessoais" e "Página Principal"
}
