package com.bleedcode.inradius.Profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bleedcode.inradius.Main.Function_Image;
import com.github.mikephil.charting.charts.PieChart;
import com.bleedcode.inradius.Main.Activity_Login;
import com.bleedcode.inradius.Main.Function_Pie;
import com.bleedcode.inradius.Main.Function_Review;
import com.bleedcode.inradius.Main.Function_URL;
import com.bleedcode.inradius.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by MadhuRima on 19-03-2017.
 */

public class Fragment_Profile extends Fragment {

    private RecyclerView recyclerReview, recyclerQuality;
    private TextView topic, desc, rating, reviewers, button;
    private PieChart pieChart;
    private ImageView squareImage, circleImage;

    private List<String> arrayIds, arrayReviewer, arrayRated, arrayCommented, arrayMeasure, arrayPoints, arrayTotal, array_calcRate;;

    private String get_id, get_name, get_pos, get_dept;
    private Float calc_rating = 0f;
    private int calc_percent = 0;

    private Uri imguri = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        //get_timestamp = new SimpleDateFormat("dd MMM yyyy").format(new Date());

        topic = (TextView)view.findViewById(R.id.topic);
        desc = (TextView)view.findViewById(R.id.desc);
        button = (TextView)view.findViewById(R.id.addreview);
        rating = (TextView)view.findViewById(R.id.mainRatingPoint);
        reviewers = (TextView)view.findViewById(R.id.mainRatingReviewers);
        pieChart = (PieChart)view.findViewById(R.id.mainRatingPie);
        recyclerReview = (RecyclerView)view.findViewById(R.id.recyclerReview);
        recyclerQuality = (RecyclerView)view.findViewById(R.id.recyclerQuality);
        circleImage = (ImageView)view.findViewById(R.id.circleImage);
        squareImage = (ImageView)view.findViewById(R.id.squareImage);

        arrayIds = new ArrayList<>();
        arrayReviewer = new ArrayList<>();
        arrayRated = new ArrayList<>();
        arrayCommented = new ArrayList<>();
        arrayMeasure = new ArrayList<>();
        array_calcRate = new ArrayList<>();

        LinearLayoutManager layoutReview = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerReview.setLayoutManager(layoutReview);

        LinearLayoutManager layoutQuality = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerQuality.setLayoutManager(layoutQuality);

        squareImage.setVisibility(View.GONE);
        circleImage.setVisibility(View.VISIBLE);
        Function_Image.getImage(getContext(), circleImage, Activity_Login.loggedin);

        button.setText("Logout");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().equals("Logout")){
                    Activity_Login.logout();
                    Intent intent = new Intent(getContext(), Activity_Login.class);
                    startActivity(intent);
                }
                if (button.getText().equals("Upload")) {
                    Function_Image.postImage(getContext(), imguri, Activity_Login.loggedin);
                    button.setText("Logout");
                }
            }
        });

        circleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, 1);

                button.setText("Upload");
            }
        });

        Function_URL f_url = new Function_URL();
        String url_info = f_url.DATA_EMPLOYEES + f_url.ACTION_FETCH + "&id=" + Activity_Login.loggedin;
        Info_getData(url_info);
        String url_review = f_url.DATA_REVIEWS + f_url.ACTION_FETCH + "&id=" + Activity_Login.loggedin;
        Review_getData(url_review);
        String url_quality = f_url.DATA_QUALITIES + f_url.ACTION_FETCH + "&id=" + Activity_Login.loggedin;
        Quality_getData(url_quality);

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
                    desc.setText(get_pos + ", " + get_dept);
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

                        arrayIds.add(get_data.getString("emp_id"));
                        arrayReviewer.add(get_data.getString("emp_name"));
                        arrayRated.add(get_data.getString("rated"));
                        arrayCommented.add(get_data.getString("commented"));

                        calc_rating += Float.parseFloat(get_data.getString("rated"));
                    }
                    Function_Review adapter_review = new Function_Review(arrayIds, arrayReviewer, arrayCommented, arrayRated);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imguri = data.getData();
            circleImage.setImageURI(imguri);
        }
    }
}
