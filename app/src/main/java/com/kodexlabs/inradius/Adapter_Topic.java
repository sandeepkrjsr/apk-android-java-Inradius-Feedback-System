package com.kodexlabs.inradius;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;

import java.util.List;

/**
 * Created by 1505560 on 10-Feb-18.
 */

class Adapter_Topic extends RecyclerView.Adapter<Adapter_Topic.ViewHolder> {
    Context context;

    private CardView card;
    private TextView topic, desc;
    private PieChart pieChart;
    private RatingBar rating;

    private List<String> arrayId, arrayTopic, arrayDesc, arrayRating;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            card = (CardView) view.findViewById(R.id.card);
            topic = (TextView)view.findViewById(R.id.topicName);
            desc = (TextView)view.findViewById(R.id.topicDesc);
            rating = (RatingBar)view.findViewById(R.id.topicRating);
            pieChart = (PieChart)view.findViewById(R.id.topicPie);
        }
    }

    public Adapter_Topic(List<String> arrayId, List<String> arrayTopic, List<String> arrayDesc, List<String> arrayRating) {
        this.arrayId = arrayId;
        this.arrayTopic = arrayTopic;
        this.arrayDesc = arrayDesc;
        this.arrayRating = arrayRating;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_topic, parent, false);
        context=  view.getContext();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        topic.setText(arrayTopic.get(position));
        desc.setText(arrayDesc.get(position));
        rating.setRating(Float.parseFloat(arrayRating.get(position)));

        Function_Pie fp = new Function_Pie();
        fp.makePie(pieChart, Float.parseFloat(arrayRating.get(position)));

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Activity_Info.class);
                intent.putExtra("topic_id", arrayId.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayTopic.size();
    }

    public void Info(View view){
        topic.setText("hbhbjj");
    }
}
