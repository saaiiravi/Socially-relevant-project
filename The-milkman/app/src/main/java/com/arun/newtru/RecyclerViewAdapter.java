package com.arun.newtru;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> cusname  = new ArrayList<>();
    private ArrayList<String> cusaddr = new ArrayList<>();
    private ArrayList<String> disname = new ArrayList<>();
    private ArrayList<String> complaint = new ArrayList<>();
    private ArrayList<String> timeofreport = new ArrayList<>();

    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> cusname, ArrayList<String> cusaddr, ArrayList<String> disname, ArrayList<String> complaint, ArrayList<String> timeofreport, Context mContext) {
        this.cusname = cusname;
        this.cusaddr = cusaddr;
        this.disname = disname;
        this.complaint = complaint;
        this.timeofreport = timeofreport;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.cusname.setText(cusname.get(position));
        holder.cusaddr.setText(cusaddr.get(position));
        holder.disname.setText(disname.get(position));
        holder.complaint.setText(complaint.get(position));
        holder.timeofreport.setText(timeofreport.get(position));

    }

    @Override
    public int getItemCount() {
        return cusname.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView cusname;
        TextView cusaddr;
        TextView disname;
        TextView complaint;
        TextView timeofreport;

        public ViewHolder(View itemView) {
            super(itemView);
            cusname = itemView.findViewById(R.id.Customer_name);
            cusaddr = itemView.findViewById(R.id.Customer_addr);
            disname = itemView.findViewById(R.id.Distributor_Name);
            complaint = itemView.findViewById(R.id.complaint);
            timeofreport = itemView.findViewById(R.id.timeofreport);
        }
    }
}
