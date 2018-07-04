package com.kodexlabs.inradius.General;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.kodexlabs.inradius.Main.Activity_Login;
import com.kodexlabs.inradius.Main.Function_URL;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 1505560 on 01-Jul-18.
 */

public class Dialog_Topic extends AppCompatActivity {

    private LinearLayout measures;
    private EditText input_topic, input_desc, input_measure1, input_measure2, input_measure3, input_measure4, input_measure5;
    private Button next, submit;

    private String id, topic, desc, maker , measure1, measure2, measure3, measure4, measure5;

    private String url_topic, url_quality;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_topic);
        setTitle("Add Topic");

        input_topic = (EditText)findViewById(R.id.input_topic);
        input_desc = (EditText)findViewById(R.id.input_desc);
        measures = (LinearLayout)findViewById(R.id.measures);
        input_measure1 = (EditText)findViewById(R.id.input_measure1);
        input_measure2 = (EditText)findViewById(R.id.input_measure2);
        input_measure3 = (EditText)findViewById(R.id.input_measure3);
        input_measure4 = (EditText)findViewById(R.id.input_measure4);
        input_measure5 = (EditText)findViewById(R.id.input_measure5);
        next = (Button)findViewById(R.id.next);
        submit = (Button)findViewById(R.id.submit);
    }

    public void Next(View view){
        input_topic.setVisibility(View.GONE);
        input_desc.setVisibility(View.GONE);
        measures.setVisibility(View.VISIBLE);
        next.setVisibility(View.GONE);
        submit.setVisibility(View.VISIBLE);
    }

    public void Submit(View view){
        putData();
        Intent intent = new Intent(getBaseContext(), Recycler_General.class);
        startActivity(intent);
    }

    private void putData() {
        Random random = new Random();
        int number = 99 + random.nextInt(900);

        id = ""+number;
        topic = input_topic.getText().toString();
        desc = input_desc.getText().toString();
        maker = Activity_Login.loggedin;

        measure1 = input_measure1.getText().toString();
        measure2 = input_measure2.getText().toString();
        measure3 = input_measure3.getText().toString();
        measure4 = input_measure4.getText().toString();
        measure5 = input_measure5.getText().toString();

        if (!topic.isEmpty()){
            Function_URL f_url = new Function_URL();
            url_topic = f_url.DATA_TOPICS + f_url.ACTION_CREATE;
            Topic_sendData(id, topic, desc, maker);
            url_quality = f_url.DATA_QUALITIES + f_url.ACTION_CREATE;
            Quality_sendData(id, topic, measure1, measure2, measure3, measure4, measure5);
        }
    }

    private void Topic_sendData(final String id, final String topic, final String desc, final String maker){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> data = new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("id", id));
                data.add(new BasicNameValuePair("topic", topic));
                data.add(new BasicNameValuePair("desc", desc));
                data.add(new BasicNameValuePair("maker", maker));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url_topic);
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
        sendPostReqAsyncTask.execute(id, topic, desc, maker);
    }

    private void Quality_sendData(final String id, final String topic, final String measure1, final String measure2, final String measure3, final String measure4, final String measure5){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> data = new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("id", id));
                data.add(new BasicNameValuePair("topic", topic));
                data.add(new BasicNameValuePair("measure1", measure1));
                data.add(new BasicNameValuePair("measure2", measure2));
                data.add(new BasicNameValuePair("measure3", measure3));
                data.add(new BasicNameValuePair("measure4", measure4));
                data.add(new BasicNameValuePair("measure5", measure5));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url_quality);
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
        sendPostReqAsyncTask.execute(id, topic, measure1, measure2, measure3, measure4, measure5);
    }
}
