package com.bleedcode.inradius.Discussion;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bleedcode.inradius.Main.Activity_Dashboard;
import com.bleedcode.inradius.Main.Activity_Login;
import com.bleedcode.inradius.Main.Function_URL;
import com.github.mikephil.charting.charts.PieChart;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static android.graphics.Color.YELLOW;

/**
 * Created by 1505560 on 10-Feb-18.
 */

class Adapter_Discussion extends RecyclerView.Adapter<Adapter_Discussion.ViewHolder> {
    Context context;

    private CardView card;
    private TextView topic, desc;
    private ImageView bell_notify, bell_ring;

    private List<String> arrayId, arrayTopic, arrayDesc;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            card = (CardView) view.findViewById(R.id.card);
            topic = (TextView)view.findViewById(R.id.topicName);
            desc = (TextView)view.findViewById(R.id.topicDesc);
            bell_notify = (ImageView) view.findViewById(R.id.bell_notify);
            bell_ring = (ImageView) view.findViewById(R.id.bell_ring);
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
        desc.setText("Asked by " + arrayDesc.get(position));

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Activity_Discussion_Open.class);
                intent.putExtra("topic_id", arrayId.get(position));
                context.startActivity(intent);
            }
        });

        bell_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putData(arrayId.get(position));

                //bell_notify.setVisibility(View.GONE);
                //bell_ring.setVisibility(View.VISIBLE);
                //bell_notify.setImageDrawable(context.getResources().getDrawable(R.drawable.img_user));
            }
        });
    }

    private void putData(String topic_id) {
        String sub_id = topic_id + Activity_Login.loggedin;

        Function_URL f_url = new Function_URL();
        String url = f_url.DATA_SUBSCRIPTIONS + f_url.ACTION_CREATE;
        sendData(url, sub_id, topic_id);
    }

    private void sendData(final String url, final String sub_id, final String topic_id) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> data = new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("sub_id", sub_id));
                data.add(new BasicNameValuePair("emp_id", Activity_Login.loggedin));
                data.add(new BasicNameValuePair("topic_id", topic_id));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.setEntity(new UrlEncodedFormEntity(data));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                }
                return "Data Submit Successfully";
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(url, sub_id, topic_id);
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