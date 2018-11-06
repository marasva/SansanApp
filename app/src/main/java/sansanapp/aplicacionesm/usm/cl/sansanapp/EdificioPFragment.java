package sansanapp.aplicacionesm.usm.cl.sansanapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EdificioPFragment extends Fragment {
    private static final String TAB = "Tab1Fragment";

    private Button btnTEST;
    private static final String TAG = "Tab4Fragment";

    private DatabaseReference mDatabase2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edificiop_tab_fragment,container,false);
        final List<String> values = new ArrayList<String>();



        // Firebase database
        mDatabase2 = FirebaseDatabase.getInstance().getReference();
        System.out.println(mDatabase2);
        mDatabase2.child("fields").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    values.add(child.getValue(String.class));
                    System.out.println("halo1" + child.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return view;
    }
}