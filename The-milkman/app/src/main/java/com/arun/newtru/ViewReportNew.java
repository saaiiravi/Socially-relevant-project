package com.arun.newtru;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;
import co.dift.ui.SwipeToAction;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ViewReportNew extends Activity {

    RecyclerView recyclerView;
    ComplaintsAdapter adapter;
    SwipeToAction swipeToAction;
    FirebaseAuth mAuth;
    DatabaseReference myRef;
    private FirebaseDatabase database;

    List<complaint> complaints = new ArrayList<>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report_new);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        complaints = (List<complaint>)intent.getSerializableExtra("COMPLAINTS");

        adapter = new ComplaintsAdapter(this.complaints);
        recyclerView.setAdapter(adapter);

        swipeToAction = new SwipeToAction(recyclerView, new SwipeToAction.SwipeListener<complaint>() {
            @Override
            public boolean swipeLeft(final complaint itemData) {
                final int pos = markAsDone(itemData);
                displaySnackbar(itemData.getComplaintid() + " marked as done", "Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //addComplaint(pos, itemData);
                        undoMarkAsDone(pos, itemData);
                    }
                });
                return true;
            }

            @Override
            public boolean swipeRight(final complaint itemData) {
                final int pos = markAsDone(itemData);
                displaySnackbar(itemData.getComplaintid() + " marked as done", "Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //addComplaint(pos, itemData);
                        undoMarkAsDone(pos, itemData);
                    }
                });
                return true;
            }

            @Override
            public void onClick(complaint itemData) {
                //displaySnackbar(itemData.getUserid() + " clicked", null, null);
            }

            @Override
            public void onLongClick(complaint itemData) {
                //displaySnackbar(itemData.getUserid() + " long clicked", null, null);
            }
        });

        // use swipeLeft or swipeRight and the elem position to swipe by code

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToAction.swipeRight(0);
            }
        }, 500);
        */
    }

    private int removeComplaint(complaint complaintObj) {
        int pos = complaints.indexOf(complaintObj);
        complaints.remove(complaintObj);
        adapter.notifyItemRemoved(pos);
        return pos;
    }

    private int markAsDone(complaint complaintObj){
        int pos = complaints.indexOf(complaintObj);
        complaints.get(pos).isdone = 1;
        adapter.notifyItemChanged(pos);
        return pos;
    }

    private int undoMarkAsDone(int pos, complaint complaintObj){
        //int pos = complaints.indexOf(complaintObj);
        complaints.get(pos).isdone = 0;
        adapter.notifyItemChanged(pos);
        return pos;
    }

    private void addComplaint(int pos, complaint complaintObj) {
        complaints.add(pos, complaintObj);
        adapter.notifyItemInserted(pos);
    }

    private void displaySnackbar(String text, String actionName, View.OnClickListener action) {
        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .setAction(actionName, action);

        View v = snack.getView();
        v.setBackgroundColor(getResources().getColor(R.color.secondary));
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_action)).setTextColor(Color.BLACK);

        snack.show();
    }
}
