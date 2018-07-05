package com.bleedcode.inradius.General;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bleedcode.inradius.R;

import java.util.List;

/**
 * Created by 1505560 on 10-Feb-18.
 */

class Adapter_Measure extends RecyclerView.Adapter<Adapter_Measure.ViewHolder> {
    Context context;

    private TextView measureName;
    private RatingBar measureRateus;

    private List<String> arrayMeasure;
    private List<Float> arrayRates;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            measureName = (TextView) view.findViewById(R.id.measureName);
            measureRateus = (RatingBar) view.findViewById(R.id.measureRateus);
        }
    }

    public Adapter_Measure(List<String> arrayMeasure, List<Float> arrayRates) {
        this.arrayMeasure = arrayMeasure;
        this.arrayRates = arrayRates;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_rateus, parent, false);
        context=  view.getContext();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        measureName.setText(arrayMeasure.get(position));

        measureRateus.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                arrayRates.set(position, rating);
                Float sum = 0.0f;
                for (int i=0; i < arrayRates.size(); i++)
                    sum += arrayRates.get(i);
                //Float avg = sum / arrayRates.size();
                //Toast.makeText(context, avg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayMeasure.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
