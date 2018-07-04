package com.kodexlabs.inradius.Main;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Function_Pie extends Activity {

    //PieChart pieChart ;
    PieDataSet pieDataSet ;
    PieData pieData ;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;

    String mainRatingPoint = "4.8";

    public void Function_Pie(){}

    public void makePie(PieChart pieChart, float v){
        entries = new ArrayList<>();
        PieEntryLabels = new ArrayList<String>();

        entries.add(new BarEntry(v, 0));
        entries.add(new BarEntry(5-v, 1));

        PieEntryLabels.add("");
        PieEntryLabels.add("");

        pieDataSet = new PieDataSet(entries, "");
        pieData = new PieData(PieEntryLabels, pieDataSet);
//        pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
 //       pieDataSet.setColors(ColorTemplate.LIBERTY_COLORS);



        final int[] MY_COLORS = {Color.rgb(255, 207, 64), Color.rgb(249, 246, 218)};
//        final int[] MY_COLORS = {Color.BLACK, Color.CYAN};
//        final int[] MY_COLORS = {R.color.colorPrimary, Color.GRAY};
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int c: MY_COLORS) colors.add(c);

        pieDataSet.setColors(colors);
        pieChart.setData(pieData);
        pieChart.animateY(3000);

        pieData.setDrawValues(false);
        pieChart.setDrawSliceText(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setDescription("");
    }

}
