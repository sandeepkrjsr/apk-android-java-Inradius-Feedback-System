package com.kodexlabs.inradius.General;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.kodexlabs.inradius.Main.Function_Pie;
import com.kodexlabs.inradius.R;

import java.util.List;

/**
 * Created by 1505560 on 10-Feb-18.
 */

class Adapter_QualityPie extends RecyclerView.Adapter<Adapter_QualityPie.ViewHolder> {
    Context context;

    private PieChart pieChart;
    private TextView Quality, Rating;

    private List<String> arrayQuality, arrayRating;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            pieChart = (PieChart) view.findViewById(R.id.normalRatingPie);
            Quality = (TextView) view.findViewById(R.id.normalRatingText);
            Rating = (TextView)view.findViewById(R.id.normalRatingPoint);
        }
    }

    public Adapter_QualityPie(List<String> arrayQuality, List<String> arrayRating) {
        this.arrayQuality = arrayQuality;
        this.arrayRating = arrayRating;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_pie, parent, false);
        context=  view.getContext();
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Function_Pie fp = new Function_Pie();
        fp.makePie(pieChart, Float.parseFloat(arrayRating.get(position))/20);
        Quality.setText(arrayQuality.get(position));
        Rating.setText(arrayRating.get(position)+"%");
    }

    @Override
    public int getItemCount() {
        return arrayQuality.size();
    }
}
