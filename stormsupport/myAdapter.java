package com.minlabs.stormsupport;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
    private List<Alert> values;
    private String url;
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtLocation;
        public TextView txtSeverity;
        public View layout;
        private final Context context;
        public ViewHolder(View v) {
            super(v);
            context = v.getContext();
            layout = v;
            txtTitle = (TextView) v.findViewById(R.id.title_line);
            txtSeverity = (TextView) v.findViewById(R.id.severity_line);
            txtLocation = (TextView)v.findViewById(R.id.locations_line);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("address", url);
                    context.startActivity(intent);
                }
            });
        }
    }

    public void add (int position, Alert al) {
        values.add(position, al);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public myAdapter(List<Alert> myDataset) {
        values = myDataset;
    }

    @Override
    public myAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.alert_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Alert al = values.get(position);
        holder.txtTitle.setText(al.getTitle());
        holder.txtLocation.setText("For " + al.getLocations());
        holder.txtSeverity.setText("Severity:  " + al.getSeverity());
        url = al.getUrl();
        if(al.getSeverity().equals("warning")){
            //red
            int red = Color.parseColor("#F7858D");
            holder.txtTitle.setBackgroundColor(red);
            holder.txtLocation.setBackgroundColor(red);
            holder.txtSeverity.setBackgroundColor(red);
        }
        else if(al.getSeverity().equals("watch")){
            //orange
            int orange = Color.parseColor("#F7C185");
            holder.txtTitle.setBackgroundColor(orange);
            holder.txtLocation.setBackgroundColor(orange);
            holder.txtSeverity.setBackgroundColor(orange);
        }
        else{
            //yellow
            int yellow = Color.parseColor("#F7ED85");
            holder.txtTitle.setBackgroundColor(yellow);
            holder.txtLocation.setBackgroundColor(yellow);
            holder.txtSeverity.setBackgroundColor(yellow);
        }
    }
    @Override
    public int getItemCount() {
        return values.size();
    }
}
