package com.guild.kaapoera.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.guild.kaapoera.R;

public class EsquecerSenhaActivity extends AppCompatActivity {

    private EditText editTextEmailRecuperacao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esquecer_senha);

        editTextEmailRecuperacao = findViewById(R.id.editTextRecuperarEmail);

        Button buttonRecuperarSenha = findViewById(R.id.buttonRecuperarSenha);
        buttonRecuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarSenha();
            }
        });
    }

    private void recuperarSenha() {
        String email = editTextEmailRecuperacao.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            editTextEmailRecuperacao.setError("Digite seu email");
            return;
        }

        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EsquecerSenhaActivity.this, "Email de recuperação de senha enviado.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EsquecerSenhaActivity.this, "Email não encontrado.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}