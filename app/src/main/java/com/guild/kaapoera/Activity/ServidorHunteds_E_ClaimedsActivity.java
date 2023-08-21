package com.guild.kaapoera.Activity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.guild.kaapoera.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ServidorHunteds_E_ClaimedsActivity extends AppCompatActivity {

    String planilhaID = "1B92HEEfT-LVbBHK6rsRgM_VimXxPLf-sOoxI7Kx2GGY";
    String apiKEY = "AIzaSyD7AuFDUl5RqlVgcJdQ4-23Laj7OV1j1bc";

    String strNome;
    String strVocacao;
    String strLevel;
    String strCidade;
    String strLocal;

    JSONArray jsonArray;
    ListView listHuntedsView;
    ListView listClaimedsView;

    ServidorHuntedsAdapter servidorHuntedsAdapter;
    ServidorClaimedsAdapter servidorClaimedsAdapter;

    ArrayList<String> listNOME = new ArrayList<String>();
    ArrayList<String> listVOCACAO = new ArrayList<String>();
    ArrayList<String> listLEVEL = new ArrayList<String>();

    // Lista de Hunteds e Calimeds
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hunteds_e_claimeds_activity_lista_data);

        listHuntedsView = findViewById(R.id.listHunteds);
        listClaimedsView = findViewById(R.id.listClaimeds);

        processHuntedsPlanilha();
        processClaimedsPlanilha();
    }

    private void processHuntedsPlanilha() {
        String huntedsUrls = "https://sheets.googleapis.com/v4/spreadsheets/" + planilhaID + "/values/Hunteds?key=" + apiKEY;

        RequestQueue queue = Volley.newRequestQueue(ServidorHunteds_E_ClaimedsActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, huntedsUrls, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonArray = response.getJSONArray("values");
                    huntedsJSON(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void processClaimedsPlanilha() {
        String claimedsUrls = "https://sheets.googleapis.com/v4/spreadsheets/" + planilhaID + "/values/Claimed?key=" + apiKEY;

        RequestQueue queue = Volley.newRequestQueue(ServidorHunteds_E_ClaimedsActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, claimedsUrls, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonArray = response.getJSONArray("values");
                    claimedsJSON(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void huntedsJSON(JSONArray jsonArray) throws JSONException {
        for (int i = 1; i < jsonArray.length(); i++) {
            JSONArray json = jsonArray.getJSONArray(i);
            strNome = json.getString(0);
            strVocacao = json.getString(1);
            strLevel = json.getString(2);

            listNOME.add(strNome);
            listVOCACAO.add(strVocacao);
            listLEVEL.add(strLevel);
        }

        servidorHuntedsAdapter = new ServidorHuntedsAdapter(getApplicationContext(), listNOME, listVOCACAO, listLEVEL);
        listHuntedsView.setAdapter(servidorHuntedsAdapter);
    }

    private void claimedsJSON(JSONArray jsonArray) throws JSONException {
        TreeMap<String, List<String>> cityToLocationsMap = new TreeMap<>(); // Usando TreeMap para manter as cidades ordenadas

        for (int i = 1; i < jsonArray.length(); i++) {
            JSONArray json = jsonArray.getJSONArray(i);
            strCidade = json.getString(0);
            strLocal = json.getString(1);

            if (!cityToLocationsMap.containsKey(strCidade)) {
                cityToLocationsMap.put(strCidade, new ArrayList<>());
            }

            cityToLocationsMap.get(strCidade).add(strLocal);
        }

        servidorClaimedsAdapter = new ServidorClaimedsAdapter(getApplicationContext(), cityToLocationsMap);
        listClaimedsView.setAdapter(servidorClaimedsAdapter);
    }

}
