package com.kodexlabs.inradius;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Activity_Login extends Activity {

    private TextView email, pass;

    private String input_email, input_pass, get_email, get_pass, get_id, get_name;

    private static SharedPreferences preferences;
    private String prefName = "MyPref";
    private static final String UID = "UID";
    private static final String USER = "USER";
    public static String loggedin;
    public static String loggedname;

    static String DATA_URL = "http://kiitecell.hol.es/Inradius_employee_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (TextView)findViewById(R.id.email);
        pass = (TextView)findViewById(R.id.pass);

        preferences = getSharedPreferences(prefName, MODE_PRIVATE);
        loggedin = preferences.getString(UID, "UID");
        loggedname = preferences.getString(USER, "USER");

        if (!loggedin.equals("UID"))
            nextActivity(loggedin);
    }

    public void Login(View view) {
        input_email = email.getText().toString();
        input_pass = pass.getText().toString();

        String url = DATA_URL + "?email=" + input_email + "&pass=" + input_pass;

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

            get_id = get_data.getString("id");
            get_name = get_data.getString("name");
            get_email = get_data.getString("email");
            get_pass = get_data.getString("pass");

            if (get_email.compareTo(input_email)==0){
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(UID, get_id);
                editor.putString(USER, get_name);
                editor.commit();
                Intent intent = new Intent(getBaseContext(), Activity_Login.class);
                startActivity(intent);
            }else {
                Toast.makeText(getBaseContext(), "Login Failed!",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
        }
    }

    private void nextActivity(String bundle){
        if(bundle != null){
            Intent intent = new Intent(getBaseContext(), Activity_Dashboard.class);
            startActivity(intent);
            finish();
        }
    }
}
