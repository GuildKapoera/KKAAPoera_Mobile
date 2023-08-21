package com.guild.kaapoera.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.guild.kaapoera.R;

public class RankingGuild extends AppCompatActivity implements View.OnClickListener {
    private FirebaseFirestore db;
    private View currentTopPlayersLayout;
    private TextView ekButton, ekXPButton, ekSWORDButton, ekCLUBButton, ekAXEButton, edButton, edXPButton, edMLButton, rpButton, rpXPButton, rpDISTButton, rpMLButton, msButton, msXPButton, msMLButton, extraButton, extraBossButton, extraCharmButton, extraAchivButton;
    private LinearLayout top_players, ek_legenda, ed_legenda, rp_legenda, ms_legenda, menu_vocacoes, ekLineLayout, edLineLayout, rpLineLayout, msLineLayout, extra_legenda, extra_line_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_guild);
        menu_vocacoes = findViewById(R.id.menu_vocacoes);

        // Inicialize as "segundas linhas"
        ekLineLayout = findViewById(R.id.ek_line_layout);
        ek_legenda = findViewById(R.id.ek_legenda);
        edLineLayout = findViewById(R.id.ed_line_layout);
        ed_legenda = findViewById(R.id.ed_legenda);
        rpLineLayout = findViewById(R.id.rp_line_layout);
        rp_legenda = findViewById(R.id.rp_legenda);
        msLineLayout = findViewById(R.id.ms_line_layout);
        ms_legenda = findViewById(R.id.ms_legenda);
        extra_line_layout = findViewById(R.id.extra_line_layout);
        extra_legenda = findViewById(R.id.extra_legenda);
        top_players = findViewById(R.id.top_players);

        // Associe os eventos de clique aos botões da primeira linha
        ekButton = findViewById(R.id.ek_button);
        ekXPButton = findViewById(R.id.ek_xp_button);
        ekAXEButton = findViewById(R.id.ek_axe_button);
        ekSWORDButton = findViewById(R.id.ek_sword_button);
        ekCLUBButton = findViewById(R.id.ek_club_button);
        edButton = findViewById(R.id.ed_button);
        edXPButton = findViewById(R.id.ed_xp_button);
        edMLButton = findViewById(R.id.ed_ml_button);
        rpButton = findViewById(R.id.rp_button);
        rpXPButton = findViewById(R.id.rp_xp_button);
        rpDISTButton = findViewById(R.id.rp_distance_button);
        rpMLButton = findViewById(R.id.rp_ml_button);
        msButton = findViewById(R.id.ms_button);
        msXPButton = findViewById(R.id.ms_xp_button);
        msMLButton = findViewById(R.id.ms_ml_button);
        extraButton = findViewById(R.id.extra_button);
        extraBossButton = findViewById(R.id.extra_boss_button);
        extraCharmButton = findViewById(R.id.extra_charm_button);
        extraAchivButton = findViewById(R.id.extra_achiv_button);

        ekButton.setOnClickListener(this);
        ekXPButton.setOnClickListener(this);
        ekAXEButton.setOnClickListener(this);
        ekSWORDButton.setOnClickListener(this);
        ekCLUBButton.setOnClickListener(this);
        edButton.setOnClickListener(this);
        edXPButton.setOnClickListener(this);
        edMLButton.setOnClickListener(this);
        rpButton.setOnClickListener(this);
        rpXPButton.setOnClickListener(this);
        rpDISTButton.setOnClickListener(this);
        rpMLButton.setOnClickListener(this);
        msButton.setOnClickListener(this);
        msXPButton.setOnClickListener(this);
        msMLButton.setOnClickListener(this);
        extraButton.setOnClickListener(this);
        extraBossButton.setOnClickListener(this);
        extraCharmButton.setOnClickListener(this);
        extraAchivButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Ocultar todas as "segundas linhas" inicialmente
        ekLineLayout.setVisibility(View.GONE);
        ek_legenda.setVisibility(View.GONE);
        edLineLayout.setVisibility(View.GONE);
        ed_legenda.setVisibility(View.GONE);
        rpLineLayout.setVisibility(View.GONE);
        rp_legenda.setVisibility(View.GONE);
        msLineLayout.setVisibility(View.GONE);
        ms_legenda.setVisibility(View.GONE);
        extra_line_layout.setVisibility(View.GONE);
        extra_legenda.setVisibility(View.GONE);


        int buttonId = v.getId();
        if (buttonId == R.id.ek_button) {
            ekButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            rpButton.setBackgroundResource(0); edButton.setBackgroundResource(0); msButton.setBackgroundResource(0); extraButton.setBackgroundResource(0);
            ekXPButton.setBackgroundResource(0); ekAXEButton.setBackgroundResource(0); ekSWORDButton.setBackgroundResource(0); ekCLUBButton.setBackgroundResource(0); edXPButton.setBackgroundResource(0); edMLButton.setBackgroundResource(0); rpXPButton.setBackgroundResource(0); rpDISTButton.setBackgroundResource(0); rpMLButton.setBackgroundResource(0); msXPButton.setBackgroundResource(0); msMLButton.setBackgroundResource(0); extraBossButton.setBackgroundResource(0); extraCharmButton.setBackgroundResource(0); extraAchivButton.setBackgroundResource(0);
            top_players.removeAllViews();
            ekLineLayout.setVisibility(View.VISIBLE);
            ek_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 500);
        } else if (buttonId == R.id.ed_button) {
            edButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            rpButton.setBackgroundResource(0); ekButton.setBackgroundResource(0); msButton.setBackgroundResource(0); extraButton.setBackgroundResource(0);
            ekXPButton.setBackgroundResource(0); ekAXEButton.setBackgroundResource(0); ekSWORDButton.setBackgroundResource(0); ekCLUBButton.setBackgroundResource(0); edXPButton.setBackgroundResource(0); edMLButton.setBackgroundResource(0); rpXPButton.setBackgroundResource(0); rpDISTButton.setBackgroundResource(0); rpMLButton.setBackgroundResource(0); msXPButton.setBackgroundResource(0); msMLButton.setBackgroundResource(0); extraBossButton.setBackgroundResource(0); extraCharmButton.setBackgroundResource(0); extraAchivButton.setBackgroundResource(0);
            top_players.removeAllViews();
            edLineLayout.setVisibility(View.VISIBLE);
            ed_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 500);
        } else if (buttonId == R.id.rp_button) {
            rpButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            ekButton.setBackgroundResource(0); edButton.setBackgroundResource(0); msButton.setBackgroundResource(0); extraButton.setBackgroundResource(0);
            ekXPButton.setBackgroundResource(0); ekAXEButton.setBackgroundResource(0); ekSWORDButton.setBackgroundResource(0); ekCLUBButton.setBackgroundResource(0); edXPButton.setBackgroundResource(0); edMLButton.setBackgroundResource(0); rpXPButton.setBackgroundResource(0); rpDISTButton.setBackgroundResource(0); rpMLButton.setBackgroundResource(0); msXPButton.setBackgroundResource(0); msMLButton.setBackgroundResource(0); extraBossButton.setBackgroundResource(0); extraCharmButton.setBackgroundResource(0); extraAchivButton.setBackgroundResource(0);
            top_players.removeAllViews();
            rpLineLayout.setVisibility(View.VISIBLE);
            rp_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 500);
        } else if (buttonId == R.id.ms_button) {
            msButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            ekButton.setBackgroundResource(0); edButton.setBackgroundResource(0); rpButton.setBackgroundResource(0); extraButton.setBackgroundResource(0);
            ekXPButton.setBackgroundResource(0); ekAXEButton.setBackgroundResource(0); ekSWORDButton.setBackgroundResource(0); ekCLUBButton.setBackgroundResource(0); edXPButton.setBackgroundResource(0); edMLButton.setBackgroundResource(0); rpXPButton.setBackgroundResource(0); rpDISTButton.setBackgroundResource(0); rpMLButton.setBackgroundResource(0); msXPButton.setBackgroundResource(0); msMLButton.setBackgroundResource(0); extraBossButton.setBackgroundResource(0); extraCharmButton.setBackgroundResource(0); extraAchivButton.setBackgroundResource(0);
            top_players.removeAllViews();
            msLineLayout.setVisibility(View.VISIBLE);
            ms_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 500);
        } else if (buttonId == R.id.extra_button) {
            extraButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            ekButton.setBackgroundResource(0); edButton.setBackgroundResource(0); rpButton.setBackgroundResource(0); msButton.setBackgroundResource(0);
            ekXPButton.setBackgroundResource(0); ekAXEButton.setBackgroundResource(0); ekSWORDButton.setBackgroundResource(0); ekCLUBButton.setBackgroundResource(0); edXPButton.setBackgroundResource(0); edMLButton.setBackgroundResource(0); rpXPButton.setBackgroundResource(0); rpDISTButton.setBackgroundResource(0); rpMLButton.setBackgroundResource(0); msXPButton.setBackgroundResource(0); msMLButton.setBackgroundResource(0); extraBossButton.setBackgroundResource(0); extraCharmButton.setBackgroundResource(0); extraAchivButton.setBackgroundResource(0);
            top_players.removeAllViews();
            extra_line_layout.setVisibility(View.VISIBLE);
            extra_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 500);
        }
        else if (buttonId == R.id.rp_xp_button) {
            rpXPButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            rpDISTButton.setBackgroundResource(0); rpMLButton.setBackgroundResource(0);
            rpLineLayout.setVisibility(View.VISIBLE);
            rp_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 782);
            showRPXPPlayers();
        } else if (buttonId == R.id.rp_distance_button) {
            rpDISTButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            rpXPButton.setBackgroundResource(0); rpMLButton.setBackgroundResource(0);
            rpLineLayout.setVisibility(View.VISIBLE);
            rp_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 782);
            showRPDISTPlayers();
        }
        else if (buttonId == R.id.rp_ml_button) {
            rpMLButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            rpXPButton.setBackgroundResource(0); rpDISTButton.setBackgroundResource(0);
            rpLineLayout.setVisibility(View.VISIBLE);
            rp_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 782);
            showRPMLPlayers();
        }
        else if (buttonId == R.id.ek_xp_button) {
            ekXPButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            ekAXEButton.setBackgroundResource(0); ekSWORDButton.setBackgroundResource(0); ekCLUBButton.setBackgroundResource(0);
            ekLineLayout.setVisibility(View.VISIBLE);
            ek_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 782);
            showEKXPPlayers();
        }
        else if (buttonId == R.id.ek_axe_button) {
            ekAXEButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            ekXPButton.setBackgroundResource(0); ekSWORDButton.setBackgroundResource(0); ekCLUBButton.setBackgroundResource(0);
            ekLineLayout.setVisibility(View.VISIBLE);
            ek_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 782);
            showEKAXEPlayers();
        }
        else if (buttonId == R.id.ek_sword_button) {
            ekSWORDButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            ekXPButton.setBackgroundResource(0); ekAXEButton.setBackgroundResource(0); ekCLUBButton.setBackgroundResource(0);
            ekLineLayout.setVisibility(View.VISIBLE);
            ek_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 782);
            showEKSWORDPlayers();
        }
        else if (buttonId == R.id.ek_club_button) {
            ekCLUBButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            ekXPButton.setBackgroundResource(0); ekAXEButton.setBackgroundResource(0); ekSWORDButton.setBackgroundResource(0);
            ekLineLayout.setVisibility(View.VISIBLE);
            ek_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 782);
            showEKCLUBPlayers();
        }
        else if (buttonId == R.id.ed_xp_button) {
            edXPButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            edMLButton.setBackgroundResource(0);
            edLineLayout.setVisibility(View.VISIBLE);
            ed_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 782);
            showEDXPPlayers();
        }
        else if (buttonId == R.id.ed_ml_button) {
            edMLButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            edXPButton.setBackgroundResource(0);
            edLineLayout.setVisibility(View.VISIBLE);
            ed_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 782);
            showEDMLPlayers();
        }
        else if (buttonId == R.id.ms_xp_button) {
            msXPButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            msMLButton.setBackgroundResource(0);
            msLineLayout.setVisibility(View.VISIBLE);
            ms_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 782);
            showMSXPPlayers();
        }
        else if (buttonId == R.id.ms_ml_button) {
            msMLButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            msXPButton.setBackgroundResource(0);
            msLineLayout.setVisibility(View.VISIBLE);
            ms_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 782);
            showMSMLPlayers();
        }
        else if (buttonId == R.id.extra_boss_button) {
            extraBossButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            extraCharmButton.setBackgroundResource(0); extraAchivButton.setBackgroundResource(0);
            extra_line_layout.setVisibility(View.VISIBLE);
            extra_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 782);
            showExtraBossPlayers();
        }
        else if (buttonId == R.id.extra_charm_button) {
            extraCharmButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            extraBossButton.setBackgroundResource(0); extraAchivButton.setBackgroundResource(0);
            extra_line_layout.setVisibility(View.VISIBLE);
            extra_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 782);
            showExtraCharmPlayers();
        }
        else if (buttonId == R.id.extra_achiv_button) {
            extraAchivButton.setBackgroundResource(R.drawable.bordas_arredondadas);
            extraBossButton.setBackgroundResource(0); extraCharmButton.setBackgroundResource(0);
            extra_line_layout.setVisibility(View.VISIBLE);
            extra_legenda.setVisibility(View.VISIBLE);
            setMarginTop(menu_vocacoes, 782);
            showExtraAchivPlayers();
        }
    }

    private void setMarginTop(View view, int marginTop) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.topMargin = marginTop;
        view.setLayoutParams(layoutParams);
    }

    private void showRPXPPlayers() {
        // Verificar se há um layout de melhores jogadores atualmente exibido e removê-lo
        if (currentTopPlayersLayout != null) {
            LinearLayout topPlayersContainer = findViewById(R.id.top_players);
            topPlayersContainer.removeView(currentTopPlayersLayout);
        }

        // Limpar o topPlayersContainer
        LinearLayout topPlayersContainer = findViewById(R.id.top_players);
        topPlayersContainer.removeAllViews();

        // Inflar o layout de melhores jogadores
        View topPlayersLayout = LayoutInflater.from(this).inflate(R.layout.ranking_top_players_layout, null);

        // Adicionar o layout de melhores jogadores abaixo do linear layout vazio (top_players)
        topPlayersContainer.addView(topPlayersLayout);

        // Armazenar a referência ao layout de melhores jogadores atual
        currentTopPlayersLayout = topPlayersLayout;

        // Recuperar as informações dos jogadores do Firebase e atualizar os TextViews no layout
        DocumentReference rpDocumentRef = db.collection("Ranking").document("WkA5qlWXJ8SA959dt9f1");
        rpDocumentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String top1Name = document.getString("rp_top_xp_1_nome");
                    int top1Level = document.getLong("rp_top_xp_1_level").intValue();
                    String top2Name = document.getString("rp_top_xp_2_nome");
                    int top2Level = document.getLong("rp_top_xp_2_level").intValue();
                    String top3Name = document.getString("rp_top_xp_3_nome");
                    int top3Level = document.getLong("rp_top_xp_3_level").intValue();


                    // Encontrar os TextViews no layout inflado e atualizar com as informações dos jogadores
                    TextView top1TextView = topPlayersLayout.findViewById(R.id.top1_text_view);
                    TextView top2TextView = topPlayersLayout.findViewById(R.id.top2_text_view);
                    TextView top3TextView = topPlayersLayout.findViewById(R.id.top3_text_view);

                    // Modelo do texto exibido em tela
                    top1TextView.setText("🥇: " + top1Name + " - " + top1Level);
                    top2TextView.setText("🥈: " + top2Name + " - " + top2Level);
                    top3TextView.setText("🥉: " + top3Name + " - " + top3Level);
                }
            }
        });
    }
    private void showRPMLPlayers() {
        // Verificar se há um layout de melhores jogadores atualmente exibido e removê-lo
        if (currentTopPlayersLayout != null) {
            LinearLayout topPlayersContainer = findViewById(R.id.top_players);
            topPlayersContainer.removeView(currentTopPlayersLayout);
        }

        // Limpar o topPlayersContainer
        LinearLayout topPlayersContainer = findViewById(R.id.top_players);
        topPlayersContainer.removeAllViews();

        // Inflar o layout de melhores jogadores
        View topPlayersLayout = LayoutInflater.from(this).inflate(R.layout.ranking_top_players_layout, null);

        // Adicionar o layout de melhores jogadores abaixo do linear layout vazio (top_players)
        topPlayersContainer.addView(topPlayersLayout);

        // Armazenar a referência ao layout de melhores jogadores atual
        currentTopPlayersLayout = topPlayersLayout;

        // Recuperar as informações dos jogadores do Firebase e atualizar os TextViews no layout
        DocumentReference rpDocumentRef = db.collection("Ranking").document("WkA5qlWXJ8SA959dt9f1");
        rpDocumentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String top1Name = document.getString("rp_top_ml_1_nome");
                    int top1Level = document.getLong("rp_top_ml_1_skill").intValue();
                    String top2Name = document.getString("rp_top_ml_2_nome");
                    int top2Level = document.getLong("rp_top_ml_2_skill").intValue();
                    String top3Name = document.getString("rp_top_ml_3_nome");
                    int top3Level = document.getLong("rp_top_ml_3_skill").intValue();


                    // Encontrar os TextViews no layout inflado e atualizar com as informações dos jogadores
                    TextView top1TextView = topPlayersLayout.findViewById(R.id.top1_text_view);
                    TextView top2TextView = topPlayersLayout.findViewById(R.id.top2_text_view);
                    TextView top3TextView = topPlayersLayout.findViewById(R.id.top3_text_view);

                    // Modelo do texto exibido em tela
                    top1TextView.setText("🥇: " + top1Name + " - " + top1Level);
                    top2TextView.setText("🥈: " + top2Name + " - " + top2Level);
                    top3TextView.setText("🥉: " + top3Name + " - " + top3Level);
                }
            }
        });
    }
    private void showRPDISTPlayers() {
        // Verificar se há um layout de melhores jogadores atualmente exibido e removê-lo
        if (currentTopPlayersLayout != null) {
            LinearLayout topPlayersContainer = findViewById(R.id.top_players);
            topPlayersContainer.removeView(currentTopPlayersLayout);
        }

        // Limpar o topPlayersContainer
        LinearLayout topPlayersContainer = findViewById(R.id.top_players);
        topPlayersContainer.removeAllViews();

        // Inflar o layout de melhores jogadores
        View topPlayersLayout = LayoutInflater.from(this).inflate(R.layout.ranking_top_players_layout, null);

        // Adicionar o layout de melhores jogadores abaixo do linear layout vazio (top_players)
        topPlayersContainer.addView(topPlayersLayout);

        // Armazenar a referência ao layout de melhores jogadores atual
        currentTopPlayersLayout = topPlayersLayout;

        // Recuperar as informações dos jogadores do Firebase e atualizar os TextViews no layout
        DocumentReference rpDocumentRef = db.collection("Ranking").document("WkA5qlWXJ8SA959dt9f1");
        rpDocumentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String top1Name = document.getString("rp_top_distance_1_nome");
                    int top1Level = document.getLong("rp_top_distance_1_skill").intValue();
                    String top2Name = document.getString("rp_top_distance_2_nome");
                    int top2Level = document.getLong("rp_top_distance_2_skill").intValue();
                    String top3Name = document.getString("rp_top_distance_3_nome");
                    int top3Level = document.getLong("rp_top_distance_3_skill").intValue();


                    // Encontrar os TextViews no layout inflado e atualizar com as informações dos jogadores
                    TextView top1TextView = topPlayersLayout.findViewById(R.id.top1_text_view);
                    TextView top2TextView = topPlayersLayout.findViewById(R.id.top2_text_view);
                    TextView top3TextView = topPlayersLayout.findViewById(R.id.top3_text_view);

                    // Modelo do texto exibido em tela
                    top1TextView.setText("🥇: " + top1Name + " - " + top1Level);
                    top2TextView.setText("🥈: " + top2Name + " - " + top2Level);
                    top3TextView.setText("🥉: " + top3Name + " - " + top3Level);
                }
            }
        });
    }
    private void showEKXPPlayers() {
        // Verificar se há um layout de melhores jogadores atualmente exibido e removê-lo
        if (currentTopPlayersLayout != null) {
            LinearLayout topPlayersContainer = findViewById(R.id.top_players);
            topPlayersContainer.removeView(currentTopPlayersLayout);
        }

        // Limpar o topPlayersContainer
        LinearLayout topPlayersContainer = findViewById(R.id.top_players);
        topPlayersContainer.removeAllViews();

        // Inflar o layout de melhores jogadores
        View topPlayersLayout = LayoutInflater.from(this).inflate(R.layout.ranking_top_players_layout, null);

        // Adicionar o layout de melhores jogadores abaixo do linear layout vazio (top_players)
        topPlayersContainer.addView(topPlayersLayout);

        // Armazenar a referência ao layout de melhores jogadores atual
        currentTopPlayersLayout = topPlayersLayout;

        // Recuperar as informações dos jogadores do Firebase e atualizar os TextViews no layout
        DocumentReference rpDocumentRef = db.collection("Ranking").document("WkA5qlWXJ8SA959dt9f1");
        rpDocumentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String top1Name = document.getString("ek_top_xp_1_nome");
                    int top1Level = document.getLong("ek_top_xp_1_level").intValue();
                    String top2Name = document.getString("ek_top_xp_2_nome");
                    int top2Level = document.getLong("ek_top_xp_2_level").intValue();
                    String top3Name = document.getString("ek_top_xp_3_nome");
                    int top3Level = document.getLong("ek_top_xp_3_level").intValue();


                    // Encontrar os TextViews no layout inflado e atualizar com as informações dos jogadores
                    TextView top1TextView = topPlayersLayout.findViewById(R.id.top1_text_view);
                    TextView top2TextView = topPlayersLayout.findViewById(R.id.top2_text_view);
                    TextView top3TextView = topPlayersLayout.findViewById(R.id.top3_text_view);

                    // Modelo do texto exibido em tela
                    top1TextView.setText("🥇: " + top1Name + " - " + top1Level);
                    top2TextView.setText("🥈: " + top2Name + " - " + top2Level);
                    top3TextView.setText("🥉: " + top3Name + " - " + top3Level);
                }
            }
        });
    }
    private void showEKAXEPlayers() {
        // Verificar se há um layout de melhores jogadores atualmente exibido e removê-lo
        if (currentTopPlayersLayout != null) {
            LinearLayout topPlayersContainer = findViewById(R.id.top_players);
            topPlayersContainer.removeView(currentTopPlayersLayout);
        }

        // Limpar o topPlayersContainer
        LinearLayout topPlayersContainer = findViewById(R.id.top_players);
        topPlayersContainer.removeAllViews();

        // Inflar o layout de melhores jogadores
        View topPlayersLayout = LayoutInflater.from(this).inflate(R.layout.ranking_top_players_layout, null);

        // Adicionar o layout de melhores jogadores abaixo do linear layout vazio (top_players)
        topPlayersContainer.addView(topPlayersLayout);

        // Armazenar a referência ao layout de melhores jogadores atual
        currentTopPlayersLayout = topPlayersLayout;

        // Recuperar as informações dos jogadores do Firebase e atualizar os TextViews no layout
        DocumentReference rpDocumentRef = db.collection("Ranking").document("WkA5qlWXJ8SA959dt9f1");
        rpDocumentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String top1Name = document.getString("ek_top_axe_1_nome");
                    int top1Level = document.getLong("ek_top_axe_1_skill").intValue();
                    String top2Name = document.getString("ek_top_axe_2_nome");
                    int top2Level = document.getLong("ek_top_axe_2_skill").intValue();
                    String top3Name = document.getString("ek_top_axe_3_nome");
                    int top3Level = document.getLong("ek_top_axe_3_skill").intValue();


                    // Encontrar os TextViews no layout inflado e atualizar com as informações dos jogadores
                    TextView top1TextView = topPlayersLayout.findViewById(R.id.top1_text_view);
                    TextView top2TextView = topPlayersLayout.findViewById(R.id.top2_text_view);
                    TextView top3TextView = topPlayersLayout.findViewById(R.id.top3_text_view);

                    // Modelo do texto exibido em tela
                    top1TextView.setText("🥇: " + top1Name + " - " + top1Level);
                    top2TextView.setText("🥈: " + top2Name + " - " + top2Level);
                    top3TextView.setText("🥉: " + top3Name + " - " + top3Level);
                }
            }
        });
    }
    private void showEKSWORDPlayers() {
        // Verificar se há um layout de melhores jogadores atualmente exibido e removê-lo
        if (currentTopPlayersLayout != null) {
            LinearLayout topPlayersContainer = findViewById(R.id.top_players);
            topPlayersContainer.removeView(currentTopPlayersLayout);
        }

        // Limpar o topPlayersContainer
        LinearLayout topPlayersContainer = findViewById(R.id.top_players);
        topPlayersContainer.removeAllViews();

        // Inflar o layout de melhores jogadores
        View topPlayersLayout = LayoutInflater.from(this).inflate(R.layout.ranking_top_players_layout, null);

        // Adicionar o layout de melhores jogadores abaixo do linear layout vazio (top_players)
        topPlayersContainer.addView(topPlayersLayout);

        // Armazenar a referência ao layout de melhores jogadores atual
        currentTopPlayersLayout = topPlayersLayout;

        // Recuperar as informações dos jogadores do Firebase e atualizar os TextViews no layout
        DocumentReference rpDocumentRef = db.collection("Ranking").document("WkA5qlWXJ8SA959dt9f1");
        rpDocumentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String top1Name = document.getString("ek_top_sword_1_nome");
                    int top1Level = document.getLong("ek_top_sword_1_skill").intValue();
                    String top2Name = document.getString("ek_top_sword_2_nome");
                    int top2Level = document.getLong("ek_top_sword_2_skill").intValue();
                    String top3Name = document.getString("ek_top_sword_3_nome");
                    int top3Level = document.getLong("ek_top_sword_3_skill").intValue();


                    // Encontrar os TextViews no layout inflado e atualizar com as informações dos jogadores
                    TextView top1TextView = topPlayersLayout.findViewById(R.id.top1_text_view);
                    TextView top2TextView = topPlayersLayout.findViewById(R.id.top2_text_view);
                    TextView top3TextView = topPlayersLayout.findViewById(R.id.top3_text_view);

                    // Modelo do texto exibido em tela
                    top1TextView.setText("🥇: " + top1Name + " - " + top1Level);
                    top2TextView.setText("🥈: " + top2Name + " - " + top2Level);
                    top3TextView.setText("🥉: " + top3Name + " - " + top3Level);
                }
            }
        });
    }
    private void showEKCLUBPlayers() {
        // Verificar se há um layout de melhores jogadores atualmente exibido e removê-lo
        if (currentTopPlayersLayout != null) {
            LinearLayout topPlayersContainer = findViewById(R.id.top_players);
            topPlayersContainer.removeView(currentTopPlayersLayout);
        }

        // Limpar o topPlayersContainer
        LinearLayout topPlayersContainer = findViewById(R.id.top_players);
        topPlayersContainer.removeAllViews();

        // Inflar o layout de melhores jogadores
        View topPlayersLayout = LayoutInflater.from(this).inflate(R.layout.ranking_top_players_layout, null);

        // Adicionar o layout de melhores jogadores abaixo do linear layout vazio (top_players)
        topPlayersContainer.addView(topPlayersLayout);

        // Armazenar a referência ao layout de melhores jogadores atual
        currentTopPlayersLayout = topPlayersLayout;

        // Recuperar as informações dos jogadores do Firebase e atualizar os TextViews no layout
        DocumentReference rpDocumentRef = db.collection("Ranking").document("WkA5qlWXJ8SA959dt9f1");
        rpDocumentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String top1Name = document.getString("ek_top_club_1_nome");
                    int top1Level = document.getLong("ek_top_club_1_skill").intValue();
                    String top2Name = document.getString("ek_top_club_2_nome");
                    int top2Level = document.getLong("ek_top_club_2_skill").intValue();
                    String top3Name = document.getString("ek_top_club_3_nome");
                    int top3Level = document.getLong("ek_top_club_3_skill").intValue();


                    // Encontrar os TextViews no layout inflado e atualizar com as informações dos jogadores
                    TextView top1TextView = topPlayersLayout.findViewById(R.id.top1_text_view);
                    TextView top2TextView = topPlayersLayout.findViewById(R.id.top2_text_view);
                    TextView top3TextView = topPlayersLayout.findViewById(R.id.top3_text_view);

                    // Modelo do texto exibido em tela
                    top1TextView.setText("🥇: " + top1Name + " - " + top1Level);
                    top2TextView.setText("🥈: " + top2Name + " - " + top2Level);
                    top3TextView.setText("🥉: " + top3Name + " - " + top3Level);
                }
            }
        });
    }
    private void showEDXPPlayers() {
        // Verificar se há um layout de melhores jogadores atualmente exibido e removê-lo
        if (currentTopPlayersLayout != null) {
            LinearLayout topPlayersContainer = findViewById(R.id.top_players);
            topPlayersContainer.removeView(currentTopPlayersLayout);
        }

        // Limpar o topPlayersContainer
        LinearLayout topPlayersContainer = findViewById(R.id.top_players);
        topPlayersContainer.removeAllViews();

        // Inflar o layout de melhores jogadores
        View topPlayersLayout = LayoutInflater.from(this).inflate(R.layout.ranking_top_players_layout, null);

        // Adicionar o layout de melhores jogadores abaixo do linear layout vazio (top_players)
        topPlayersContainer.addView(topPlayersLayout);

        // Armazenar a referência ao layout de melhores jogadores atual
        currentTopPlayersLayout = topPlayersLayout;

        // Recuperar as informações dos jogadores do Firebase e atualizar os TextViews no layout
        DocumentReference rpDocumentRef = db.collection("Ranking").document("WkA5qlWXJ8SA959dt9f1");
        rpDocumentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String top1Name = document.getString("ed_top_xp_1_nome");
                    int top1Level = document.getLong("ed_top_xp_1_level").intValue();
                    String top2Name = document.getString("ed_top_xp_2_nome");
                    int top2Level = document.getLong("ed_top_xp_2_level").intValue();
                    String top3Name = document.getString("ed_top_xp_3_nome");
                    int top3Level = document.getLong("ed_top_xp_3_level").intValue();


                    // Encontrar os TextViews no layout inflado e atualizar com as informações dos jogadores
                    TextView top1TextView = topPlayersLayout.findViewById(R.id.top1_text_view);
                    TextView top2TextView = topPlayersLayout.findViewById(R.id.top2_text_view);
                    TextView top3TextView = topPlayersLayout.findViewById(R.id.top3_text_view);

                    // Modelo do texto exibido em tela
                    top1TextView.setText("🥇: " + top1Name + " - " + top1Level);
                    top2TextView.setText("🥈: " + top2Name + " - " + top2Level);
                    top3TextView.setText("🥉: " + top3Name + " - " + top3Level);
                }
            }
        });
    }
    private void showEDMLPlayers() {
        // Verificar se há um layout de melhores jogadores atualmente exibido e removê-lo
        if (currentTopPlayersLayout != null) {
            LinearLayout topPlayersContainer = findViewById(R.id.top_players);
            topPlayersContainer.removeView(currentTopPlayersLayout);
        }

        // Limpar o topPlayersContainer
        LinearLayout topPlayersContainer = findViewById(R.id.top_players);
        topPlayersContainer.removeAllViews();

        // Inflar o layout de melhores jogadores
        View topPlayersLayout = LayoutInflater.from(this).inflate(R.layout.ranking_top_players_layout, null);

        // Adicionar o layout de melhores jogadores abaixo do linear layout vazio (top_players)
        topPlayersContainer.addView(topPlayersLayout);

        // Armazenar a referência ao layout de melhores jogadores atual
        currentTopPlayersLayout = topPlayersLayout;

        // Recuperar as informações dos jogadores do Firebase e atualizar os TextViews no layout
        DocumentReference rpDocumentRef = db.collection("Ranking").document("WkA5qlWXJ8SA959dt9f1");
        rpDocumentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String top1Name = document.getString("ed_top_ml_1_nome");
                    int top1Level = document.getLong("ed_top_ml_1_skill").intValue();
                    String top2Name = document.getString("ed_top_ml_2_nome");
                    int top2Level = document.getLong("ed_top_ml_2_skill").intValue();
                    String top3Name = document.getString("ed_top_ml_3_nome");
                    int top3Level = document.getLong("ed_top_ml_3_skill").intValue();


                    // Encontrar os TextViews no layout inflado e atualizar com as informações dos jogadores
                    TextView top1TextView = topPlayersLayout.findViewById(R.id.top1_text_view);
                    TextView top2TextView = topPlayersLayout.findViewById(R.id.top2_text_view);
                    TextView top3TextView = topPlayersLayout.findViewById(R.id.top3_text_view);

                    // Modelo do texto exibido em tela
                    top1TextView.setText("🥇: " + top1Name + " - " + top1Level);
                    top2TextView.setText("🥈: " + top2Name + " - " + top2Level);
                    top3TextView.setText("🥉: " + top3Name + " - " + top3Level);
                }
            }
        });
    }
    private void showMSXPPlayers() {
        // Verificar se há um layout de melhores jogadores atualmente exibido e removê-lo
        if (currentTopPlayersLayout != null) {
            LinearLayout topPlayersContainer = findViewById(R.id.top_players);
            topPlayersContainer.removeView(currentTopPlayersLayout);
        }

        // Limpar o topPlayersContainer
        LinearLayout topPlayersContainer = findViewById(R.id.top_players);
        topPlayersContainer.removeAllViews();

        // Inflar o layout de melhores jogadores
        View topPlayersLayout = LayoutInflater.from(this).inflate(R.layout.ranking_top_players_layout, null);

        // Adicionar o layout de melhores jogadores abaixo do linear layout vazio (top_players)
        topPlayersContainer.addView(topPlayersLayout);

        // Armazenar a referência ao layout de melhores jogadores atual
        currentTopPlayersLayout = topPlayersLayout;

        // Recuperar as informações dos jogadores do Firebase e atualizar os TextViews no layout
        DocumentReference rpDocumentRef = db.collection("Ranking").document("WkA5qlWXJ8SA959dt9f1");
        rpDocumentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String top1Name = document.getString("ms_top_xp_1_nome");
                    int top1Level = document.getLong("ms_top_xp_1_level").intValue();
                    String top2Name = document.getString("ms_top_xp_2_nome");
                    int top2Level = document.getLong("ms_top_xp_2_level").intValue();
                    String top3Name = document.getString("ms_top_xp_3_nome");
                    int top3Level = document.getLong("ms_top_xp_3_level").intValue();


                    // Encontrar os TextViews no layout inflado e atualizar com as informações dos jogadores
                    TextView top1TextView = topPlayersLayout.findViewById(R.id.top1_text_view);
                    TextView top2TextView = topPlayersLayout.findViewById(R.id.top2_text_view);
                    TextView top3TextView = topPlayersLayout.findViewById(R.id.top3_text_view);

                    // Modelo do texto exibido em tela
                    top1TextView.setText("🥇: " + top1Name + " - " + top1Level);
                    top2TextView.setText("🥈: " + top2Name + " - " + top2Level);
                    top3TextView.setText("🥉: " + top3Name + " - " + top3Level);
                }
            }
        });
    }
    private void showMSMLPlayers() {
        // Verificar se há um layout de melhores jogadores atualmente exibido e removê-lo
        if (currentTopPlayersLayout != null) {
            LinearLayout topPlayersContainer = findViewById(R.id.top_players);
            topPlayersContainer.removeView(currentTopPlayersLayout);
        }

        // Limpar o topPlayersContainer
        LinearLayout topPlayersContainer = findViewById(R.id.top_players);
        topPlayersContainer.removeAllViews();

        // Inflar o layout de melhores jogadores
        View topPlayersLayout = LayoutInflater.from(this).inflate(R.layout.ranking_top_players_layout, null);

        // Adicionar o layout de melhores jogadores abaixo do linear layout vazio (top_players)
        topPlayersContainer.addView(topPlayersLayout);

        // Armazenar a referência ao layout de melhores jogadores atual
        currentTopPlayersLayout = topPlayersLayout;

        // Recuperar as informações dos jogadores do Firebase e atualizar os TextViews no layout
        DocumentReference rpDocumentRef = db.collection("Ranking").document("WkA5qlWXJ8SA959dt9f1");
        rpDocumentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String top1Name = document.getString("ms_top_ml_1_nome");
                    int top1Level = document.getLong("ms_top_ml_1_skill").intValue();
                    String top2Name = document.getString("ms_top_ml_2_nome");
                    int top2Level = document.getLong("ms_top_ml_2_skill").intValue();
                    String top3Name = document.getString("ms_top_ml_3_nome");
                    int top3Level = document.getLong("ms_top_ml_3_skill").intValue();


                    // Encontrar os TextViews no layout inflado e atualizar com as informações dos jogadores
                    TextView top1TextView = topPlayersLayout.findViewById(R.id.top1_text_view);
                    TextView top2TextView = topPlayersLayout.findViewById(R.id.top2_text_view);
                    TextView top3TextView = topPlayersLayout.findViewById(R.id.top3_text_view);

                    // Modelo do texto exibido em tela
                    top1TextView.setText("🥇: " + top1Name + " - " + top1Level);
                    top2TextView.setText("🥈: " + top2Name + " - " + top2Level);
                    top3TextView.setText("🥉: " + top3Name + " - " + top3Level);
                }
            }
        });
    }
    private void showExtraBossPlayers() {
        // Verificar se há um layout de melhores jogadores atualmente exibido e removê-lo
        if (currentTopPlayersLayout != null) {
            LinearLayout topPlayersContainer = findViewById(R.id.top_players);
            topPlayersContainer.removeView(currentTopPlayersLayout);
        }

        // Limpar o topPlayersContainer
        LinearLayout topPlayersContainer = findViewById(R.id.top_players);
        topPlayersContainer.removeAllViews();

        // Inflar o layout de melhores jogadores
        View topPlayersLayout = LayoutInflater.from(this).inflate(R.layout.ranking_top_players_layout, null);

        // Adicionar o layout de melhores jogadores abaixo do linear layout vazio (top_players)
        topPlayersContainer.addView(topPlayersLayout);

        // Armazenar a referência ao layout de melhores jogadores atual
        currentTopPlayersLayout = topPlayersLayout;

        // Recuperar as informações dos jogadores do Firebase e atualizar os TextViews no layout
        DocumentReference rpDocumentRef = db.collection("Ranking").document("WkA5qlWXJ8SA959dt9f1");
        rpDocumentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String top1Name = document.getString("extra_top_boss_1_nome");
                    int top1Level = document.getLong("extra_top_boss_1_pontos").intValue();
                    String top2Name = document.getString("extra_top_boss_2_nome");
                    int top2Level = document.getLong("extra_top_boss_2_pontos").intValue();
                    String top3Name = document.getString("extra_top_boss_3_nome");
                    int top3Level = document.getLong("extra_top_boss_3_pontos").intValue();


                    // Encontrar os TextViews no layout inflado e atualizar com as informações dos jogadores
                    TextView top1TextView = topPlayersLayout.findViewById(R.id.top1_text_view);
                    TextView top2TextView = topPlayersLayout.findViewById(R.id.top2_text_view);
                    TextView top3TextView = topPlayersLayout.findViewById(R.id.top3_text_view);

                    // Modelo do texto exibido em tela
                    top1TextView.setText("🥇: " + top1Name + " - " + top1Level);
                    top2TextView.setText("🥈: " + top2Name + " - " + top2Level);
                    top3TextView.setText("🥉: " + top3Name + " - " + top3Level);
                }
            }
        });
    }
    private void showExtraCharmPlayers() {
        // Verificar se há um layout de melhores jogadores atualmente exibido e removê-lo
        if (currentTopPlayersLayout != null) {
            LinearLayout topPlayersContainer = findViewById(R.id.top_players);
            topPlayersContainer.removeView(currentTopPlayersLayout);
        }

        // Limpar o topPlayersContainer
        LinearLayout topPlayersContainer = findViewById(R.id.top_players);
        topPlayersContainer.removeAllViews();

        // Inflar o layout de melhores jogadores
        View topPlayersLayout = LayoutInflater.from(this).inflate(R.layout.ranking_top_players_layout, null);

        // Adicionar o layout de melhores jogadores abaixo do linear layout vazio (top_players)
        topPlayersContainer.addView(topPlayersLayout);

        // Armazenar a referência ao layout de melhores jogadores atual
        currentTopPlayersLayout = topPlayersLayout;

        // Recuperar as informações dos jogadores do Firebase e atualizar os TextViews no layout
        DocumentReference rpDocumentRef = db.collection("Ranking").document("WkA5qlWXJ8SA959dt9f1");
        rpDocumentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String top1Name = document.getString("extra_top_charm_1_nome");
                    int top1Level = document.getLong("extra_top_charm_1_pontos").intValue();
                    String top2Name = document.getString("extra_top_charm_2_nome");
                    int top2Level = document.getLong("extra_top_charm_2_pontos").intValue();
                    String top3Name = document.getString("extra_top_charm_3_nome");
                    int top3Level = document.getLong("extra_top_charm_3_pontos").intValue();


                    // Encontrar os TextViews no layout inflado e atualizar com as informações dos jogadores
                    TextView top1TextView = topPlayersLayout.findViewById(R.id.top1_text_view);
                    TextView top2TextView = topPlayersLayout.findViewById(R.id.top2_text_view);
                    TextView top3TextView = topPlayersLayout.findViewById(R.id.top3_text_view);

                    // Modelo do texto exibido em tela
                    top1TextView.setText("🥇: " + top1Name + " - " + top1Level);
                    top2TextView.setText("🥈: " + top2Name + " - " + top2Level);
                    top3TextView.setText("🥉: " + top3Name + " - " + top3Level);
                }
            }
        });
    }
    private void showExtraAchivPlayers() {
        // Verificar se há um layout de melhores jogadores atualmente exibido e removê-lo
        if (currentTopPlayersLayout != null) {
            LinearLayout topPlayersContainer = findViewById(R.id.top_players);
            topPlayersContainer.removeView(currentTopPlayersLayout);
        }

        // Limpar o topPlayersContainer
        LinearLayout topPlayersContainer = findViewById(R.id.top_players);
        topPlayersContainer.removeAllViews();

        // Inflar o layout de melhores jogadores
        View topPlayersLayout = LayoutInflater.from(this).inflate(R.layout.ranking_top_players_layout, null);

        // Adicionar o layout de melhores jogadores abaixo do linear layout vazio (top_players)
        topPlayersContainer.addView(topPlayersLayout);

        // Armazenar a referência ao layout de melhores jogadores atual
        currentTopPlayersLayout = topPlayersLayout;

        // Recuperar as informações dos jogadores do Firebase e atualizar os TextViews no layout
        DocumentReference rpDocumentRef = db.collection("Ranking").document("WkA5qlWXJ8SA959dt9f1");
        rpDocumentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String top1Name = document.getString("extra_top_achiv_1_nome");
                    int top1Level = document.getLong("extra_top_achiv_1_pontos").intValue();
                    String top2Name = document.getString("extra_top_achiv_2_nome");
                    int top2Level = document.getLong("extra_top_achiv_2_pontos").intValue();
                    String top3Name = document.getString("extra_top_achiv_3_nome");
                    int top3Level = document.getLong("extra_top_achiv_3_pontos").intValue();


                    // Encontrar os TextViews no layout inflado e atualizar com as informações dos jogadores
                    TextView top1TextView = topPlayersLayout.findViewById(R.id.top1_text_view);
                    TextView top2TextView = topPlayersLayout.findViewById(R.id.top2_text_view);
                    TextView top3TextView = topPlayersLayout.findViewById(R.id.top3_text_view);

                    // Modelo do texto exibido em tela
                    top1TextView.setText("🥇: " + top1Name + " - " + top1Level);
                    top2TextView.setText("🥈: " + top2Name + " - " + top2Level);
                    top3TextView.setText("🥉: " + top3Name + " - " + top3Level);
                }
            }
        });
    }
}