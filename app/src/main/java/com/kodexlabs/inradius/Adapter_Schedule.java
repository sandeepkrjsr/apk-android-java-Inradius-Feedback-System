package com.kodexlabs.inradius;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 1505560 on 10-Feb-18.
 */

class Adapter_Schedule extends RecyclerView.Adapter<Adapter_Schedule.ViewHolder> {
    String track;
    Context context;

    private TextView time, title, venue, description, speaker, profile;
    private LinearLayout header;

    private List<String> array_time, array_title, array_venue, array_description, array_speaker, array_profile;



    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            header = (LinearLayout)view.findViewById(R.id.header);
            time = (TextView)view.findViewById(R.id.time);
            title = (TextView)view.findViewById(R.id.title);
            venue = (TextView)view.findViewById(R.id.venue);
            description = (TextView)view.findViewById(R.id.description);
            speaker = (TextView)view.findViewById(R.id.speaker);
            profile = (TextView)view.findViewById(R.id.profile);

        }
    }

    public Adapter_Schedule(List<String> array_time, List<String> array_title, List<String> array_venue, List<String> array_description, List<String> array_speaker, List<String> array_profile) {
        this.array_time = array_time;
        this.array_title = array_title;
        this.array_venue = array_venue;
        this.array_description = array_description;
        this.array_speaker = array_speaker;
        this.array_profile = array_profile;
        this.track=track;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cards, parent, false);
        context=  view.getContext();
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        time.setText(array_time.get(position));
        title.setText(array_title.get(position));
        venue.setText(array_venue.get(position));
        description.setText(array_description.get(position));

        if(array_speaker.get(position)!=null) {
            speaker.setVisibility(View.VISIBLE);
            speaker.setText(array_speaker.get(position));
        }

        if(array_profile.get(position)!=null) {
            profile.setVisibility(View.VISIBLE);
            profile.setText(array_profile.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return array_title.size();
    }
}
