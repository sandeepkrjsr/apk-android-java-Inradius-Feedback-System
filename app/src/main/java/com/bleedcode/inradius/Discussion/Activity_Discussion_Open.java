package com.bleedcode.inradius.Discussion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bleedcode.inradius.Main.Function_Review;
import com.bleedcode.inradius.Main.Function_URL;
import com.bleedcode.inradius.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_Discussion_Open extends Activity {

    private RecyclerView recyclerReply;
    private TextView topic, desc, replied, addreply;

    private List<String> arrayIds, arrayReviewer, arrayRated, arrayCommented;

    private String get_id, get_topic, get_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_open);

        Intent bundle = getIntent();
        get_id = bundle.getStringExtra("topic_id");

        topic = (TextView)findViewById(R.id.topic);
        desc = (TextView)findViewById(R.id.desc);
        replied = (TextView)findViewById(R.id.replied);
        addreply = (TextView)findViewById(R.id.addreply);
        recyclerReply = (RecyclerView) findViewById(R.id.recyclerReply);

        arrayIds = new ArrayList<>();
        arrayReviewer = new ArrayList<>();
        arrayRated = new ArrayList<>();
        arrayCommented = new ArrayList<>();

        LinearLayoutManager layoutReply = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerReply.setLayoutManager(layoutReply);

        addreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Discussion_Open.this, Dialog_Reply.class);
                intent.putExtra("topic_id", get_id);
                startActivity(intent);
                finish();
            }
        });

        Function_URL f_url = new Function_URL();
        String url_info = f_url.DATA_TOPICS + f_url.ACTION_FETCH + "&id=" + get_id;
        Info_getData(url_info);
        String url_reply = f_url.DATA_REVIEWS + f_url.ACTION_FETCH + "&id=" + get_id;
        Reply_getData(url_reply);
    }

    private void Info_getData(String url) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("report");
                    JSONObject get_data = result.getJSONObject(0);

                    get_topic = get_data.getString("topic");
                    get_desc = get_data.getString("desc");

                    topic.setText(get_topic);
                    desc.setText(get_desc);
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

    private void Reply_getData(String url) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("report");

                    for (int i = 0; i < result.length(); i++){
                        JSONObject get_data = result.getJSONObject(i);

                        arrayIds.add(get_data.getString("emp_id"));
                        arrayReviewer.add(get_data.getString("emp_name"));
                        arrayRated.add(get_data.getString("rated"));
                        arrayCommented.add(get_data.getString("commented"));
                    }
                    Function_Review adapter_review = new Function_Review(arrayIds, arrayReviewer, arrayCommented, arrayRated);
                    recyclerReply.setAdapter(adapter_review);

                    replied.setText("   |   " + result.length() + " Replied");
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
