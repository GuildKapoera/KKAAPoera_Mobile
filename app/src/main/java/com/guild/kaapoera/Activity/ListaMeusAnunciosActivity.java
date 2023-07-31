package com.guild.kaapoera.Activity;

import android.os.Bundle;

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
import com.google.firebase.firestore.QuerySnapshot;
import com.guild.kaapoera.R;

import java.util.ArrayList;
import java.util.List;

public class ListaMeusAnunciosActivity extends AppCompatActivity implements AnuncioGerenciarAdapter.OnAnuncioClickListener {

    private RecyclerView recyclerViewMeusAnuncios;
    private AnuncioGerenciarAdapter anuncioAdapter;
    private List<AnuncioGerenciar> meusAnuncios;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private String nomePersonagemUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_meus_anuncios);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        recyclerViewMeusAnuncios = findViewById(R.id.recyclerViewMeusAnuncios);
        recyclerViewMeusAnuncios.setLayoutManager(new LinearLayoutManager(this));
        meusAnuncios = new ArrayList<>();
        anuncioAdapter = new AnuncioGerenciarAdapter(this, meusAnuncios, this);
        recyclerViewMeusAnuncios.setAdapter(anuncioAdapter);

        // Obter o ID do usuário logado
        if (currentUser != null) {
            String userID = currentUser.getUid();
            // Buscar o campo nomePersonagem do documento do usuário no Firestore na coleção Usuarios
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference usuariosRef = db.collection("Usuarios");
            usuariosRef.document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            // Obter o nomePersonagem do documento do usuário
                            nomePersonagemUsuario = document.getString("nomePersonagem");
                            // Buscar os anúncios do usuário
                            loadMeusAnunciosFromFirestore();
                        }
                    }
                }
            });
        }
    }

    private void loadMeusAnunciosFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference anunciosRef = db.collection("Anuncios");

        // Consultar para obter os anúncios criados pelo usuário logado usando o nomePersonagem como filtro
        Query query = anunciosRef.whereEqualTo("nomePersonagem", nomePersonagemUsuario);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    meusAnuncios.clear();
                    for (DocumentSnapshot document : task.getResult()) {
                        // Crie um objeto AnuncioGerenciar a partir dos dados do Firestore
                        AnuncioGerenciar anuncio = document.toObject(AnuncioGerenciar.class);
                        // Obter o ID do anúncio (nome gerado automaticamente)
                        anuncio.setUid(document.getId());
                        meusAnuncios.add(anuncio);
                    }
                    // Notifique o adaptador que os dados foram atualizados
                    anuncioAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onAnuncioClick(int position) {
        // Implemente aqui a lógica para excluir o anúncio clicado
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference anunciosRef = db.collection("Anuncios");

        AnuncioGerenciar anuncio = meusAnuncios.get(position);
        String anuncioID = anuncio.getUid();

        // Excluir o anúncio do Firestore
        anunciosRef.document(anuncioID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Remover o anúncio da lista local
                    meusAnuncios.remove(position);
                    // Notificar o adaptador sobre a exclusão
                    anuncioAdapter.notifyItemRemoved(position);
                } else {
                    // Exibir mensagem de erro ou tratamento de erro aqui, se necessário
                }
            }
        });
    }
}
