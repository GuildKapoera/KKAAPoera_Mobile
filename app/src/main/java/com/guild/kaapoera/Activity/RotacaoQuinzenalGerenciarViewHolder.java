package com.guild.kaapoera.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.guild.kaapoera.R;

import java.util.ArrayList;
import java.util.List;

public class RotacaoQuinzenalGerenciarViewHolder extends AppCompatActivity {

    private List<RotacaoQuinzenalGerenciar> itemList;
    private RecyclerView recyclerView;
    private RotacaoQuinzenalGerenciarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotacaoquinzenal_gerenciar_layout);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        adapter = new RotacaoQuinzenalGerenciarAdapter(itemList, RotacaoQuinzenalGerenciarViewHolder.this);
        recyclerView.setAdapter(adapter);

        // Carregar dados da rotação quinzenal no Firestore
        loadRotacaoQuinzenalFromFirestore();
    }

    private void loadRotacaoQuinzenalFromFirestore() {
        // Inicialize o Firebase Firestore
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Referência à coleção "RotacaoQuinzenal" no Firestore
        CollectionReference rotacaoQuinzenalRef = firestore.collection("RotacaoQuinzenal");

        // Obtenha o UID do usuário logado
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Consulte o documento do usuário para obter o nome do personagem
        firestore.collection("Usuarios").document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String nomePersonagem = task.getResult().getString("nomePersonagem");

                // Consulta para obter os documentos da coleção onde o campo "responsavel" é igual ao nome do personagem
                rotacaoQuinzenalRef.whereEqualTo("responsavel", nomePersonagem).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            itemList.clear(); // Limpa a lista antes de adicionar novos itens

                            // Itera sobre os documentos retornados da consulta
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RotacaoQuinzenalGerenciar item = document.toObject(RotacaoQuinzenalGerenciar.class);
                                itemList.add(item);
                            }

                            // Classifica a lista com base na data ou outro critério, se necessário
                            // Collections.sort(itemList, new MyComparator());

                            adapter.notifyDataSetChanged(); // Notifica o adapter sobre a mudança nos dados
                        } else {
                            // Trate erros de consulta
                            Toast.makeText(RotacaoQuinzenalGerenciarViewHolder.this, "Erro ao carregar os dados.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                // Trate erros na obtenção do documento do usuário
            }
        });
    }
}
