package com.kodexlabs.inradius;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    Float mainRating = 2f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_main);

        Function_Pie fp = new Function_Pie();

        TextView test = (TextView)findViewById(R.id.mainRatingPoint);
        test.setText(""+mainRating);

        PieChart pieChart = (PieChart) findViewById(R.id.mainRatingPie);
        fp.makePie(pieChart, mainRating);


        List<String> arrayQuality = Arrays.asList("Punctuality","Attitude","Work","Flexible","Honesty");
        List<String> arrayRating = Arrays.asList("4.0","3.5","4.5","2.0","1.5");

        LinearLayoutManager layoutQuality = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerQuality = (RecyclerView) findViewById(R.id.recyclerQuality);
        recyclerQuality.setLayoutManager(layoutQuality);
        Adapter_Pie adapter_pie = new Adapter_Pie(arrayQuality, arrayRating);
        recyclerQuality.setAdapter(adapter_pie);

        List<String> arrayUser = Arrays.asList("Sandeep Kumar","Madhurima Mondal","Aritra Shom","Pankaj Kr. Singh","Himanshu","Sandeep Kumar","Madhurima Mondal","Aritra Shom","Pankaj Kr. Singh","Himanshu");
        List<String> arrayComment = Arrays.asList("Punctuality","Attitude","Work","Flexible","Honesty","Punctuality","Attitude","Work","Flexible","Honesty");
        List<String> arrayRated = Arrays.asList("4.0","3.5","4.5","2.0","1.5","4.0","3.5","4.5","2.0","1.5");
        List<String> arrayPic = Arrays.asList("4.0","3.5","4.5","2.0","1.5");

        LinearLayoutManager layoutReview = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerReview = (RecyclerView) findViewById(R.id.recyclerReview);
        //recyclerReview.setHasFixedSize(true);
        //recyclerReview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerReview.setLayoutManager(layoutReview);
        Adapter_Review adapter_review = new Adapter_Review(arrayUser, arrayComment, arrayRated);
        recyclerReview.setAdapter(adapter_review);
    }
}
