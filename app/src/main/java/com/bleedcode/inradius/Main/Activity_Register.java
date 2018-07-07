package com.bleedcode.inradius.Main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bleedcode.inradius.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 1505560 on 17-Jun-17.
 */

public class Activity_Register extends Activity {

    private EditText emp_name, emp_email, emp_pass;
    private TextView emp_dept, emp_pos;
    private CircleImageView uploadImage;

    private String id, name, email, pass, pos, dept, level = "0";

    private List<String> measure;

    private String url_quality;

    private Uri imguri = Uri.parse("http://riverfoxrealty.com/wp-content/uploads/2018/02/User-Default.jpg");

    static String DataParseUrl = "http://kiitecell.hol.es/Inradius_employees.php?action=create";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imguri = data.getData();
            uploadImage.setImageURI(imguri);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        emp_name = (EditText)findViewById(R.id.emp_name);
        emp_email = (EditText)findViewById(R.id.emp_email);
        emp_pass = (EditText)findViewById(R.id.emp_pass);
        emp_dept = (TextView)findViewById(R.id.emp_dept);
        emp_pos = (TextView)findViewById(R.id.emp_pos);
        uploadImage = (CircleImageView) findViewById(R.id.uploadImage);

        measure = new ArrayList<>();

        emp_dept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence choices[] = getResources().getStringArray(R.array.department);
                Select_Option("Select Department", choices);
            }
        });

        emp_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence choices[] = getResources().getStringArray(R.array.designation);
                Select_Option("Select Designation", choices);
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, 1);
            }
        });
    }

    private void Select_Option(final String header, final CharSequence[] choices) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(header);
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (header.compareTo("Select Department")==0){
                    emp_dept.setText(choices[which]);
                }else if (header.compareTo("Select Designation")==0){
                    emp_pos.setText(choices[which]);
                    level = ""+which;
                }
            }
        });
        builder.create().show();
    }

    public void Register(View view){
        putData();
        //Toast.makeText(getBaseContext(), ""+imguri,Toast.LENGTH_SHORT).show();
    }

    private void putData() {
        try {
            Random random = new Random();
            int number = 10000 + random.nextInt(89999);

            id = ""+number;
            name = emp_name.getText().toString();
            email = emp_email.getText().toString();
            pass = emp_pass.getText().toString();
            dept = emp_dept.getText().toString();
            pos = emp_pos.getText().toString();

            if (pass.length()>2){
                Function_Image.postImage(getBaseContext(), imguri, id);
                if (imguri != null) {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Inradius");
                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Inradius");
                    StorageReference filepath = storageReference.child(imguri.getLastPathSegment());
                    filepath.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloaduri = taskSnapshot.getDownloadUrl();
                            databaseReference.child(id).setValue(imguri);
                            Toast.makeText(getBaseContext(), "Uploaded Successfully !", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                SendDataToServer(id, name, email, pass, level, pos, dept);

                Function_URL f_url = new Function_URL();
                url_quality = f_url.DATA_QUALITIES + f_url.ACTION_CREATE;
                Quality_sendData(id, name);

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



    private void Quality_sendData(final String id, final String topic){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> data = new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("id", id));
                data.add(new BasicNameValuePair("topic", topic));
                data.add(new BasicNameValuePair("measure[]", "Communication Skills"));
                data.add(new BasicNameValuePair("measure[]", "Management Ability"));
                data.add(new BasicNameValuePair("measure[]", "Cooperation"));
                data.add(new BasicNameValuePair("measure[]", "Interpersonal Skiils"));
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
        sendPostReqAsyncTask.execute(id, topic);
    }
}
