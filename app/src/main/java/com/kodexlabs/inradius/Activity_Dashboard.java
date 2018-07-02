package com.kodexlabs.inradius;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklaus on 26-Feb-17.
 */

public class Activity_Dashboard extends Activity {

    private List<String> arrayId, arrayTopic, arrayDesc, arrayRating;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    static String DATA_URL = "http://kiitecell.hol.es/Inradius_topic_all.php";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);

        arrayId = new ArrayList<>();
        arrayTopic = new ArrayList<>();
        arrayDesc = new ArrayList<>();
        arrayRating = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(layoutManager);

        getData();

    }

    private void getData() {
        String url = DATA_URL;

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
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++){
                JSONObject get_data = result.getJSONObject(i);

                arrayId.add(get_data.getString("id"));
                arrayTopic.add(get_data.getString("topic"));
                arrayDesc.add(get_data.getString("desc"));
                arrayRating.add(get_data.getString("rating"));
            }
            adapter = new Adapter_Topic(arrayId, arrayTopic, arrayDesc, arrayRating);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
        }
    }

    public void Topic(View view){
        Intent intent = new Intent(getBaseContext(), Dialog_Topic_Add.class);
        startActivity(intent);
    }
}
