package com.kodexlabs.inradius.Main;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

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

/**
 * Created by 1505560 on 17-Jun-17.
 */

public class Activity_Register extends Activity {

    private String id, name, email, pass, level, dept;

    /*private static SharedPreferences preferences;
    private String prefName = "MyPref";
    private static final String UID = "UID";*/

    static String DataParseUrl = "http://kiitecell.hol.es/Inradius_employee_add.php";

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        //preferences = getSharedPreferences(prefName, MODE_PRIVATE);
        //String loggedin = preferences.getString(UID, "UID");

        //if (loggedin.equals("UID"))
        //    facebook_login();
        //else
        //    nextActivity(loggedin);
        getFacebookData();
        //Toast.makeText(getBaseContext(),"jj",Toast.LENGTH_SHORT).show();
    }

    public void Login(View view){
        getFacebookData();
    }

    private void getFacebookData() {
        try {
            id = "128";
            name = "Pradeep";
            email = "radius@gmail.com";
            pass = "M";
            level = "dd/mm/yyyy";
            dept = "dd/mm/yyyy";

            /*SharedPreferences.Editor editor = preferences.edit();
            editor.putString(UID, idFacebook);
            editor.commit();*/

            SendDataToServer(id, name, email, pass, level, dept);
            //nextActivity(idFacebook);

        } catch (Exception e) {}
    }

    private void SendDataToServer(final String id, final String name, final String email, final String pass, final String level, final String dept){
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

    /*private void nextActivity(String bundle){
        if(bundle != null){
            Intent intent = new Intent(getBaseContext(), Activity_Main.class);
            intent.putExtra("idFacebook", bundle);
            startActivity(intent);
            finish();
        }
    }*/

    /*static void facebook_logout(){
        LoginManager.getInstance().logOut();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(UID, "UID");
        editor.commit();
    }*/
}
