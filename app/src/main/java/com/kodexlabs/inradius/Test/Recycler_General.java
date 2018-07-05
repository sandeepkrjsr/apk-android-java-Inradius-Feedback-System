package com.kodexlabs.inradius.Test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kodexlabs.inradius.General.Dialog_Topic;
import com.kodexlabs.inradius.Main.Function_URL;
import com.kodexlabs.inradius.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklaus on 26-Feb-17.
 */

public class Recycler_General extends AppCompatActivity {

    private FloatingActionButton fab;

    private List<String> arrayId, arrayName, arrayDept, arrayPos;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);

        arrayId = new ArrayList<>();
        arrayName = new ArrayList<>();
        arrayDept = new ArrayList<>();
        arrayPos = new ArrayList<>();

        fab = (FloatingActionButton)findViewById(R.id.fab);
        //fab.setVisibility(View.GONE);

        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(layoutManager);

        Function_URL f_url = new Function_URL();
        String url = f_url.DATA_TOPICS + f_url.ACTION_READ;
        getData(url);
    }

    private void getData(String url) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("report");

            for (int i = 0; i < result.length(); i++){
                JSONObject get_data = result.getJSONObject(i);

                arrayId.add(get_data.getString("id"));
                arrayName.add(get_data.getString("topic"));
                arrayPos.add(get_data.getString("desc"));
            }
            adapter = new Adapter_General(arrayId, arrayName, arrayPos);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
        }
    }

    public void addNew(View view){
        Intent intent = new Intent(getBaseContext(), Dialog_Topic.class);
        startActivity(intent);
    }
}
