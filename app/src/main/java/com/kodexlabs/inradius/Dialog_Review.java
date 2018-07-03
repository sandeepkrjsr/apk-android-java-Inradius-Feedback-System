package com.kodexlabs.inradius;

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
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

    private RatingBar rating;
    private EditText comment;
    private CheckBox anonymous;
    private Button next, submit;

    private String get_topicid, id, topic_id, emp_id, emp_name, rated, commented;

    static String DataParseUrl = "http://kiitecell.hol.es/Inradius_review_add.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_review);

        Intent bundle = getIntent();
        get_topicid = bundle.getStringExtra("topic_id");

        rating = (RatingBar) findViewById(R.id.rating);
        comment = (EditText)findViewById(R.id.comment);
        anonymous = (CheckBox)findViewById(R.id.anonymous);
        next = (Button)findViewById(R.id.next);
        submit = (Button)findViewById(R.id.submit);
    }

    public void Next(View view){
        rating.setVisibility(View.GONE);
        comment.setVisibility(View.VISIBLE);
        anonymous.setVisibility(View.VISIBLE);
        next.setVisibility(View.GONE);
        submit.setVisibility(View.VISIBLE);
    }

    public void Submit(View view){
        getData();
        finish();
    }

    private void getData() {
        try {
            topic_id = get_topicid;
            emp_id = Activity_Login.loggedin;
            emp_name = Activity_Login.loggedname;
            rated = ""+rating.getRating();
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
}
