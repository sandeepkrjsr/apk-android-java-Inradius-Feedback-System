package com.kodexlabs.inradius;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Activity_fetchtopic extends AppCompatActivity {

    private TextView id, topic, desc, rating, reviewers;

    private String get_id, get_topic, get_desc, get_rating, get_reviewers;

    static String DATA_URL = "http://kiitecell.hol.es/Inradius_topic_fetch.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetchtopic);
        Intent bundle = getIntent();

        get_id = bundle.getStringExtra("idFacebook");
        getData(get_id);

        id = (TextView)findViewById(R.id.id);
        topic = (TextView)findViewById(R.id.topic);
        desc = (TextView)findViewById(R.id.desc);
        rating = (TextView)findViewById(R.id.rating);
        reviewers = (TextView)findViewById(R.id.reviewers);
    }

    private void getData(String idFacebook) {
        String url = DATA_URL + "?id=24";

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
            JSONObject get_data = result.getJSONObject(0);

            get_id = get_data.getString("id");
            get_topic = get_data.getString("topic");
            get_desc = get_data.getString("desc");
            get_rating = get_data.getString("rating");
            get_reviewers = get_data.getString("reviewers");

            id.setText("Name : " + get_id);
            topic.setText("Name : " + get_topic);
            desc.setText("Email : " + get_desc);
            rating.setText("Gender : " + get_rating);
            reviewers.setText("Birthday : " + get_reviewers);

        } catch (JSONException e) {
        }
    }
}
