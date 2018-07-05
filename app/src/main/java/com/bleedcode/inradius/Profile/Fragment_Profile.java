package com.bleedcode.inradius.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.bleedcode.inradius.Main.Activity_Login;
import com.bleedcode.inradius.Main.Function_Pie;
import com.bleedcode.inradius.Main.Function_Review;
import com.bleedcode.inradius.Main.Function_URL;
import com.bleedcode.inradius.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MadhuRima on 19-03-2017.
 */

public class Fragment_Profile extends Fragment {

    private RecyclerView recyclerReview;
    private TextView topic, desc, rating, reviewers, addreview;
    private PieChart pieChart;
    private Button logout;

    private List<String> arrayReviewer, arrayRated, arrayCommented;

    private String get_id, get_name, get_pos, get_dept;
    private Float calc_rating = 0f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);

        topic = (TextView)view.findViewById(R.id.topic);
        desc = (TextView)view.findViewById(R.id.desc);
        addreview = (TextView)view.findViewById(R.id.addreview);
        rating = (TextView)view.findViewById(R.id.mainRatingPoint);
        reviewers = (TextView)view.findViewById(R.id.mainRatingReviewers);
        pieChart = (PieChart)view.findViewById(R.id.mainRatingPie);
        recyclerReview = (RecyclerView)view.findViewById(R.id.recyclerReview);
        logout = (Button) view.findViewById(R.id.logout);

        arrayReviewer = new ArrayList<>();
        arrayRated = new ArrayList<>();
        arrayCommented = new ArrayList<>();

        LinearLayoutManager layoutReview = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerReview.setLayoutManager(layoutReview);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity_Login.logout();
                Intent intent = new Intent(getContext(), Activity_Login.class);
                startActivity(intent);
            }
        });

        addreview.setVisibility(View.INVISIBLE);

        Function_URL f_url = new Function_URL();
        String url_info = f_url.DATA_EMPLOYEES + f_url.ACTION_FETCH + "&id=" + Activity_Login.loggedin;
        Info_getData(url_info);
        String url_review = f_url.DATA_REVIEWS + f_url.ACTION_FETCH + "&id=" + Activity_Login.loggedin;
        Review_getData(url_review);

        return view;
    }

    private void Info_getData(String url) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("report");
                    JSONObject get_data = result.getJSONObject(0);

                    get_name = get_data.getString("name");
                    get_pos = get_data.getString("pos");
                    get_dept = get_data.getString("dept");

                    topic.setText(get_name);
                    desc.setText(get_pos + "\n" + get_dept);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
