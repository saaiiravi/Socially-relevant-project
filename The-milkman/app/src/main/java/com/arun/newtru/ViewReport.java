package com.arun.newtru;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewReport extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayout parentLinearLayout;

    FirebaseAuth mAuth;
    DatabaseReference threadRef,temp;
    complaint Tinfo;

    private ArrayList<String> cusname  = new ArrayList<>();
    private ArrayList<String> cusaddr = new ArrayList<>();
    private ArrayList<String> disname = new ArrayList<>();
    private ArrayList<String> complaint = new ArrayList<>();
    private ArrayList<String> timeofreport = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        mAuth = FirebaseAuth.getInstance();
        threadRef = FirebaseDatabase.getInstance().getReference();
        temp =  FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        parentLinearLayout = (LinearLayout) findViewById(R.id.parent);

        threadRef.child("reports").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                insert(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void insert(DataSnapshot dataSnapshot) {
        cusname.clear();
        cusaddr.clear();
        disname.clear();
        complaint.clear();
        timeofreport.clear();

        for(DataSnapshot ds : dataSnapshot.getChildren()){
            Tinfo = new complaint();
            Tinfo.setDistributorname(ds.getValue(complaint.class).getDistributorname());
            Tinfo.setUserid(ds.getValue(complaint.class).getUserid());
            Tinfo.setComplaint(ds.getValue(complaint.class).getComplaint());
            Tinfo.setCustomerName(ds.getValue(complaint.class).getCustomerName());
            Tinfo.setCustomerAddress(ds.getValue(complaint.class).getCustomerAddress());
            Tinfo.setTimeofreport(ds.getValue(complaint.class).getTimeofreport());
            /*
            temp.child("user").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        if(ds.getValue(usernew.class).userid.equals(Tinfo.getUserid())) {
                            usernew uInfo = new usernew();
                            uInfo.setName(ds.getValue(usernew.class).getName()); //set the name
                            uInfo.setAddress(ds.getValue(usernew.class).getAddress());
                            cusname.add("Customer_Name : "+uInfo.getName());
                            cusaddr.add("Customer_Adrr : "+uInfo.getAddress());
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            */
            cusname.add("Customer Name : "+Tinfo.getCustomerName());
            cusaddr.add("Customer Address : "+Tinfo.getCustomerAddress());
            disname.add("Distributor Name : "+Tinfo.getDistributorname());
            complaint.add("Complaint : "+Tinfo.getComplaint());
            timeofreport.add("Time : "+Tinfo.getTimeofreport());
        }
        initrecylerview();
    }

    public void initrecylerview() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(cusname,cusaddr,disname,complaint, timeofreport,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}