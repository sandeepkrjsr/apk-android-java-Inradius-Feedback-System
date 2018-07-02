package com.kodexlabs.inradius;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
 * Created by 1505560 on 01-Jul-18.
 */

public class Dialog_Review extends AppCompatActivity {

    private LinearLayout rating;
    private EditText comment;
    private CheckBox anonymous;
    private Button next, submit;
    private RecyclerView recyclerQuality;

    private List<String> arrayMeasure;


    static String DATA_QUALITY = "http://kiitecell.hol.es/Inradius_quality_all.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_review);

        rating = (LinearLayout)findViewById(R.id.rating);
        comment = (EditText)findViewById(R.id.comment);
        anonymous = (CheckBox)findViewById(R.id.anonymous);
        next = (Button)findViewById(R.id.next);
        submit = (Button)findViewById(R.id.submit);
        recyclerQuality = (RecyclerView) findViewById(R.id.recyclerQuality);

        arrayMeasure = new ArrayList<>();

        LinearLayoutManager layoutQuality = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerQuality.setLayoutManager(layoutQuality);
    }

    public void Next(View view){
        rating.setVisibility(View.GONE);
        comment.setVisibility(View.VISIBLE);
        anonymous.setVisibility(View.VISIBLE);
        next.setVisibility(View.GONE);
        submit.setVisibility(View.VISIBLE);
    }

    public void Submit(View view){
        finish();
    }

    private void Rating_getData(String id) {
        String url = DATA_QUALITY + "?id=" + id;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Rating_showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void Rating_showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++){
                JSONObject get_data = result.getJSONObject(i);

                arrayMeasure.add(get_data.getString("measure"));
            }
            Adapter_Quality adapter_quality = new Adapter_Quality(arrayMeasure);
            recyclerQuality.setAdapter(adapter_quality);

        } catch (JSONException e) {
        }
    }
}
