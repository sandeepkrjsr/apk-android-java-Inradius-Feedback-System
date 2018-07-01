package com.kodexlabs.inradius;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Activity_fetchtopic extends AppCompatActivity {

    private TextView idFacebook, name, email, gender, birthday, location;

    private String get_idFacebook, get_name, get_email, get_gender, get_birthday, get_location;

    static String DATA_URL = "http://kiitecell.hol.es/Inradius_topic_fetch.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetchtopic);
        Intent bundle = getIntent();

        get_idFacebook = bundle.getStringExtra("idFacebook");
        getData(get_idFacebook);

        idFacebook = (TextView)findViewById(R.id.id);
        name = (TextView)findViewById(R.id.topic);
        email = (TextView)findViewById(R.id.desc);
        gender = (TextView)findViewById(R.id.rating);
        birthday = (TextView)findViewById(R.id.reviewers);

        idFacebook.setText("Id : " + get_idFacebook);
        //profilePictureView = (ProfilePictureView)findViewById(R.id.picture);
        //profilePictureView.setProfileId(get_idFacebook);

        /*ShareDialog shareDialog = new ShareDialog(this);
        ShareLinkContent content = new ShareLinkContent.Builder().build();
        shareDialog.shower(content);*/
    }

    private void getData(String idFacebook) {
        String url = DATA_URL + "?id=36226";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject get_data = result.getJSONObject(0);

            get_name = get_data.getString("id");
            get_email = get_data.getString("name");
            get_gender = get_data.getString("email");
            get_birthday = get_data.getString("pass");
            get_gender = get_data.getString("level");
            get_birthday = get_data.getString("dept");

            name.setText("Name : " + get_name);
            email.setText("Email : " + get_email);
            gender.setText("Gender : " + get_gender);
            birthday.setText("Birthday : " + get_birthday);

        } catch (JSONException e) {
        }
    }
}
