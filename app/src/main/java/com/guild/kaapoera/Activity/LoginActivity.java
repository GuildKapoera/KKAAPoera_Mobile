package com.guild.kaapoera.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private RequestQueue requestQueue;
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
        requestQueue = Volley.newRequestQueue(this);
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
                            Timestamp ultimaAtualizacao = document.getTimestamp("ultimaAtualizacao");
                            String nomePersonagem1 = document.getString("nomePersonagem");

                            Date currentDate = new Date();
                            long diffMillis = currentDate.getTime() - ultimaAtualizacao.toDate().getTime();
                            long diffHours = diffMillis / (60 * 60 * 1000);

                            if (diffHours >= 3) {
                                // Atualiza os dados via API
                                chamarAPIAtualizarDados(nomePersonagem1);
                            } else {
                                // Lê os dados atualizados e verifica condições
                                String guildName = document.getString("guildName");
                                String sex = document.getString("sex");
                                int level = document.getLong("level").intValue();
                                String nomePersonagem = document.getString("nomePersonagem");
                                String nomeUsuario = document.getString("nomeUsuario");
                                String telefone = document.getString("telefone");
                                String vocacao = document.getString("vocacao");
                                String world = document.getString("world");

                                if (guildName.equals("Kapoera") && sex != null && level != 0 && nomePersonagem != null &&
                                        nomeUsuario != null && telefone != null && vocacao != null && world != null) {
                                    abrirBemVindo();
                                } else {
                                    abrirVerificarPersonagem();
                                }
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

    private void chamarAPIAtualizarDados(String nomePersonagem) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Atualizando dados...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String apiUrl = "https://api.tibiadata.com/v3/character/" + Uri.encode(nomePersonagem);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject characterObject = response.getJSONObject("characters").getJSONObject("character");

                            int level = characterObject.getInt("level");
                            String sex = characterObject.getString("sex");
                            String world = characterObject.getString("world");
                            JSONObject guildObject = characterObject.optJSONObject("guild");
                            String guildName = guildObject != null ? guildObject.optString("name") : "";
                            String guildRank = guildObject != null ? guildObject.optString("rank") : "";

                            // Aqui você atualiza os dados no Firestore
                            atualizarDadosNoFirestore(level, world, sex, guildName, guildRank);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Lidar com exceção
                            progressDialog.dismiss();  // Fechar ProgressDialog em caso de erro
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Lidar com erro de resposta
                        progressDialog.dismiss();  // Fechar ProgressDialog em caso de erro
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    private void atualizarDadosNoFirestore(int level, String world, String sex, String guildName, String guildRank) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DocumentReference userRef = db.collection("Usuarios").document(uid);

            Map<String, Object> updates = new HashMap<>();
            updates.put("level", level);
            updates.put("sex", sex);
            updates.put("world", world);
            updates.put("guildName", guildName);
            updates.put("rank", guildRank);
            //Atualização da data
            updates.put("ultimaAtualizacao", new Timestamp(new Date()));

            userRef.update(updates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Atualização bem-sucedida
                                verificarInformacoesFirestore();
                            } else {
                                // Lidar com erro na atualização
                            }
                            progressDialog.dismiss();  // Fechar ProgressDialog após a atualização
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

