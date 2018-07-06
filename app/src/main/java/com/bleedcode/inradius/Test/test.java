package com.bleedcode.inradius.Test;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1505560 on 04-Jul-18.
 */

public class test extends Activity {

    private String id, name;

    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);

        update_Data();
    }

    private void update_Data() {
        try {
            id = "510";
            name = "Kiitjj";

            Function_URL f_url = new Function_URL();
            url = f_url.DATA_QUALITIES + f_url.ACTION_UPDATE;
            updateData(id, name);

            //SendDataToServer(id, name);
        } catch (Exception e) {}
    }

    private void updateData(final String id, final String name){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> data = new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("id", id));
                data.add(new BasicNameValuePair("points", name));
                data.add(new BasicNameValuePair("total", name));
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
        sendPostReqAsyncTask.execute(id, name);
    }
}
