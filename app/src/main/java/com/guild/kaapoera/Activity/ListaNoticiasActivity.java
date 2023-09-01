package com.guild.kaapoera.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.guild.kaapoera.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaNoticiasActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNoticias;
    private NoticiasAdapter noticiasAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_noticias);

        recyclerViewNoticias = findViewById(R.id.recyclerViewNoticias);
        recyclerViewNoticias.setLayoutManager(new LinearLayoutManager(this));

        noticiasAdapter = new NoticiasAdapter(new ArrayList<>());
        recyclerViewNoticias.setAdapter(noticiasAdapter);

        db = FirebaseFirestore.getInstance();

        // Carregar notícias do Firestore
        carregarNoticiasDoFirestore();
    }

    private void carregarNoticiasDoFirestore() {
        db.collection("Noticias")
                .addSnapshotListener(this, (value, error) -> {
                    if (error != null) {
                        // Lidar com erros de carregamento, se necessário
                        return;
                    }

                    List<Noticia> listaNoticias = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : value) {
                        String titulo = documentSnapshot.getString("titulo");
                        String conteudo = documentSnapshot.getString("conteudo");
                        String autor = documentSnapshot.getString("autor");
                        String data = documentSnapshot.getString("data");

                        Noticia noticia = new Noticia(titulo, conteudo, autor, data);
                        listaNoticias.add(noticia);
                    }

                    if (!listaNoticias.isEmpty()) {
                        // Ordenar a lista de notícias por data
                        Collections.sort(listaNoticias, (noticia1, noticia2) ->
                                noticia2.getData().compareTo(noticia1.getData())
                        );

                        noticiasAdapter.setListaNoticias(listaNoticias);
                    } else {
                        exibirToast("Não há notícia ou recado registrado até o momento.");
                    }
                });
    }

    private void exibirToast(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}
