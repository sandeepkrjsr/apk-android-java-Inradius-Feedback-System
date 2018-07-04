package com.kodexlabs.inradius;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.kodexlabs.inradius.Main.Function_Pie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_Info extends Activity {

    private RecyclerView recyclerReview, recyclerQuality;
    private TextView topic, desc, rating, reviewers, addreview;
    private PieChart pieChart;

    private List<String> arrayReviewer, arrayRated, arrayCommented, arrayMeasure, arrayPoints, arrayTotal, array_calcRate;

    private String get_id, get_topic, get_desc;
    private Float calc_rating = 0f;
    private int calc_percent = 0;

    static String DATA_INFO = "http://kiitecell.hol.es/Inradius_topics.php?action=fetch";
    static String DATA_REVIEW = "http://kiitecell.hol.es/Inradius_reviews.php?action=fetch";
    static String DATA_QUALITY = "http://kiitecell.hol.es/Inradius_qualities.php?action=fetch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_main);

        Intent bundle = getIntent();
        get_id = bundle.getStringExtra("topic_id");

        topic = (TextView)findViewById(R.id.topic);
        desc = (TextView)findViewById(R.id.desc);
        addreview = (TextView)findViewById(R.id.addreview);
        rating = (TextView)findViewById(R.id.mainRatingPoint);
        reviewers = (TextView)findViewById(R.id.mainRatingReviewers);
        pieChart = (PieChart) findViewById(R.id.mainRatingPie);
        recyclerReview = (RecyclerView) findViewById(R.id.recyclerReview);
        recyclerQuality = (RecyclerView) findViewById(R.id.recyclerQuality);

        arrayReviewer = new ArrayList<>();
        arrayRated = new ArrayList<>();
        arrayCommented = new ArrayList<>();
        arrayMeasure = new ArrayList<>();
        arrayPoints = new ArrayList<>();
        arrayTotal = new ArrayList<>();
        array_calcRate = new ArrayList<>();

        Info_getData(get_id);
        Review_getData(get_id);
        Rating_getData(get_id);

        LinearLayoutManager layoutQuality = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerQuality.setLayoutManager(layoutQuality);

        LinearLayoutManager layoutReview = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerReview.setLayoutManager(layoutReview);

        addreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Info.this, Dialog_Review_Add.class);
                intent.putExtra("topic_id", get_id);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Info_getData(String id) {
        String url = DATA_INFO + "&id=" + id;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Info_showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void Info_showJSON(String response){
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

    private void Review_getData(String id) {
        String url = DATA_REVIEW + "&id=" + id;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Review_showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void Review_showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("report");

            for (int i = 0; i < result.length(); i++){
                JSONObject get_data = result.getJSONObject(i);

                arrayReviewer.add(get_data.getString("emp_name"));
                arrayRated.add(get_data.getString("rated"));
                arrayCommented.add(get_data.getString("commented"));

                calc_rating += Float.parseFloat(get_data.getString("rated"));
            }
            Adapter_Review adapter_review = new Adapter_Review(arrayReviewer, arrayCommented, arrayRated);
            recyclerReview.setAdapter(adapter_review);

            reviewers.setText(result.length()+" Reviews");
            rating.setText(Math.round((calc_rating / result.length()) * 10.0) / 10.0+"");
            Function_Pie fp = new Function_Pie();
            fp.makePie(pieChart, calc_rating / result.length());
        } catch (JSONException e) {
        }
    }

    private void Rating_getData(String id) {
        String url = DATA_QUALITY + "&id=" + id;
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
            JSONArray result = jsonObject.getJSONArray("report");

            for (int i = 0; i < result.length(); i++){
                JSONObject get_data = result.getJSONObject(i);

                arrayMeasure.add(get_data.getString("measure"));

                calc_percent = Integer.parseInt(get_data.getString("points"))*100/Integer.parseInt(get_data.getString("total"));
                array_calcRate.add(""+calc_percent);
            }
            Adapter_Pie adapter_pie = new Adapter_Pie(arrayMeasure, array_calcRate);
            recyclerQuality.setAdapter(adapter_pie);

        } catch (JSONException e) {
        }
    }
}
