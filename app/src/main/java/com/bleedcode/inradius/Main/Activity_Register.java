package com.bleedcode.inradius.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
 * Created by 1505560 on 17-Jun-17.
 */

public class Activity_Register extends Activity {

    private EditText emp_id, emp_name, emp_email, emp_pass, emp_level, emp_pos, emp_dept;

    private String id, name, email, pass, level, pos, dept;

    static String DataParseUrl = "http://kiitecell.hol.es/Inradius_employees.php?action=create";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emp_id = (EditText)findViewById(R.id.emp_id);
        emp_name = (EditText)findViewById(R.id.emp_name);
        emp_email = (EditText)findViewById(R.id.emp_email);
        emp_pass = (EditText)findViewById(R.id.emp_pass);
        emp_level = (EditText)findViewById(R.id.emp_level);
        emp_pos = (EditText)findViewById(R.id.emp_pos);
        emp_dept = (EditText)findViewById(R.id.emp_dept);
    }

    public void Register(View view){
        putData();
    }

    private void putData() {
        try {
            id = emp_id.getText().toString();
            name = emp_name.getText().toString();
            email = emp_email.getText().toString();
            pass = emp_pass.getText().toString();
            level = emp_level.getText().toString();
            pos = emp_pos.getText().toString();
            dept = emp_dept.getText().toString();

            if (id.length()==5 && email.contains("@radius.com") && pass.length()>5){
                SendDataToServer(id, name, email, pass, level, pos, dept);

                Activity_Login.userSaved(id, name, level);
                Intent intent = new Intent(getBaseContext(), Activity_Login.class);
                startActivity(intent);
            }else {
                Toast.makeText(getBaseContext(), "Incorrect Info",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {}
    }

    private void SendDataToServer(final String id, final String name, final String email, final String pass, final String level, final String pos, final String dept){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> data = new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("id", id));
                data.add(new BasicNameValuePair("name", name));
                data.add(new BasicNameValuePair("email", email));
                data.add(new BasicNameValuePair("pass", pass));
                data.add(new BasicNameValuePair("level", level));
                data.add(new BasicNameValuePair("dept", dept));

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
        sendPostReqAsyncTask.execute(id, name, email, pass, level, dept);
    }
}
