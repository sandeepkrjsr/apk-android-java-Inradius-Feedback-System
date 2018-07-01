package com.kodexlabs.inradius;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by 1505560 on 01-Jul-18.
 */

public class Activity_Review extends AppCompatActivity {

    private LinearLayout rating;
    private EditText comment;
    private CheckBox anonymous;
    private Button next, submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        rating = (LinearLayout)findViewById(R.id.rating);
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
        finish();
    }
}
