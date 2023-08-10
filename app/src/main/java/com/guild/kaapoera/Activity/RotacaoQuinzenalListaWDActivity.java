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

public class RotacaoQuinzenalListaWDActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RotacaoQuinzenalListaWDAdapter adapter;
    private List<RotacaoQuinzenalWD> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotacaoquinzenal_lista_wd);

        recyclerView = findViewById(R.id.recyclerView); // Replace with your RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        adapter = new RotacaoQuinzenalListaWDAdapter(this, itemList);
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
                        RotacaoQuinzenalWD item = document.toObject(RotacaoQuinzenalWD.class);
                        itemList.add(item);
                    }
                    // Notifique o adaptador que os dados foram atualizados
                    adapter.notifyDataSetChanged();

                    // Verifique se a lista de itens está vazia após a atualização
                    if (itemList.isEmpty()) {
                        Toast.makeText(RotacaoQuinzenalListaWDActivity.this, "Nenhuma Rotação para WD encontrada.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RotacaoQuinzenalListaWDActivity.this, "Erro ao carregar Rotação.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}