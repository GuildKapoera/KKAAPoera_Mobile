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

        rotacaoRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    itemList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Crie um objeto RotacaoQuinzenalWD a partir dos dados do Firestore
                        RotacaoQuinzenalFERU item = document.toObject(RotacaoQuinzenalFERU.class);
                        if (item.getBoss().equals("feru")) {
                            item.setRotacaoID(document.getId());
                            itemList.add(item);
                        }
                    }
                    // Ordenar a lista com base na data de evento
                    Collections.sort(itemList, new Comparator<RotacaoQuinzenalFERU>() {
                        @Override
                        public int compare(RotacaoQuinzenalFERU item1, RotacaoQuinzenalFERU item2) {
                            return item1.getData().compareTo(item2.getData());
                        }
                    });
                    // Notifique o adaptador que os dados foram atualizados
                    adapter.notifyDataSetChanged();

                    // Verifique se a lista de itens está vazia após a atualização
                    if (itemList.isEmpty()) {
                        Toast.makeText(RotacaoQuinzenalListaFERUActivity.this, "Nenhuma Rotação para Feru encontrada.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RotacaoQuinzenalListaFERUActivity.this, "Erro ao carregar Rotação.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}