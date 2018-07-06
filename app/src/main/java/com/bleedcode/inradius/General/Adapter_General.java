package com.bleedcode.inradius.General;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bleedcode.inradius.Main.Function_Image;
import com.github.mikephil.charting.charts.PieChart;
import com.bleedcode.inradius.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 1505560 on 10-Feb-18.
 */

class Adapter_General extends RecyclerView.Adapter<Adapter_General.ViewHolder> {
    Context context;

    private CardView card;
    private TextView title, desc;
    private ImageView squareImage;
    private CircleImageView circleImage;

    private List<String> arrayId, arrayTitle, arrayDesc;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            card = (CardView) view.findViewById(R.id.card);
            title = (TextView)view.findViewById(R.id.title);
            desc = (TextView)view.findViewById(R.id.desc);
            squareImage = (ImageView)view.findViewById(R.id.squareImage);
            circleImage = (CircleImageView) view.findViewById(R.id.circleImage);
        }
    }

    public Adapter_General(List<String> arrayId, List<String> arrayTitle, List<String> arrayDesc) {
        this.arrayId = arrayId;
        this.arrayTitle = arrayTitle;
        this.arrayDesc = arrayDesc;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_general, parent, false);
        context=  view.getContext();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        title.setText(arrayTitle.get(position));
        desc.setText(arrayDesc.get(position));

        Function_Image.getImage(context, squareImage, arrayId.get(position));

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
        return arrayId.size();
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
