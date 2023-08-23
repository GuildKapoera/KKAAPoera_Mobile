package com.guild.kaapoera.Activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.guild.kaapoera.R;

import java.util.ArrayList;
import java.util.List;

public class QueroPTListaGeralActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QueroPTGeralAdapter adapter;
    private List<QueroPTGeral> queroPTList;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quero_pt_lista_geral);

        // Inicializar o Firestore
        firestore = FirebaseFirestore.getInstance();

        // Inicializar a lista de QueroPTGeral
        queroPTList = new ArrayList<>();

        // Configurar o RecyclerView e o Adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QueroPTGeralAdapter(this, queroPTList);
        recyclerView.setAdapter(adapter);

        // Obter os dados do Firestore
        getQueroPTData();
    }

    private void getQueroPTData() {
        firestore.collection("QueroPT")
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("QueroPTListaGeralActivity", "Erro ao obter dados do Firestore: " + error.getMessage());
                            Toast.makeText(QueroPTListaGeralActivity.this, "Erro ao obter dados do Firestore.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                            queroPTList.clear();
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                QueroPTGeral queroPT = document.toObject(QueroPTGeral.class);
                                queroPT.setPTid(document.getId());
                                queroPTList.add(queroPT);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(QueroPTListaGeralActivity.this, "Nenhuma PT encontrada.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}