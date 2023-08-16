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
import java.util.List;

public class ListaNoticiasActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNoticias;
    private NoticiasAdapter noticiasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_noticias);

        recyclerViewNoticias = findViewById(R.id.recyclerViewNoticias);
        recyclerViewNoticias.setLayoutManager(new LinearLayoutManager(this));

        noticiasAdapter = new NoticiasAdapter(new ArrayList<>());
        recyclerViewNoticias.setAdapter(noticiasAdapter);

        carregarNoticiasDoFirestore();
    }

    private void carregarNoticiasDoFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Noticias")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Noticia> listaNoticias = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String titulo = documentSnapshot.getString("titulo");
                        String conteudo = documentSnapshot.getString("conteudo");
                        String autor = documentSnapshot.getString("autor");
                        String data = documentSnapshot.getString("data");

                        Noticia noticia = new Noticia(titulo, conteudo, autor, data);
                        listaNoticias.add(noticia);
                    }
                    if (listaNoticias.isEmpty()) {
                        exibirToast("Não há notícia ou recado registrado até o momento.");
                    } else {
                        noticiasAdapter.setListaNoticias(listaNoticias);
                    }
                })
                .addOnFailureListener(e -> {
                    // Lidar com erros de carregamento, se necessário
                });
    }

    private void exibirToast(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}
