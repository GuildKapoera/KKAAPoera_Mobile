package com.guild.kaapoera.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
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

        anunciosRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    anuncios.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Crie um objeto Anuncio a partir dos dados do Firestore
                        Anuncio anuncio = document.toObject(Anuncio.class);
                        anuncios.add(anuncio);
                    }
                    // Notifique o adaptador que os dados foram atualizados
                    anuncioAdapter.notifyDataSetChanged();

                    // Verifique se a lista de anúncios está vazia após a atualização
                    if (anuncios.isEmpty()) {
                        Toast.makeText(ListaAnuncioGeralActivity.this, "Nenhum anúncio encontrado.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ListaAnuncioGeralActivity.this, "Erro ao carregar anúncios.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
