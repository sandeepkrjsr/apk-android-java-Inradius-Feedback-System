package com.kodexlabs.inradius.General;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kodexlabs.inradius.Main.Activity_Login;
import com.kodexlabs.inradius.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1505560 on 01-Jul-18.
 */

public class Dialog_Review extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText comment;
    private CheckBox anonymous;
    private Button next, submit;
    private RecyclerView recyclerMeasure;

    private List<String> arrayMeasure;
    private List<Float> arrayRates;

    private String get_topicid, id, topic_id, emp_id, emp_name, rated, commented;

    static String DataParseUrl = "http://kiitecell.hol.es/Inradius_reviews.php?action=create";
    static String DATA_QUALITY = "http://kiitecell.hol.es/Inradius_qualities.php?action=fetch";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_review);
        setTitle("Feedback");

        Intent bundle = getIntent();
        get_topicid = bundle.getStringExtra("topic_id");

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        comment = (EditText)findViewById(R.id.comment);
        anonymous = (CheckBox)findViewById(R.id.anonymous);
        next = (Button)findViewById(R.id.next);
        submit = (Button)findViewById(R.id.submit);
        recyclerMeasure = (RecyclerView) findViewById(R.id.recyclerMeasure);

        arrayMeasure = new ArrayList<>();
        arrayRates = new ArrayList<>();

        LinearLayoutManager layoutMeasure = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerMeasure.setLayoutManager(layoutMeasure);

        Rating_getData(get_topicid);
    }

    public void Next(View view){
        ratingBar.setVisibility(View.GONE);
        recyclerMeasure.setVisibility(View.GONE);
        comment.setVisibility(View.VISIBLE);
        anonymous.setVisibility(View.VISIBLE);
        next.setVisibility(View.GONE);
        submit.setVisibility(View.VISIBLE);
    }

    public void Submit(View view){
        getData();
        Intent intent = new Intent(getBaseContext(), Activity_Info.class);
        intent.putExtra("topic_id", get_topicid);
        startActivity(intent);
        finish();
    }

    private void getData() {
        try {
            topic_id = get_topicid;
            emp_id = Activity_Login.loggedin;
            emp_name = Activity_Login.loggedname;
            rated = ""+ratingBar.getRating();
            commented = comment.getText().toString();
            id = topic_id + emp_id;

            if (anonymous.isChecked())
                emp_name = "Anonymous";

            SendDataToServer(id, topic_id, emp_id, emp_name, rated, commented);
        } catch (Exception e) {}
    }
    private void SendDataToServer(final String id, final String topic_id, final String emp_id, final String emp_name, final String rated, final String commented){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> data = new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("id", id));
                data.add(new BasicNameValuePair("topic_id", topic_id));
                data.add(new BasicNameValuePair("emp_id", emp_id));
                data.add(new BasicNameValuePair("emp_name", emp_name));
                data.add(new BasicNameValuePair("rated", rated));
                data.add(new BasicNameValuePair("commented", commented));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(DataParseUrl);
                    httpPost.setEntity(new UrlEncodedFormEntity(data));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                }
                return "Data Submit Successfully";
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(id, topic_id, emp_id, emp_name, rated, commented);
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
                arrayRates.add(0.0f);
            }
            Adapter_Measure adapter_measure = new Adapter_Measure(arrayMeasure, arrayRates);
            recyclerMeasure.setAdapter(adapter_measure);

        } catch (JSONException e) {
        }
    }
}