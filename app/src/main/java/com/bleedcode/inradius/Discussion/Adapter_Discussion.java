package com.bleedcode.inradius.Discussion;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.bleedcode.inradius.R;

import java.util.List;

/**
 * Created by 1505560 on 10-Feb-18.
 */

class Adapter_Discussion extends RecyclerView.Adapter<Adapter_Discussion.ViewHolder> {
    Context context;

    private CardView card;
    private TextView topic, desc;
    private PieChart pieChart;

    private List<String> arrayId, arrayTopic, arrayDesc;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            card = (CardView) view.findViewById(R.id.card);
            topic = (TextView)view.findViewById(R.id.topicName);
            desc = (TextView)view.findViewById(R.id.topicDesc);
            //rating = (TextView) view.findViewById(R.id.topicRating);
        }
    }

    public Adapter_Discussion(List<String> arrayId, List<String> arrayTopic, List<String> arrayDesc) {
        this.arrayId = arrayId;
        this.arrayTopic = arrayTopic;
        this.arrayDesc = arrayDesc;
        //this.arrayRating = arrayRating;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_discussion, parent, false);
        context=  view.getContext();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        topic.setText(arrayTopic.get(position));
        desc.setText(arrayDesc.get(position));

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Activity_Discussion_Open.class);
                intent.putExtra("topic_id", arrayId.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayTopic.size();
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
