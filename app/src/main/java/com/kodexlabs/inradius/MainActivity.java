package com.kodexlabs.inradius;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

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
    }
}
