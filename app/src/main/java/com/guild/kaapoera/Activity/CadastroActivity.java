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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.guild.kaapoera.Model.Usuario;
import com.guild.kaapoera.R;
import com.guild.kaapoera.Util.Config_Bd;

public class CadastroActivity extends AppCompatActivity {

    Usuario usuario;
    FirebaseAuth autenticacao;
    EditText campoNome, campoEmail, campoSenha;
    Button BotaoCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializar();
    }
    //Inicialização de componentes
    private void inicializar(){
        campoEmail = findViewById(R.id.editTextEmail);
        campoSenha = findViewById(R.id.editTextPassword);
        BotaoCadastrar = findViewById(R.id.buttonCadastrar);
    }
    public void validarCampos(View v){
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if(!email.isEmpty()){
                if(!senha.isEmpty()){
                    usuario = new Usuario();
                    usuario.setEmail(email);
                    usuario.setSenha(senha);
    //Cadastro de Usuádio
                    cadastrarUsuario();
                }else{
                    Toast.makeText(this, "Preencha sua Senha", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(this, "Preencha o seu E-mail", Toast.LENGTH_SHORT).show();
            }

        }

    private void cadastrarUsuario() {
        autenticacao = Config_Bd.AutenticacaoFirebase();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, "Cadastro de usuário com Suceso", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CadastroActivity.this,LoginActivity.class);
                    startActivity(i);
                }else{
                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Email inválido";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao = "Usuário já existente";
                    }catch (Exception e){
                        excecao= "Erro ao cadastrar usuário"+ e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}