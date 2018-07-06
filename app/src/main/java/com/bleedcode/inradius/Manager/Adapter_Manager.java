package com.bleedcode.inradius.Manager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bleedcode.inradius.Main.Function_Image;
import com.bleedcode.inradius.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 1505560 on 10-Feb-18.
 */

class Adapter_Manager extends RecyclerView.Adapter<Adapter_Manager.ViewHolder> {
    Context context;

    private CardView card;
    private TextView emp_name, emp_pos, emp_dept;
    private CircleImageView emp_image;
    //private Button givefeedback;

    private List<String> arrayId, arrayName, arrayPos, arrayDept;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            card = (CardView) view.findViewById(R.id.card);
            emp_name = (TextView)view.findViewById(R.id.emp_name);
            emp_pos = (TextView)view.findViewById(R.id.emp_pos);
            emp_dept = (TextView) view.findViewById(R.id.emp_dept);
            emp_image = (CircleImageView) view.findViewById(R.id.emp_image);
            //givefeedback = (Button)view.findViewById(R.id.givefeedback);
        }
    }

    public Adapter_Manager(List<String> arrayId, List<String> arrayName, List<String> arrayPos, List<String> arrayDept) {
        this.arrayId = arrayId;
        this.arrayName = arrayName;
        this.arrayPos = arrayPos;
        this.arrayDept = arrayDept;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_manager, parent, false);
        context=  view.getContext();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        emp_name.setText(arrayName.get(position));
        emp_pos.setText(arrayPos.get(position));
        emp_dept.setText(arrayDept.get(position));

        Function_Image.getImage(context, emp_image, arrayId.get(position));

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Dialog_Feedback.class);
                intent.putExtra("topic_id", arrayId.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayName.size();
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
