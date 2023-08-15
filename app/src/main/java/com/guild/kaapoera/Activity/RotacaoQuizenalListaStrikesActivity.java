package com.guild.kaapoera.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.guild.kaapoera.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RotacaoQuizenalListaStrikesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RotacaoQuizenalListaStrikesAdapter adapter;
    private List<RotacaoQuizenalListaStrikes> strikesList = new ArrayList<>();
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotacaoquinzenal_lista_strikes_ativos);

        recyclerView = findViewById(R.id.recyclerViewStrikesAtivos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestore = FirebaseFirestore.getInstance();

        adapter = new RotacaoQuizenalListaStrikesAdapter(strikesList, this);

        recyclerView.setAdapter(adapter);

        // Obtenha os documentos da coleção "Usuarios" do Firebase Firestore
        firestore.collection("Usuarios").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                            RotacaoQuizenalListaStrikes strike = documentChange.getDocument().toObject(RotacaoQuizenalListaStrikes.class);

                            // Verifique se o campo "strike" está preenchido e a data é válida
                            if (strike.getStrike() != null && isValidStrike(strike.getStrike())) {
                                strikesList.add(strike);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                    // Verifique se não há itens na lista e mostre o Toast apropriado
                    if (strikesList.isEmpty()) {
                        Toast.makeText(this, "Nenhum membro com Strike para listar.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private boolean isValidStrike(String strikeDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date strike = sdf.parse(strikeDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(strike);
            calendar.add(Calendar.DAY_OF_MONTH, 20);

            Date validUntil = calendar.getTime();
            Date currentDate = new Date();

            return currentDate.before(validUntil);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
