package com.guild.kaapoera.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.guild.kaapoera.R;

import java.util.ArrayList;
import java.util.List;

public class QueroPTListaMinhasPTsActivity extends AppCompatActivity implements QueroPTMinhasPtsAdapter.onPTClickListener {

    private RecyclerView recyclerView;
    private QueroPTMinhasPtsAdapter adapter;
    private List<QueroPTMinhasPts> queroPTList;
    private FirebaseFirestore firestore;
    private String nomedoResponsavel;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quero_pt_minhas_pts);

        // Inicializar o Firestore e Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        // Inicializar a lista de QueroPTMinhasPts
        queroPTList = new ArrayList<>();

        // Configurar o RecyclerView e o Adapter
        recyclerView = findViewById(R.id.recyclerViewMinhasPTs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QueroPTMinhasPtsAdapter(this, queroPTList, this);
        recyclerView.setAdapter(adapter);

        // Obter os dados do Firestore
        // Obter o ID do usuário logado
        if (currentUser != null) {
            String userID = currentUser.getUid();
            // Buscar o campo nomePersonagem do documento do usuário no Firestore na coleção Usuarios
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference usuariosRef = db.collection("Usuarios");
            usuariosRef.document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            // Obter o nomePersonagem do documento do usuário
                            nomedoResponsavel = document.getString("nomePersonagem");
                            // Buscar os anúncios do usuário
                            getQueroPTData();
                        } else {
                            Log.e("QueroPTListaMinhasPTsActivity", "Documento do usuário não encontrado.");
                        }
                    } else {
                        Log.e("QueroPTListaMinhasPTsActivity", "Erro ao obter documento do usuário: " + task.getException());
                    }
                }
            });
        } else {
            // Caso o usuário não esteja logado
            // Realize alguma ação adequada, como redirecionar para a tela de login
            // Exemplo: startActivity(new Intent(this, LoginActivity.class));
            // Ou mostre uma mensagem de erro para o usuário
            Toast.makeText(this, "Usuário não está logado.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPTclick(int position) {
        // Implemente aqui a lógica para excluir a PT clicado
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference ptRef = db.collection("QueroPT");

        QueroPTMinhasPts minhasPts = queroPTList.get(position);
        String ptId = minhasPts.getPTid();

        //Excluir PT do Firebase
        ptRef.document(ptId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    //Remover PT da lista local
                    queroPTList.remove(position);
                    adapter.notifyItemRemoved(position);

                } else {
                    // Lidar com erro na exclusão
                }
            }
        });
    }

    private void getQueroPTData() {
        // Aqui você deve fazer a consulta no Firestore para obter os dados da coleção QueroPT
        // e preencher a lista queroPTList com os objetos QueroPTMinhasPts.

        // Vou fornecer um exemplo simples de consulta para a coleção "queroPT" (use os nomes corretos do seu Firestore)
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference ptsRef = db.collection("QueroPT");

        // Consultar para obter os anúncios criados pelo usuário logado usando o nomePersonagem como filtro
        Query query = ptsRef.whereEqualTo("Responsavel", nomedoResponsavel);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    queroPTList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Crie um objeto QueroPTMinhasPts a partir dos dados do Firestore
                        QueroPTMinhasPts queroPT = document.toObject(QueroPTMinhasPts.class);
                        // Obter o ID da PT (nome gerado automaticamente)
                        queroPT.setPTid(document.getId());
                        queroPTList.add(queroPT);
                    }
                    // Notifique o adaptador que os dados foram atualizados
                    adapter.notifyDataSetChanged();

                    // Verifique se a lista está vazia e exiba o Toast caso esteja
                    if (queroPTList.isEmpty()) {
                        Toast.makeText(QueroPTListaMinhasPTsActivity.this,
                                "Nenhuma PT criada por você foi encontrada.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("QueroPTListaMinhasPTsActivity", "Erro ao obter dados do Firestore: " + task.getException());
                }
            }
        });
    }
}
