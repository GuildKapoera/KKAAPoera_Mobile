package com.guild.kaapoera.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.guild.kaapoera.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RotacaoQuizenalListaStrikesAdapter extends RecyclerView.Adapter<RotacaoQuizenalListaStrikesAdapter.ViewHolder> {

    private List<RotacaoQuizenalListaStrikes> strikesList;
    private Context context;
    private FirebaseAuth mAuth;

    public RotacaoQuizenalListaStrikesAdapter(List<RotacaoQuizenalListaStrikes> strikesList, Context context) {
        this.strikesList = strikesList;
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rotacaoquinzenal_lista_strikes_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RotacaoQuizenalListaStrikes strike = strikesList.get(position);

        holder.nomePersonagemTextView.setText(strike.getNomePersonagem());
        holder.strikeTextView.setText("Data do Strike: " + strike.getStrike());

        holder.excluirStrike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                String nomePersonagem = strike.getNomePersonagem();

                // Consulta para encontrar o documento com base no nome do personagem
                firestore.collection("Usuarios")
                        .whereEqualTo("nomePersonagem", nomePersonagem)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                                // Atualizar o campo "strike" para vazio no documento encontrado
                                documentSnapshot.getReference().update("strike", "")
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Sucesso ao remover o strike
                                                Toast.makeText(context, "Strike Removido com Sucesso!", Toast.LENGTH_SHORT).show();
                                                // Remova o item da lista e notifique o adaptador sobre a alteração
                                                strikesList.remove(position);
                                                notifyItemRemoved(position);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Erro ao remover o strike
                                                Toast.makeText(context, "Erro ao remover Strike. Tente novamente.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(e -> {
                            // Erro na consulta
                            Toast.makeText(context, "Erro ao consultar o banco de dados.", Toast.LENGTH_SHORT).show();
                        });
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date strikeDate = sdf.parse(strike.getStrike());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(strikeDate);
            calendar.add(Calendar.DAY_OF_MONTH, 20);

            Date validoAteDate = calendar.getTime();
            String validoAteString = sdf.format(validoAteDate);

            holder.validoAteTextView.setText("Strike até: " + validoAteString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return strikesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomePersonagemTextView;
        TextView strikeTextView;
        TextView validoAteTextView;
        TextView excluirStrike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomePersonagemTextView = itemView.findViewById(R.id.nomePersonagemTextView);
            strikeTextView = itemView.findViewById(R.id.strikeTextView);
            validoAteTextView = itemView.findViewById(R.id.validoAteTextView);
            excluirStrike = itemView.findViewById(R.id.excluirStrike);
        }
    }
}
