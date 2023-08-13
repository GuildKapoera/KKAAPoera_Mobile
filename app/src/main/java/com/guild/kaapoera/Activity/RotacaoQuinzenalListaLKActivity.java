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

public class RotacaoQuinzenalListaLKActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RotacaoQuinzenalListaLKAdapter adapter;
    private List<RotacaoQuinzenalLK> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotacaoquinzenal_lista_lk);

        recyclerView = findViewById(R.id.recyclerView); // Replace with your RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        adapter = new RotacaoQuinzenalListaLKAdapter(this, itemList);
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
                        RotacaoQuinzenalLK item = document.toObject(RotacaoQuinzenalLK.class);
                        if (item.getBoss().equals("lk")) {
                            item.setRotacaoID(document.getId());
                            itemList.add(item);
                        }
                    }
                    // Ordenar a lista com base na data de evento
                    Collections.sort(itemList, new Comparator<RotacaoQuinzenalLK>() {
                        @Override
                        public int compare(RotacaoQuinzenalLK item1, RotacaoQuinzenalLK item2) {
                            return item1.getData().compareTo(item2.getData());
                        }
                    });
                    // Notifique o adaptador que os dados foram atualizados
                    adapter.notifyDataSetChanged();

                    // Verifique se a lista de itens está vazia após a atualização
                    if (itemList.isEmpty()) {
                        Toast.makeText(RotacaoQuinzenalListaLKActivity.this, "Nenhuma Rotação para WD encontrada.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RotacaoQuinzenalListaLKActivity.this, "Erro ao carregar Rotação.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}