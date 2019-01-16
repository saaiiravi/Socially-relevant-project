package com.arun.newtru;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.TextView;

import com.github.badoualy.datepicker.DatePickerTimeline;
import com.github.badoualy.datepicker.MonthView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecordsFragment extends Fragment {
    Calendar cal;
    DatePickerTimeline timeline;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    usernew value;
    TextView textView;

    public RecordsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_records, container, false);
        cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        timeline = (DatePickerTimeline)v.findViewById(R.id.dateTimeline);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        textView = (TextView) v.findViewById(R.id.recordQuantity);
        mUser = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot shot2: dataSnapshot.getChildren()){
                    if(shot2.getValue(usernew.class).userid != null && shot2.getValue(usernew.class).userid.equals(mUser.getUid())) {
                        value = shot2.getValue(usernew.class);
                        timeline.setFirstVisibleDate(value.msyear, value.msmonth, value.msdate);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef.child("user").addValueEventListener(listener);
        /*
        DatabaseReference myRef2 = database.getReference();
        ValueEventListener listener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot shot2: dataSnapshot.getChildren()){
                    if(shot2.getValue(usernew.class).userid.equals(mUser.getUid())) {
                        value = shot2.getValue(usernew.class);
                        timeline.setFirstVisibleDate(value.msyear, value.msmonth, value.msdate);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myRef2.child("consumption").addValueEventListener(listener2);
        */
        // Inflate the layout for this fragment
        /*timeline.setLastVisibleDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        timeline.setDateLabelAdapter(new MonthView.DateLabelAdapter() {
            @Override
            public CharSequence getLabel(Calendar calendar, int index) {
                return Integer.toString(calendar.get(Calendar.MONTH) + 1) + "/" + (calendar.get(Calendar.YEAR) % 2000);
            }
        });*/
        timeline.setFirstVisibleDate(cal.get(Calendar.YEAR)-1, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        timeline.setLastVisibleDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)-1);
        timeline.setDateLabelAdapter(new MonthView.DateLabelAdapter() {
            @Override
            public CharSequence getLabel(Calendar calendar, int index) {
                return Integer.toString(calendar.get(Calendar.MONTH) +1) + "/" + (calendar.get(Calendar.YEAR) % 2000);
            }
        });

        timeline.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int index) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("consumption").child(mUser.getUid()).child(Integer.toString(year)).child(Integer.toString(month+1)).child(Integer.toString(day)).child("quantity");
                Log.e("founding",ref.toString());
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("founding",dataSnapshot.exists()+"  Has children = "+dataSnapshot.hasChildren()) ;
                        if(dataSnapshot.exists())
                        {
                            textView.setText(dataSnapshot.getValue().toString()+"litres");
                        }
                        else
                        {
                            textView.setText("No Records");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_change_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}