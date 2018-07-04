package com.kodexlabs.inradius.General;

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
import com.github.mikephil.charting.charts.PieChart;
import com.kodexlabs.inradius.Main.Function_Pie;
import com.kodexlabs.inradius.Main.Function_Review;
import com.kodexlabs.inradius.Main.Function_URL;
import com.kodexlabs.inradius.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

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

        LinearLayoutManager layoutQuality = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerQuality.setLayoutManager(layoutQuality);

        LinearLayoutManager layoutReview = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerReview.setLayoutManager(layoutReview);

        addreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Info.this, Dialog_Review.class);
                intent.putExtra("topic_id", get_id);
                startActivity(intent);
                finish();
            }
        });

        Function_URL f_url = new Function_URL();
        String url_info = f_url.DATA_TOPICS + f_url.ACTION_FETCH + "&id=" + get_id;
        Info_getData(url_info);
        String url_review = f_url.DATA_REVIEWS + f_url.ACTION_FETCH + "&id=" + get_id;
        Review_getData(url_review);
        String url_quality = f_url.DATA_QUALITIES + f_url.ACTION_FETCH + "&id=" + get_id;
        Quality_getData(url_quality);
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

    private void Review_getData(String url) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                    Function_Review adapter_review = new Function_Review(arrayReviewer, arrayCommented, arrayRated);
                    recyclerReview.setAdapter(adapter_review);

                    reviewers.setText(result.length()+" Reviews");
                    rating.setText(Math.round((calc_rating / result.length()) * 10.0) / 10.0+"");
                    Function_Pie fp = new Function_Pie();
                    fp.makePie(pieChart, calc_rating / result.length());
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

    private void Quality_getData(String url) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("report");

                    for (int i = 0; i < result.length(); i++){
                        JSONObject get_data = result.getJSONObject(i);

                        arrayMeasure.add(get_data.getString("measure"));

                        if (!get_data.getString("total").equals("0"))
                            calc_percent = Integer.parseInt(get_data.getString("points"))*100/Integer.parseInt(get_data.getString("total"));
                        array_calcRate.add(""+calc_percent);
                    }
                    Adapter_QualityPie adapter_qualityPie = new Adapter_QualityPie(arrayMeasure, array_calcRate);
                    recyclerQuality.setAdapter(adapter_qualityPie);

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
