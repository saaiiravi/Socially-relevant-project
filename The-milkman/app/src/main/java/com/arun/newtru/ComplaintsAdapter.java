package com.arun.newtru;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.RelativeLayout;
import java.util.List;
import co.dift.ui.SwipeToAction;
import android.graphics.Color;

public class ComplaintsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<complaint> items;

    /** References to the views for each data item **/
    public class ComplaintViewHolder extends SwipeToAction.ViewHolder<complaint> {
        public TextView titleView;
        public TextView authorView;
        public RelativeLayout frontBg;
        //public SimpleDraweeView imageView;

        public ComplaintViewHolder (View v) {
            super(v);
            titleView = (TextView) v.findViewById(R.id.title);
            authorView = (TextView) v.findViewById(R.id.author);
            frontBg = (RelativeLayout) v.findViewById(R.id.itemBg);
            //imageView = (SimpleDraweeView) v.findViewById(R.id.image);
        }
    }

    /** Constructor **/
    public ComplaintsAdapter(List<complaint> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        complaint item = items.get(position);
        ComplaintViewHolder vh = (ComplaintViewHolder) holder;
        vh.titleView.setText(item.getComplaint() + " on " + item.getDistributorname());
        vh.authorView.setText("by " + item.getCustomerName() + " at " + item.getTimeofreport());
        if(item.getIsdone() == 1){
            vh.frontBg.setBackgroundColor(Color.parseColor("#eeeeee"));
        }else{
            vh.frontBg.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        //vh.imageView.setImageURI(Uri.parse(item.getImageUrl()));
        vh.data = item;
    }
}
