package com.guild.kaapoera.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.guild.kaapoera.Model.Usuario;
import com.guild.kaapoera.R;
import com.guild.kaapoera.Util.Config_Bd;

public class LoginActivity extends AppCompatActivity {

    EditText campoEmail, campoSenha;
    Button BotaoLogar;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = Config_Bd.AutenticacaoFirebase();
        db = FirebaseFirestore.getInstance();
        inicializarComponentes();
    }

    //validação de componentes
    public void validarautenticacao(View view) {
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                Usuario usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setSenha(senha);

                logar(usuario);

            } else {
                Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
        }
    }

    private void logar(Usuario usuario) {
        auth.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    verificarInformacoesFirestore();
                } else {
                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        excecao = "Usuário não cadastrado";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Email ou Senha inválidos";
                    } catch (Exception e) {
                        excecao = "Erro ao logar o usuário" + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verificarInformacoesFirestore() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DocumentReference userRef = db.collection("Usuarios").document(uid);

            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String guildName = document.getString("guildName");
                            String sex = document.getString("sex");
                            int level = document.getLong("level").intValue();
                            String nomePersonagem = document.getString("nomePersonagem");
                            String nomeUsuario = document.getString("nomeUsuario");
                            String telefone = document.getString("telefone");
                            String vocacao = document.getString("vocacao");
                            String world = document.getString("world");

                            if (guildName != null && sex != null && level != 0 && nomePersonagem != null && nomeUsuario != null &&
                                    telefone != null && vocacao != null && world != null) {
                                abrirBemVindo();
                            } else {
                                abrirVerificarPersonagem();
                            }
                        } else {
                            abrirVerificarPersonagem();
                        }
                    } else {
                        abrirVerificarPersonagem();
                    }
                }
            });
        }
    }

    private void abrirVerificarPersonagem() {
        Intent i = new Intent(LoginActivity.this, VerificarCharacterActivity.class);
        startActivity(i);
    }

    private void abrirBemVindo() {
        Intent i = new Intent(LoginActivity.this, BemVindoActivity.class);
        startActivity(i);
    }

    public void cadastre_se(View v) {
        Intent i = new Intent(this, CadastroActivity.class);
        startActivity(i);
    }

    public void esqueciSenha(View view) {
        Intent intent = new Intent(LoginActivity.this, EsquecerSenhaActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAuth = auth.getCurrentUser();
        if (usuarioAuth != null) {
            verificarInformacoesFirestore();
        }
    }

    //Inicialização de componentes
    private void inicializarComponentes() {
        campoEmail = findViewById(R.id.editTextEmailLogin);
        campoSenha = findViewById(R.id.editTextPasswordLogin);
        BotaoLogar = findViewById(R.id.buttonLogar);
    }
}