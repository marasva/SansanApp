package sansanapp.aplicacionesm.usm.cl.sansanapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LHistoryReservationTabFragment extends Fragment {

        private static final String TAG = "Tab1Fragment";

        private Button btnTEST;

        //listView
        private ListView newCampoList;
        ArrayList<String> listItems;
        ArrayAdapter<String> adapter;

        // Database
        //  private DatabaseReference mDatabase;

        //date/time
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;
        String Date;

        TextView dateTimeView;

        // datePicker view
        DatePickerDialog datePickerDialog;
        EditText datePickerText;



        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.campo_history_tab_fragment,container,false);
        /*
        btnTEST = (Button) view.findViewById(R.id.campoButton);

        // Creating the list in the end of the view
        newCampoList = (ListView) view.findViewById(R.id.newCampoList);
        listItems = new ArrayList<String>();
        listItems.add("First Item - added on Activity Create");
        listItems.add("Hallooo");
        listItems.add("GUTEN TAG");
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);
        newCampoList.setAdapter(adapter);

        //datetime
        //  dateTimeView = (TextView) view.findViewById(R.id.dateTimeView);
//        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm",Locale.ROOT);
        //      Date = simpleDateFormat.format(calendar.getTime());
        datePickerText=(EditText) view.findViewById(R.id.datePicker);

        final List<String> values = new ArrayList<String>();
        final StringBuilder result = new StringBuilder();
        // temporary solutions with only using the String Array, because the frickin Firebase list
        // is not working
        final String[] fieldNames={"Football","Basketball","Tennis"};
        String[] answerseconds = { "Unlimited", "1 second", "2 seconds", "3 seconds",
                "4 seconds", " 5 seconds" };

        final String[] gameminutes = { "Unlimited", "1 minute", "2 minutes", "3 minutes",
                "4 minutes", " 5 minutes" };



        // Firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("fields").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    values.add(child.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        // Set all the Spinnerz
        final Spinner spinnerField = (Spinner) view.findViewById(fieldSpinner);
        final Spinner spinnerStartTime = (Spinner) view.findViewById(startTimeSpinner);
        final Spinner spinnerEndTime = (Spinner) view.findViewById(endTimeSpinner);

        ArrayAdapter<String> startTimeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,answerseconds );
        ArrayAdapter<String> endTimeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,gameminutes );
        ArrayAdapter<String> fieldsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,fieldNames);


        startTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fieldsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerStartTime.setAdapter(startTimeAdapter);
        spinnerEndTime.setAdapter(endTimeAdapter);
        spinnerField.setAdapter(fieldsAdapter);

        spinnerStartTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "startTimeSpinner", Toast.LENGTH_LONG).show();
                if (spinnerStartTime.getSelectedItemId() > 2 ) {
                    spinnerEndTime.setSelection(3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerEndTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "endStartSpinner", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "fieldSpinner", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // datePickerText
        datePickerText.setInputType(InputType.TYPE_NULL);
        datePickerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                datePickerText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.append(spinnerStartTime.getSelectedItem().toString()).append(spinnerEndTime.getSelectedItem().toString()).append(spinnerField.getSelectedItem()).append(datePickerText.getText());
                listItems.add(result.toString());
                adapter.notifyDataSetChanged();
                newCampoList.setVisibility(View.VISIBLE);
            }
        });
        */

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




