package com.guild.kaapoera.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.guild.kaapoera.R;

import java.util.ArrayList;
import java.util.List;

public class ListaAnuncioGeralActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAnuncios;
    private AnuncioAdapter anuncioAdapter;
    private List<Anuncio> anuncios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_anuncio_geral);

        recyclerViewAnuncios = findViewById(R.id.recyclerViewAnuncios);
        recyclerViewAnuncios.setLayoutManager(new LinearLayoutManager(this));
        anuncios = new ArrayList<>();
        anuncioAdapter = new AnuncioAdapter(this, anuncios);
        recyclerViewAnuncios.setAdapter(anuncioAdapter);

        // Carregar anúncios do Firestore
        loadAnunciosFromFirestore();
    }

    private void loadAnunciosFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference anunciosRef = db.collection("Anuncios");

        anunciosRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(ListaAnuncioGeralActivity.this, "Erro ao carregar anúncios.", Toast.LENGTH_SHORT).show();
                    return;
                }

                anuncios.clear();
                if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Anuncio anuncio = document.toObject(Anuncio.class);
                        anuncios.add(anuncio);
                    }
                    anuncioAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ListaAnuncioGeralActivity.this, "Nenhum anúncio encontrado.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
