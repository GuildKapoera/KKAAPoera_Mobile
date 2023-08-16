package com.guild.kaapoera.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.guild.kaapoera.R;

public class DetalhesNoticiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_noticia);

        Intent intent = getIntent();
        String titulo = intent.getStringExtra("titulo");
        String conteudoHtml = intent.getStringExtra("conteudo");
        String autor = intent.getStringExtra("autor");
        String data = intent.getStringExtra("data");

        TextView tituloTextView = findViewById(R.id.textTitulo);
        TextView conteudoTextView = findViewById(R.id.textConteudo);
        TextView autorTextView = findViewById(R.id.textAutor);
        TextView dataTextView = findViewById(R.id.textData);

        tituloTextView.setText(titulo);
        autorTextView.setText("Autor: " + autor);
        dataTextView.setText("Data: " + data);

        // Converter conteúdo HTML formatado em texto legível
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            conteudoTextView.setText(Html.fromHtml(conteudoHtml, Html.FROM_HTML_MODE_COMPACT));
        } else {
            conteudoTextView.setText(Html.fromHtml(conteudoHtml));
        }
    }
}
