package com.guild.kaapoera.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.guild.kaapoera.R;

public class CompraEVendaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_e_venda);

        // ...
    }

    // Método que será chamado quando a ImageButton "Criar Anúncio" for clicada
    public void abrirCriarAnuncio(View view) {
        Intent intent = new Intent(this, CriarAnuncioActivity.class);
        startActivity(intent);
    }

    // Método que será chamado quando a ImageButton "Ver Meus Anúncios" for clicada
    public void gerenciarMeusAnuncios(View view) {
        Intent intent = new Intent(this, ListaMeusAnunciosActivity.class);
        startActivity(intent);
    }

    // Método que será chamado quando a ImageButton "Ver Todos Anúncios" for clicada
    public void abrirTodosAnuncios(View view) {
        Intent intent = new Intent(this, ListaAnuncioGeralActivity.class);
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
