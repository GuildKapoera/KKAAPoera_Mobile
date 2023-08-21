package com.guild.kaapoera.Activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guild.kaapoera.R;

import java.util.List;
import java.util.TreeMap;

public class ServidorClaimedsAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    TreeMap<String, List<String>> cityToLocationsMap;

    public ServidorClaimedsAdapter(Context context, TreeMap<String, List<String>> cityToLocationsMap) {
        this.context = context;
        this.cityToLocationsMap = cityToLocationsMap;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cityToLocationsMap.size();
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
        view = inflater.inflate(R.layout.claimeds_lista_data, null);
        TextView textNome = view.findViewById(R.id.textCidade);
        TextView textVocacao = view.findViewById(R.id.textLocal);

        String city = (String) cityToLocationsMap.keySet().toArray()[i];
        List<String> locations = cityToLocationsMap.get(city);

        textNome.setText(city);
        textVocacao.setText(TextUtils.join("\n", locations));

        return view;
    }
}

