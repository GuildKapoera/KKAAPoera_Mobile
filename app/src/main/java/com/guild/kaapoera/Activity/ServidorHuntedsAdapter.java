package com.guild.kaapoera.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guild.kaapoera.R;

import java.util.ArrayList;

public class ServidorHuntedsAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<String> listNome;
    ArrayList<String> listVocacao;
    ArrayList<String> listLevel;

    public ServidorHuntedsAdapter(Context context, ArrayList<String> listNome, ArrayList<String> listVocacao, ArrayList<String> listLevel){
        this.context = context;
        this.listNome = listNome;
        this.listVocacao = listVocacao;
        this.listLevel = listLevel;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listNome.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.hunteds_lista_data, null);
        TextView textNome = view.findViewById(R.id.textNome);
        TextView textVocacao = view.findViewById(R.id.textVocacao);
        TextView textLevel = view.findViewById(R.id.textLevel);

        textNome.setText(listNome.get(i));
        textVocacao.setText(listVocacao.get(i));
        textLevel.setText(listLevel.get(i));

        return view;
    }
}