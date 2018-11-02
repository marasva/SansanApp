package sansanapp.aplicacionesm.usm.cl.sansanapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

    private Button btnTEST;

    //listView
    private ListView newCampoList;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;

    // Database
    private DatabaseReference mDatabase;

    //date/time
    TimePickerDialog startTimePicker;
    TimePickerDialog endTimePicker;
    EditText startTimeText;
    EditText endTimeText;

    // datePicker view
    DatePickerDialog datePickerDialog;
    EditText datePickerText;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.campo_new_tab_fragment,container,false);
        btnTEST = (Button) view.findViewById(R.id.campoButton);

        // Creating the list in the end of the view
        newCampoList = (ListView) view.findViewById(R.id.newCampoList);
        listItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);
        newCampoList.setAdapter(adapter);

        //datetime
        //  dateTimeView = (TextView) view.findViewById(R.id.dateTimeView);
//        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm",Locale.ROOT);
        //      Date = simpleDateFormat.format(calendar.getTime());
        datePickerText =(EditText) view.findViewById(R.id.datePicker);

        // starTime
        startTimeText =(EditText) view.findViewById(R.id.startTimeText);
        endTimeText = (EditText) view.findViewById(R.id.endTimeText);

        final List<String> values = new ArrayList<String>();
        final StringBuilder result = new StringBuilder();
        // temporary solutions with only using the String Array, because the frickin Firebase list
        // is not working
        final String[] fieldNames={"Football","Basketball","Tennis"};

        /*
        // Firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("fields").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    values.add(child.getValue(String.class));
                    System.out.print("halo" + child.getValue(String.class));

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        for (String value : values) {
            System.out.println(value);
        }

*/


        // Set all the Spinnerz
        final Spinner spinnerField = (Spinner) view.findViewById(fieldSpinner);
        ArrayAdapter<String> fieldsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,fieldNames);
        fieldsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerField.setAdapter(fieldsAdapter);
        spinnerField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "fieldSpinner", Toast.LENGTH_LONG).show();
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
                                // made hack to get two zeros in time
                                if (String.valueOf(sMinute).length() == 1){
                                    endTimeText.setText(sHour + ":" + "00");
                                }
                                else {
                                    endTimeText.setText(sHour + ":" + sMinute);
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

        btnTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setLength(0);
                listItems.clear();
                result.append(startTimeText.getText()).append(endTimeText.getText()).append(spinnerField.getSelectedItem()).append(datePickerText.getText());

                final String starttoFirebase = startTimeText.getText().toString().replace(":","");
                final String endtoFirebase = endTimeText.getText().toString().replace(":","");
                final String datetoFirebase = datePickerText.getText().toString().replace("/","");
                System.out.println("startFirebase" + starttoFirebase);



                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("football").addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        /*
                        for (DataSnapshot child : dataSnapshot.child("7112018").getChildren()) {
                            System.out.println("4db2" + child.child("isBooked").getValue(String.class));
                            if (child.child("isBooked").getValue(String.class).equalsIgnoreCase("true")){
                                System.out.println("true" + child.getKey());
                            }
                        }
                        */

                        boolean flagStart = false;
                        boolean flagEnd = false;
                        List<String> getValues = new ArrayList<String>();
                        for (DataSnapshot child : dataSnapshot.child(datetoFirebase).getChildren()) {
                            if (child.getKey().equalsIgnoreCase(starttoFirebase) && child.child("isBooked").getValue(String.class).equalsIgnoreCase("false")){
                                System.out.println("funStart" + child.getKey());
                                flagStart = true;
                            }
                            if (child.getKey().equalsIgnoreCase(endtoFirebase) && child.child("isBooked").getValue(String.class).equalsIgnoreCase("false")){
                                System.out.println("funEnd" + child.getKey());
                                flagEnd = true;
                            }
                        }
                        if (!flagStart || !flagEnd) {
                            listItems.add("There is no bookings available");
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            mDatabase.child("football").child(datetoFirebase).child(starttoFirebase).child("isBooked").setValue("true");
                            mDatabase.child("football").child(datetoFirebase).child(endtoFirebase).child("isBooked").setValue("true");
                            listItems.add(result.toString());
                            System.out.println("Updated");
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


                adapter.notifyDataSetChanged();
                newCampoList.setVisibility(View.VISIBLE);




            }
        });



        return view;
    }
}

 /*
           // can add this under onDataChange
                //working for Map entries
                for (Map.Entry<String,String> entry : map.entrySet())
                    System.out.println("Key = " + entry.getKey() +
                            ", Value = " + entry.getValue());

                final List<String> propertyAddressList = new ArrayList<String>();
                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator );
                System.out.println(map);
                 */
