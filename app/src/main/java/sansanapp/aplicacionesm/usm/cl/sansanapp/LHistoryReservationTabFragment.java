package sansanapp.aplicacionesm.usm.cl.sansanapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class LHistoryReservationTabFragment extends Fragment {

    private static final String TAG = "Tab1Fragment";
    private Button newReservationHistory;
    private ListView campoHistoryList;
    private ArrayList<String> itemIdList;
    private ArrayList<String> fillList;
    private ArrayList<UserData> userObjects;
    private ShowBibHistoryAdapter listAdapter;
    // Database
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    String userID;
    TextView userTextHistory;
    Button btnLogout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.biblioteca_history_tab_fragment,container,false);

        newReservationHistory = (Button) view.findViewById(R.id.bibReservationHistory);
        campoHistoryList = (ListView) view.findViewById(R.id.bibHistoryList);

        userTextHistory = (TextView) view.findViewById(R.id.bibUserTextEmailHistory);
        btnLogout =(Button) view.findViewById(R.id.bibLogoutButtonHistory);

        itemIdList = new ArrayList<String>();
        userObjects = new ArrayList<UserData>();

        // Firebase authentication
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
        userTextHistory.setText(user.getEmail());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity().getApplicationContext(),MainActivity.class));
                getActivity().finish();
            }
        });

        newReservationHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TabActivityReservationBiblioteca)getActivity()).selectFragment(0);
            }
        });

        mDatabase.child("users").child(userID).child("reservationLibrary").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fillList = new ArrayList<>();
                StringBuilder result = new StringBuilder();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    result.setLength(0);
                    String itemId = ds.getKey();
                    UserData userData = ds.getValue(UserData.class);
                    //   Toast.makeText(getActivity(), "this is the key" + userData.getReservationStartTime(), Toast.LENGTH_LONG).show();
                    //  UserData us = ds.getValue(UserData.class);
                    // make startTime string look nice
                    String startTime = "";
                    String startTimestart = userData.getReservationStartTime().substring(0,2);
                    String startTimeend = userData.getReservationStartTime().substring(2);
                    startTime += startTimestart + ":" + startTimeend;

                    // make endTime string look nice
                    String endTime = "";
                    String endTimestart = userData.getReservationEndTime().substring(0,2);
                    String endTimeend = userData.getReservationEndTime().substring(2);
                    // make endTime appear correctly:
                    int endTimeShow = Integer.parseInt(endTimeend);
                    endTimeend = String.valueOf(endTimeShow + 1);
                    endTime += endTimestart + ":" + endTimeend;



                    // fix reservationType
                    String userDataReservationType = userData.getReservationType();
                    String reservationType = Character.toUpperCase(userDataReservationType.charAt(0)) + userDataReservationType.substring(1);

                    //fix reservationDate
                    String date = "";
                    String dateDay = userData.getReservationDate().substring(0,2);
                    String dateMonth = userData.getReservationDate().substring(2,4);
                    String dateYear = userData.getReservationDate().substring(4);
                    date += dateDay + "/" + dateMonth + "/" + dateYear;

                    // add the result and send it to the ShowCampoHistoryAdapter to show it in list
                    result.append(reservationType).append("\n").append(startTime).append("-").append(endTime).append(" ").append(date);
                    itemIdList.add(itemId);
                    fillList.add(result.toString());
                    userObjects.add(userData);
                }
                listAdapter = new ShowBibHistoryAdapter(fillList,itemIdList,userObjects,getActivity());
                campoHistoryList.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        return view;
    }
}
