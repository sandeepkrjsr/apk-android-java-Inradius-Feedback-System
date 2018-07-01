package com.kodexlabs.inradius;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

public class Day extends Activity {

    private List<String> array_time, array_title, array_venue, array_description, array_speaker, array_profile;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    LinearLayout internet;
    static String DATA_URL = "http://kiitecell.hol.es/Inradius_review_all.php";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);

        array_time = new ArrayList<>();
        array_title = new ArrayList<>();
        array_venue = new ArrayList<>();
        array_description = new ArrayList<>();
        array_speaker = new ArrayList<>();
        array_profile = new ArrayList<>();


        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(layoutManager);

        internet = (LinearLayout)findViewById(R.id.internet);

            getData("24");
    }

    private void getData(String id) {
        String url = DATA_URL + "?id=" + id;

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

                array_time.add(get_data.getString("topic_id"));
                array_title.add(get_data.getString("emp_id"));
                array_venue.add(get_data.getString("emp_name"));
                array_description.add(get_data.getString("rated"));
                array_speaker.add(get_data.getString("commented"));
                array_profile.add(get_data.getString("anonymous"));
            }

            adapter = new Adapter_Schedule(array_time, array_title, array_venue, array_description, array_speaker, array_profile);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
        }
    }
}
