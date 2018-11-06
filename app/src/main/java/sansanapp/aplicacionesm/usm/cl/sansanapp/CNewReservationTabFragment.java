package sansanapp.aplicacionesm.usm.cl.sansanapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static sansanapp.aplicacionesm.usm.cl.sansanapp.R.id.fieldSpinner;

public class CNewReservationTabFragment extends Fragment implements Serializable {
    private static final String TAG = "Tab1Fragment";

    private Button showButton;
    private Button bookButton;

    //listView
    private ListView newCampoList;
    private ListView newCampoListwoRB;
    ArrayList<String> listItems;
    ArrayAdapter<String> listAdapter;
    ArrayAdapter<String> listAdapterwoRB;
    ArrayList<String> addItemstoFirebase;

    // Database
    private DatabaseReference mDatabase;

    //date/time
    TimePickerDialog startTimePicker;
    TimePickerDialog endTimePicker;
    EditText startTimeText;
    EditText endTimeText;
    EditText endTimeManip;
    EditText dateManip;

    // datePicker view
    DatePickerDialog datePickerDialog;
    EditText datePickerText;

    TextView userText;
    Button btnLogout;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String userID;
    private String fieldSelected;
    private String starttoFirebase;
    private String endtoFirebase;
    private String datetoFirebase;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.campo_new_tab_fragment,container,false);
        showButton = (Button) view.findViewById(R.id.campoButton);
        bookButton = (Button) view.findViewById(R.id.bookButton);

        // Creating the list in the end of the view
        newCampoList = (ListView) view.findViewById(R.id.newCampoList);
        newCampoListwoRB = (ListView) view.findViewById(R.id.newCampoListwoRB);
        listItems = new ArrayList<String>();
        addItemstoFirebase = new ArrayList<String>();
        listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, listItems);
        listAdapterwoRB = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);
        newCampoList.setAdapter(listAdapter);
        newCampoListwoRB.setAdapter(listAdapterwoRB);

        //datetime
        datePickerText =(EditText) view.findViewById(R.id.datePicker);

        // starTime
        startTimeText =(EditText) view.findViewById(R.id.startTimeText);
        endTimeText = (EditText) view.findViewById(R.id.endTimeText);
        endTimeManip = (EditText) view.findViewById(R.id.endTimeManip);
        dateManip = (EditText) view.findViewById(R.id.dateManip);


        userText = (TextView) view.findViewById(R.id.userText);
        btnLogout =(Button) view.findViewById(R.id.logoutButton);

        // Firebase authentication
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        };
        userText.setText(user.getEmail());


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity().getApplicationContext(),MainActivity.class));
                getActivity().finish();
            }
        });


        // StringBuilder of the results from the different editTexts
        final StringBuilder result = new StringBuilder();
        // temporary solutions with only using the String Array, because the frickin Firebase list
        // is not working
        final String[] fieldNames={"Football","Basketball","Tennis"};


        // Set all the Spinnerz
        final Spinner spinnerField = (Spinner) view.findViewById(fieldSpinner);
        ArrayAdapter<String> fieldsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,fieldNames);
        fieldsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerField.setAdapter(fieldsAdapter);
        spinnerField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fieldSelected = spinnerField.getSelectedItem().toString().toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // startTimePicker
        startTimeText.setInputType(InputType.TYPE_NULL);
        startTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting fixed value, fix this later
                /*
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                */
                int hour = 11;
                int minutes = 00;
                // time picker dialog
                startTimePicker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                // made hack to get two zeros in time
                                if (String.valueOf(sMinute).length() == 1) {
                                    startTimeText.setText(sHour + ":" + "00");
                                } else {
                                    startTimeText.setText(sHour + ":" + sMinute);
                                }
                            }
                        }, hour, minutes, true);
                startTimePicker.show();
            }
        });

        // endTimePicker
        endTimeText.setInputType(InputType.TYPE_NULL);
        endTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting fixed value, fix this later
                /*
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                */
                int hour = 11;
                int minutes = 30;
                // time picker dialog
                endTimePicker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                // made temporary hack to get two zeros in time
                                // probably have to do something to fix the number from 00-09 :SSS
                                if (String.valueOf(sMinute).length() == 1){
                                    endTimeText.setText(sHour + ":" + "00");
                                    endTimeManip.setText((sHour - 1) + ":" + "59");
                                }
                                else {
                                    endTimeText.setText(sHour + ":" + sMinute);
                                    endTimeManip.setText(sHour + ":" + (sMinute - 1));
                                }
                            }
                        }, hour, minutes, true);
                endTimePicker.show();
            }
        });

        // datePickerText
        datePickerText.setInputType(InputType.TYPE_NULL);
        datePickerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set the day to fixed date, fix this later
                /*
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                */
                int day = 7;
                int month = 10;
                int year = 2018;
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // for making it easier to handle in HistoryReservationTabFragment
                                if ((String.valueOf(dayOfMonth).length() == 1) && (String.valueOf(monthOfYear).length() == 1) ) {
                                    dateManip.setText("0" +(dayOfMonth) + ("0" + (monthOfYear + 1)) + "/" + year);
                                }
                                else if ((String.valueOf(dayOfMonth).length() == 1) && (String.valueOf(monthOfYear).length() == 2) ) {
                                    dateManip.setText("0" +(dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                                }
                                else if ((String.valueOf(dayOfMonth).length() == 2) && (String.valueOf(monthOfYear).length() == 1) ) {
                                    dateManip.setText( dayOfMonth + "/" + ("0" + (monthOfYear + 1)) + "/" + year);
                                }
                                else {
                                    dateManip.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                                datePickerText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                // set the minimum date to todays date
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        starttoFirebase = startTimeText.getText().toString().replace(":","");
        endtoFirebase = endTimeManip.getText().toString().replace(":","");
        datetoFirebase = dateManip.getText().toString().replace("/","");



        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clearing the views and stuff for when button is clicked several times
                newCampoListwoRB.setVisibility(View.GONE);
                newCampoList.setVisibility(View.GONE);
                result.setLength(0);
                listItems.clear();
                result.append(startTimeText.getText()).append("-").append(endTimeText.getText()).append(" ").append(spinnerField.getSelectedItem()).append(" ").append(datePickerText.getText());


                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("fields").child(fieldSelected).child(datetoFirebase).orderByKey().startAt(starttoFirebase).endAt(endtoFirebase).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean flagStop = false;

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            if (child.child("isBooked").getValue(String.class).equalsIgnoreCase("true")){
                                flagStop = true;
                            }
                            if (flagStop){
                                listItems.add("There is no bookings available for this date and time period");
                                listAdapterwoRB.notifyDataSetChanged();
                                newCampoListwoRB.setVisibility(View.VISIBLE);
                                break;
                            }
                        }
                        if (!flagStop) {
                            listItems.add(result.toString());
                            System.out.println("Added to results");
                            listAdapter.notifyDataSetChanged();
                            newCampoList.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


            }
        });

        newCampoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bookButton.setVisibility(View.VISIBLE);
            }
        });


        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String starttoFirebase = startTimeText.getText().toString().replace(":", "");
                final String endtoFirebase = endTimeManip.getText().toString().replace(":", "");
                final String datetoFirebase = dateManip.getText().toString().replace("/", "");
                mDatabase.child("fields").child(fieldSelected).child(datetoFirebase).orderByKey().startAt(starttoFirebase).endAt(endtoFirebase).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            mDatabase.child("fields").child(fieldSelected).child(datetoFirebase).child(child.getKey()).child("isBooked").setValue("true");
                            mDatabase.child("fields").child(fieldSelected).child(datetoFirebase).child(child.getKey()).child("isBookedBy").setValue(firebaseAuth.getUid());
                        }
                        Toast.makeText(getActivity(), "Successfully booked", Toast.LENGTH_LONG).show();
                        ((TabActivityReservationCampo)getActivity()).selectFragment(1);
                        newCampoListwoRB.setVisibility(View.GONE);
                        newCampoList.setVisibility(View.GONE);

                        //add to local Database
                        System.out.println("datetofirebase" + datetoFirebase);
                        UserData userData = new UserData(starttoFirebase,endtoFirebase,datetoFirebase,fieldSelected);
                        DatabaseReference newChildRef = mDatabase.child("users").child(userID).child("reservationField").push();
                        newChildRef.setValue(userData);
                        addItemstoFirebase.add(newChildRef.getKey());

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(authStateListener!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

}
