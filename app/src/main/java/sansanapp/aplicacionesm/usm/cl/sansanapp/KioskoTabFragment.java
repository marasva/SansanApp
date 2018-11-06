package sansanapp.aplicacionesm.usm.cl.sansanapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class KioskoTabFragment extends Fragment {
    private static final String TAG = "Tab4Fragment";
    private Button btnTEST;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edificiop_tab_fragment,container,false);
        return view;
    }
}

    /*

    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kiosko_tab_fragment,container,false);
        final ArrayList<String> values = new ArrayList<String>();
        final String[] bankNames={"BOI","SBI","HDFC","PNB","OBC"};

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("fields").orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    values.add((child.getValue()).toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Spinner spinnerProperty = (Spinner) view.findViewById(R.id.kioskoFieldSpinner);
        ArrayAdapter addressAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, bankNames);
        addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProperty.setAdapter(addressAdapter);
    //    spinnerProperty.setSelection(0,true); //set the default value
        spinnerProperty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getActivity(), "a", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }
}
*/

