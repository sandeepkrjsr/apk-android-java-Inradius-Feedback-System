package com.bleedcode.inradius.Discussion;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.bleedcode.inradius.Main.Activity_Dashboard;
import com.bleedcode.inradius.Main.Activity_Login;
import com.bleedcode.inradius.Main.Function_URL;
import com.bleedcode.inradius.R;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by 1505560 on 01-Jul-18.
 */

public class Dialog_Create extends AppCompatActivity {

    private EditText input_discuss;
    private CheckBox anonymous;

    private String id, topic, desc, maker;

    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_discussion);
        setTitle("Ask Question");

        input_discuss = (EditText)findViewById(R.id.input_discuss);
        anonymous = (CheckBox)findViewById(R.id.anonymous);
    }

    public void Submit(View view){
        putData();
        Intent intent = new Intent(getBaseContext(), Activity_Dashboard.class);
        startActivity(intent);
        finish();
    }

    private void putData() {
        Random random = new Random();
        int number = 999 + random.nextInt(9000);

        id = ""+number;
        topic = input_discuss.getText().toString();
        desc = Activity_Login.loggedname;
        maker = Activity_Login.loggedin;

        if (anonymous.isChecked()){
            desc = "Anonymous";
        }
        if (!topic.isEmpty()){
            Function_URL f_url = new Function_URL();
            url = f_url.DATA_TOPICS + f_url.ACTION_CREATE;
            sendData(id, topic, desc, maker);
        }
    }

    private void sendData(final String id, final String topic, final String desc, final String maker){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> data = new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("id", id));
                data.add(new BasicNameValuePair("topic", topic));
                data.add(new BasicNameValuePair("desc", desc + " | " + new SimpleDateFormat("dd MMM yyyy").format(new Date())));
                data.add(new BasicNameValuePair("maker", maker));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url);
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
}
