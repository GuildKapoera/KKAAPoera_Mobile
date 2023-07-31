package com.guild.kaapoera.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.guild.kaapoera.R;

public class QueroPTActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queropt);

        // ...
    }

    // Método que será chamado quando a ImageButton "Criar PT" for clicada
    public void abrirQueroPTCriarPT(View view) {
        Intent intent = new Intent(this, QueroPTCriarPTActivity.class);
        startActivity(intent);
    }

    // Método que será chamado quando a ImageButton "Ver Minhas PTs" for clicada
    //public void abrirQueroPTMinhasPTs(View view) {
    //    Intent intent = new Intent(this, QueroPTMinhasPTsActivity.class);
    //    startActivity(intent);
    //}

    // Método que será chamado quando a ImageButton "Ver Todas PTs" for clicada
    public void abrirQueroPTGeral(View view) {
        Intent intent = new Intent(this, QueroPTListaGeralActivity.class);
        startActivity(intent);
    }

    // Método que será chamado quando o botão "Voltar à Página Principal" for clicado
    public void voltarPaginaPrincipal(View view) {
        Intent intent = new Intent(this, BemVindoActivity.class);
        startActivity(intent);
        finish();
    }

    // ...
}
