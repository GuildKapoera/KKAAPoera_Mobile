package com.guild.kaapoera.Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.guild.kaapoera.R;
import com.guild.kaapoera.Util.Config_Bd;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerificarCharacterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView textViewUID;
    private EditText editTextCharacterName;
    private ImageView imageViewCopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_character);
        auth = Config_Bd.AutenticacaoFirebase();

        textViewUID = findViewById(R.id.textViewUID);
        editTextCharacterName = findViewById(R.id.editTextCharacterName);
        imageViewCopy = findViewById(R.id.imageViewCopy);

        if (auth.getCurrentUser() != null) {
            String uid = auth.getCurrentUser().getUid();
            textViewUID.setText(uid);
        }

        imageViewCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyUIDToClipboard();
            }
        });
    }

    public void deslogar(View view) {
        try {
            auth.signOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent i = new Intent(VerificarCharacterActivity.this, LoginActivity.class);
        startActivity(i);
    }

    public void verificarPersonagem(View view) {
        String characterName = editTextCharacterName.getText().toString().trim();
        if (!characterName.isEmpty()) {
            searchCharacter(characterName);
        } else {
            editTextCharacterName.setError("Digite o nome do personagem");
        }
    }

    private void searchCharacter(String characterName) {
        String apiUrl = "https://api.tibiadata.com/v3/character/" + Uri.encode(characterName);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject characterObject = response.getJSONObject("characters").getJSONObject("character");

                            String name = characterObject.getString("name");
                            String vocation = characterObject.getString("vocation");
                            int level = characterObject.getInt("level");
                            String world = characterObject.getString("world");
                            JSONObject guildObject = characterObject.optJSONObject("guild");
                            String guildName = guildObject != null ? guildObject.optString("name") : "";

                            String comment = characterObject.has("comment") ? characterObject.getString("comment") : "";

                            String uid = auth.getCurrentUser().getUid();

                            if (comment.equals(uid)) {
                                if (world.equals("Descubra")) {
                                    if (!guildName.equals("Kapoera") || guildName.isEmpty()) {
                                        Toast.makeText(VerificarCharacterActivity.this, "Este personagem não é da Kapoera", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Obtendo a informação do sexo do personagem
                                        String sex = characterObject.getString("sex");

                                        // Passando a informação do sexo para a próxima tela (Bem-Vindo)
                                        Intent intent = new Intent(VerificarCharacterActivity.this, CharacterDetailsActivity.class);
                                        intent.putExtra("sex", sex);
                                        intent.putExtra("name", name);
                                        intent.putExtra("vocation", vocation);
                                        intent.putExtra("level", level);
                                        intent.putExtra("world", world);
                                        intent.putExtra("guildName", guildName);
                                        intent.putExtra("comment", comment);
                                        startActivity(intent);
                                    }
                                } else {
                                    Toast.makeText(VerificarCharacterActivity.this, "Este personagem não é da Kapoera", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(VerificarCharacterActivity.this, "Código verificador não confere", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(VerificarCharacterActivity.this, "Erro ao processar resposta da API", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VerificarCharacterActivity.this, "Erro ao fazer a chamada da API", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("accept", "application/json");
                return headers;
            }
        };

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    public void copyUIDToClipboard() {
        String uid = textViewUID.getText().toString();
        if (!uid.isEmpty()) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("UID", uid);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "UID copiado", Toast.LENGTH_SHORT).show();
        }
    }
}
