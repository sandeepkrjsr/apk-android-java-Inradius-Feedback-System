package com.kodexlabs.inradius.General;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kodexlabs.inradius.Main.Activity_Login;
import com.kodexlabs.inradius.Main.Function_URL;
import com.kodexlabs.inradius.Manager.Recycler_Manager;
import com.kodexlabs.inradius.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MadhuRima on 19-03-2017.
 */

public class Fragment_General extends Fragment {

    private AppBarLayout gotoManager;
    private FloatingActionButton fab;

    private List<String> arrayId, arrayName, arrayDept, arrayPos;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general, container, false);

        arrayId = new ArrayList<>();
        arrayName = new ArrayList<>();
        arrayDept = new ArrayList<>();
        arrayPos = new ArrayList<>();

        gotoManager = (AppBarLayout) view.findViewById(R.id.app_bar);
        gotoManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Recycler_Manager.class);
                startActivity(intent);
            }
        });

        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Dialog_Topic.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        Function_URL f_url = new Function_URL();
        String url = f_url.DATA_TOPICS + f_url.ACTION_READ;
        getData(url);

        return view;
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

                        arrayId.add(get_data.getString("id"));
                        arrayName.add(get_data.getString("topic"));
                        arrayPos.add(get_data.getString("desc"));
                    }
                    adapter = new Adapter_General(arrayId, arrayName, arrayPos);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
