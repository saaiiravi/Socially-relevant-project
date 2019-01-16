package com.arun.newtru;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

//update account settings fragment under user dashboard
public class AccountFragment extends Fragment {
    FirebaseDatabase database;
    AwesomeValidation validator = new AwesomeValidation(BASIC);
    FirebaseAuth auth;
    FirebaseUser mUser;
    usernew value;
    TextView title;
    EditText nameEditText, firstAddressEditText, secondAddressEditText, thirdAddressEditText, nppp2;
    String name;
    double lat, lang;
    String[] address;
    Double dtdt, tdtdt;
    Button btn, submitButton;
    ProgressDialog progressDialog;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        nameEditText = (EditText) view.findViewById(R.id.nameUpdateText);
        title = (TextView)view.findViewById(R.id.editAccountTextView);
        firstAddressEditText = (EditText) view.findViewById(R.id.firstAddressUpdateText);
        secondAddressEditText = (EditText) view.findViewById(R.id.secondAddressUpdateText);
        thirdAddressEditText = (EditText) view.findViewById(R.id.thirdAddressUpdateText);
        nppp2 = (EditText) view.findViewById(R.id.nppp2);
        submitButton = (Button) view.findViewById(R.id.submitUpdates);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        mUser = auth.getCurrentUser();
        DatabaseReference myref = database.getReference();
        progressDialog = ProgressDialog.show(getActivity(), "Please wait...", "Signing you out...", true);
        myref.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for(DataSnapshot shot: dataSnapshot.getChildren()){
                    if(mUser.getUid().equals(shot.getValue(usernew.class).userid)){
                        value = shot.getValue(usernew.class);
                        break;
                    }
                }
                nameEditText.setText(value.name);
                dtdt = (value.defaultRequirement * 2) - 1;
                tdtdt = dtdt;
                title.setText("Edit account details");
                address = value.address.split(", ");
                firstAddressEditText.setText(address[0]);
                secondAddressEditText.setText(address[1]);
                thirdAddressEditText.setText(address[2]);
                nppp2.setText(Double.toString(value.defaultRequirement));
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        validator.addValidation(nameEditText, RegexTemplate.NOT_EMPTY, "Cannot be empty");
                        validator.addValidation(firstAddressEditText, RegexTemplate.NOT_EMPTY, "Cannot be empty");
                        validator.addValidation(secondAddressEditText, RegexTemplate.NOT_EMPTY, "Cannot be empty");
                        validator.addValidation(thirdAddressEditText, RegexTemplate.NOT_EMPTY, "Cannot be empty");
                        validator.addValidation(nppp2, RegexTemplate.NOT_EMPTY, "Cannot be empty");
                        String n = nameEditText.getText().toString();
                        String a = firstAddressEditText.getText().toString()+", "+ secondAddressEditText.getText().toString()+", "+ thirdAddressEditText.getText().toString();
                        String temp = nppp2.getText().toString();
                        Double newRequirement = Double.parseDouble(temp);
                        newRequirement = Math.round(newRequirement * 2)/2.0;
                        if(validator.validate()) {
                            DatabaseReference newref = database.getReference();
                            newref.child("user").child(mUser.getUid()).child("name").setValue(n);
                            newref.child("user").child(mUser.getUid()).child("address").setValue(a);
                            newref.child("user").child(mUser.getUid()).child("defaultRequirement").setValue(newRequirement);
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(AccountFragment.this);
                            ft.attach(AccountFragment.this);
                            ft.commit();
                            Toast.makeText(getActivity(), "Changes saved!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "Oops! There are problems.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_change_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}

