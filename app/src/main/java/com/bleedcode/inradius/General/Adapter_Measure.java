package com.bleedcode.inradius.General;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bleedcode.inradius.Main.Function_URL;
import com.bleedcode.inradius.R;

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
 * Created by 1505560 on 10-Feb-18.
 */

public class Adapter_Measure extends RecyclerView.Adapter<Adapter_Measure.ViewHolder> {
    Context context;

    private RatingBar overallRating;
    private Button next;

    private TextView measureName;
    private RatingBar measureRateus;

    private List<String> arrayMeasure;
    private List<Float> arrayRates;

    private String url;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            measureName = (TextView) view.findViewById(R.id.measureName);
            measureRateus = (RatingBar) view.findViewById(R.id.measureRateus);
        }
    }

    public Adapter_Measure(List<String> arrayMeasure, List<Float> arrayRates, RatingBar overallRating) {
        this.arrayMeasure = arrayMeasure;
        this.arrayRates = arrayRates;

        this.overallRating = overallRating;
        this.next = next;
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
                Float avg = sum / arrayRates.size();
                overallRating.setRating(avg);
                //Toast.makeText(context, ""+avg,Toast.LENGTH_SHORT).show();
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
