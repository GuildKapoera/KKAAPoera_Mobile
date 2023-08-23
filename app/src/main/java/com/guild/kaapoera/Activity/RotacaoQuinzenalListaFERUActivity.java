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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RotacaoQuinzenalListaFERUActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RotacaoQuinzenalListaFERUAdapter adapter;
    private List<RotacaoQuinzenalFERU> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotacaoquinzenal_lista_feru);

        recyclerView = findViewById(R.id.recyclerView); // Replace with your RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        adapter = new RotacaoQuinzenalListaFERUAdapter(this, itemList);
        recyclerView.setAdapter(adapter);

        // Carregar dados da coleção RotacaoQuinzenal do Firestore
        loadRotacaoQuinzenalFromFirestore();
    }

    private void loadRotacaoQuinzenalFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference rotacaoRef = db.collection("RotacaoQuinzenal");

        rotacaoRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(RotacaoQuinzenalListaFERUActivity.this, "Erro ao carregar Rotação.", Toast.LENGTH_SHORT).show();
                    return;
                }

                itemList.clear();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    RotacaoQuinzenalFERU item = document.toObject(RotacaoQuinzenalFERU.class);
                    if (item.getBoss().equals("feru")) {
                        item.setRotacaoID(document.getId());
                        itemList.add(item);
                    }
                }

                Collections.sort(itemList, new Comparator<RotacaoQuinzenalFERU>() {
                    @Override
                    public int compare(RotacaoQuinzenalFERU item1, RotacaoQuinzenalFERU item2) {
                        return item1.getData().compareTo(item2.getData());
                    }
                });

                adapter.notifyDataSetChanged();

                if (itemList.isEmpty()) {
                    Toast.makeText(RotacaoQuinzenalListaFERUActivity.this, "Nenhuma Rotação para Feru encontrada.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}