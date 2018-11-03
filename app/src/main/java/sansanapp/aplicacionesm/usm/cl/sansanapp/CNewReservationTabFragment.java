package sansanapp.aplicacionesm.usm.cl.sansanapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static sansanapp.aplicacionesm.usm.cl.sansanapp.R.id.endTimeSpinner;
import static sansanapp.aplicacionesm.usm.cl.sansanapp.R.id.fieldSpinner;
import static sansanapp.aplicacionesm.usm.cl.sansanapp.R.id.startTimeSpinner;

public class CNewReservationTabFragment extends Fragment {
    private static final String TAG = "Tab1Fragment";


    private Button showButton;
    private Button bookButton;

    //listView
    private ListView newCampoList;
    private ListView newCampoListwoRB;
    ArrayList<String> listItems;
    ArrayAdapter<String> listAdapter;
    ArrayAdapter<String> listAdapterwoRB;


    // Database
    private DatabaseReference mDatabase;

    //date/time
    TimePickerDialog startTimePicker;
    TimePickerDialog endTimePicker;
    EditText startTimeText;
    EditText endTimeText;
    EditText endTimeManip;

    // datePicker view
    DatePickerDialog datePickerDialog;
    EditText datePickerText;



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
                                datePickerText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });


        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clearing the views and stuff for when button is clicked several times
                newCampoListwoRB.setVisibility(View.GONE);
                newCampoList.setVisibility(View.GONE);
                result.setLength(0);
                listItems.clear();
                result.append(startTimeText.getText()).append("-").append(endTimeText.getText()).append(" ").append(spinnerField.getSelectedItem()).append(" ").append(datePickerText.getText());

                final String starttoFirebase = startTimeText.getText().toString().replace(":","");
                final String endtoFirebase = endTimeManip.getText().toString().replace(":","");
                final String datetoFirebase = datePickerText.getText().toString().replace("/","");

                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("football").child(datetoFirebase).orderByKey().startAt(starttoFirebase).endAt(endtoFirebase).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean flagStop = false;
                        List<String> getValues = new ArrayList<String>();

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
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                mDatabase.child("football").child(datetoFirebase).child(child.getKey()).child("isBooked").setValue("true");
                            }
                            listItems.add(result.toString());
                            System.out.println("Updated");
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
                Toast.makeText(getActivity(),"BOOKED",Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
