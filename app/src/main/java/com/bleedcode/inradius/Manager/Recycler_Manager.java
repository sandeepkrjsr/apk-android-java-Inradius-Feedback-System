package com.bleedcode.inradius.Manager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bleedcode.inradius.Main.Activity_Login;
import com.bleedcode.inradius.Main.Function_URL;
import com.bleedcode.inradius.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklaus on 26-Feb-17.
 */

public class Recycler_Manager extends AppCompatActivity {

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
        fab.setVisibility(View.GONE);

        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(layoutManager);

        Function_URL f_url = new Function_URL();
        String url = f_url.DATA_EMPLOYEES + f_url.ACTION_READ;
        getData(url);
    }

    private void getData(String url) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("report");

                    for (int i = 0; i < result.length(); i++){
                        JSONObject get_data = result.getJSONObject(i);

                        //if (Integer.parseInt(get_data.getString("level")) > Integer.parseInt(Activity_Login.loggedlevel)){
                            arrayId.add(get_data.getString("id"));
                            arrayName.add(get_data.getString("name"));
                            arrayPos.add(get_data.getString("pos"));
                            arrayDept.add(get_data.getString("dept"));
                        //}
                    }
                    adapter = new Adapter_Manager(arrayId, arrayName, arrayPos, arrayDept);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
