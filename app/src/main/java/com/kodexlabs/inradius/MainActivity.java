package com.kodexlabs.inradius;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView recyclerReview, recyclerQuality;
    private TextView id, topic, desc, rating, reviewers;
    private PieChart pieChart;

    private List<String> arrayReviewer, arrayRated, arrayCommented;

    private String get_id, get_topic, get_desc, get_rating, get_reviewers;

    static String DATA_INFO = "http://kiitecell.hol.es/Inradius_topic_fetch.php";
    static String DATA_REVIEW = "http://kiitecell.hol.es/Inradius_review_all.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_main);

        topic = (TextView)findViewById(R.id.topic);
        desc = (TextView)findViewById(R.id.desc);
        rating = (TextView)findViewById(R.id.mainRatingPoint);
        reviewers = (TextView)findViewById(R.id.mainRatingReviewers);
        pieChart = (PieChart) findViewById(R.id.mainRatingPie);
        recyclerReview = (RecyclerView) findViewById(R.id.recyclerReview);
        recyclerQuality = (RecyclerView) findViewById(R.id.recyclerQuality);

        arrayReviewer = new ArrayList<>();
        arrayRated = new ArrayList<>();
        arrayCommented = new ArrayList<>();

        Info_getData("24");
        Review_getData("24");

        List<String> arrayQuality = Arrays.asList("Punctuality","Attitude","Work","Flexible","Honesty");
        List<String> arrayRating = Arrays.asList("4.0","3.5","4.5","2.0","1.5");

        LinearLayoutManager layoutQuality = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerQuality.setLayoutManager(layoutQuality);
        Adapter_Pie adapter_pie = new Adapter_Pie(arrayQuality, arrayRating);
        recyclerQuality.setAdapter(adapter_pie);

        LinearLayoutManager layoutReview = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerReview.setLayoutManager(layoutReview);

        TextView addreview = (TextView)findViewById(R.id.addreview);
        addreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity_Review.class);
                startActivity(intent);
            }
        });
    }

    private void Info_getData(String id) {
        String url = DATA_INFO + "?id=" + id;
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
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject get_data = result.getJSONObject(0);

            get_topic = get_data.getString("topic");
            get_desc = get_data.getString("desc");
            get_rating = get_data.getString("rating");
            get_reviewers = get_data.getString("reviewers");

            topic.setText(get_topic);
            desc.setText(get_desc);
            rating.setText(get_rating);
            reviewers.setText(get_reviewers + " Reviews");

            Function_Pie fp = new Function_Pie();
            fp.makePie(pieChart, Float.parseFloat(get_rating));

        } catch (JSONException e) {
        }
    }

    private void Review_getData(String id) {
        String url = DATA_REVIEW + "?id=" + id;
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
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++){
                JSONObject get_data = result.getJSONObject(i);

                arrayReviewer.add(get_data.getString("emp_name"));
                arrayRated.add(get_data.getString("rated"));
                arrayCommented.add(get_data.getString("commented"));
            }
            Adapter_Review adapter_review = new Adapter_Review(arrayReviewer, arrayCommented, arrayRated);
            recyclerReview.setAdapter(adapter_review);

        } catch (JSONException e) {
        }
    }
}
