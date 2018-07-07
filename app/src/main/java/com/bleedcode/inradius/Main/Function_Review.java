package com.bleedcode.inradius.Main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bleedcode.inradius.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 1505560 on 10-Feb-18.
 */

public class Function_Review extends RecyclerView.Adapter<Function_Review.ViewHolder> {
    Context context;

    private RatingBar rated;
    private TextView user, comment;
    private CircleImageView image;

    private List<String> arrayIds, arrayUser, arrayComment, arrayRated;

    public void Function_Review(){}

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            //pieChart = (PieChart) view.findViewById(R.id.normalRatingPie);
            user = (TextView) view.findViewById(R.id.reviewerName);
            comment = (TextView)view.findViewById(R.id.reviewerComment);
            rated = (RatingBar) view.findViewById(R.id.reviewerRating);
            image = (CircleImageView)view.findViewById(R.id.reviewerImage);
        }
    }

    public Function_Review(List<String> arrayIds, List<String> arrayUser, List<String> arrayComment, List<String> arrayRated) {
        this.arrayIds = arrayIds;
        this.arrayUser = arrayUser;
        this.arrayComment = arrayComment;
        this.arrayRated = arrayRated;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_review, parent, false);
        context=  view.getContext();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        user.setText(arrayUser.get(position));
        comment.setText(arrayComment.get(position));
        rated.setRating(Float.parseFloat(arrayRated.get(position)));

        if (arrayRated.get(position).equals("0.0"))
            rated.setVisibility(View.GONE);

        if (!arrayUser.get(position).equals("Anonymous"))
            Function_Image.getImage(context, image, arrayIds.get(position));
        //image.setImageDrawable(context.getResources().getDrawable(R.drawable.img_manager));
    }

    @Override
    public int getItemCount() {
        return arrayUser.size();
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
