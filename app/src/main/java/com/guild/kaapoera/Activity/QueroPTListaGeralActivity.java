package com.guild.kaapoera.Activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
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
        // Aqui você deve fazer a consulta no Firestore para obter os dados da coleção QueroPT
        // e preencher a lista queroPTList com os objetos QueroPTGeral.

        // Vou fornecer um exemplo simples de consulta para a coleção "queroPT" (use os nomes corretos do seu Firestore)
        firestore.collection("QueroPT")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // Limpar a lista antes de adicionar os novos itens
                            queroPTList.clear();

                            // Iterar sobre os documentos da consulta e adicionar à lista
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                QueroPTGeral queroPT = document.toObject(QueroPTGeral.class);
                                queroPT.setPTid(document.getId());
                                queroPTList.add(queroPT);
                            }

                            // Notificar o adapter que os dados foram atualizados
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(QueroPTListaGeralActivity.this, "Nenhuma PT encontrada.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("QueroPTListaGeralActivity", "Erro ao obter dados do Firestore: " + e.getMessage());
                        Toast.makeText(QueroPTListaGeralActivity.this, "Erro ao obter dados do Firestore.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}