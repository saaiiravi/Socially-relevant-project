package com.arun.newtru;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Report extends AppCompatActivity {

    Spinner spinnername,spinnercomplaint;
    Button submit;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference databaseReference,myRef;

    String userID;
    String username;
    String address;
    final String[] name = new String[1];
    final String[] complaint = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        submit = (Button)findViewById(R.id.button);
        spinnername = (Spinner)findViewById(R.id.spinner_Name);
        spinnercomplaint = (Spinner)findViewById(R.id.spinner_Complaint);
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        myRef.child("distributor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        myRef.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.getValue(usernew.class).userid.equals(userID)) {
                        usernew uInfo = new usernew();
                        uInfo.setName(ds.getValue(usernew.class).getName()); //set the name
                        uInfo.setAddress(ds.getValue(usernew.class).getAddress());
                        username = uInfo.getName();
                        address=uInfo.getAddress();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ArrayList<String> Complaintlist = new ArrayList<>();

        Complaintlist.add("Not Delivered on time");
        Complaintlist.add("Quality was not good");
        Complaintlist.add("Quantity was not as Expected");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,Complaintlist);
        spinnercomplaint.setAdapter(adapter);

        spinnercomplaint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                complaint[0] = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dn = name[0];
                String comp = complaint[0];
                Long tsLong = System.currentTimeMillis()/1000;
                String timestamp = tsLong.toString();

                //complaint newcomplaint = new complaint(userID,dn,comp, username, timestamp, address);

                databaseReference = FirebaseDatabase.getInstance().getReference("reports");

                DatabaseReference newcomplaintref = databaseReference.push();

                newcomplaintref.setValue(new complaint(userID,dn,comp, username, timestamp, address));

                String complaintkey = newcomplaintref.getKey();

                databaseReference.child(complaintkey).child("complaintid").setValue(complaintkey);

                startActivity(new Intent(getApplicationContext(),Complaint_Registered.class));
            }
        });
    }




    private void showData(DataSnapshot dataSnapshot) {
        ArrayList<String> list = new ArrayList<>();

        for(DataSnapshot ds : dataSnapshot.getChildren()) {

            distid dist = new distid();
            dist.setId(ds.getValue(distid.class).getId());
            dist.setName(ds.getValue(distid.class).getName());
            dist.setPno(ds.getValue(distid.class).getPno());

            list.add(dist.getName());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);

        spinnername.setAdapter(adapter);

        spinnername.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                name[0] = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }
}