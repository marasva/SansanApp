package sansanapp.aplicacionesm.usm.cl.sansanapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowBibHistoryAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> fillList = new ArrayList<String>();
    private ArrayList<String> listOfIds = new ArrayList<String>();
    private ArrayList<UserData> userObjects = new ArrayList<UserData>();
    private Context context;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    public ShowBibHistoryAdapter(ArrayList<String> fillList, ArrayList<String> listOfIds, ArrayList<UserData> userObjects, Context context) {
        this.fillList = fillList;
        this.context = context;
        this.listOfIds = listOfIds;
        this.userObjects = userObjects;
    }

    @Override
    public int getCount() {
        return fillList.size();
    }

    @Override
    public Object getItem(int pos) {
        return fillList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.bib_history_list_items, null);
        }

        if (fillList.isEmpty()) {
            System.out.println("Fillist is empty");
        }

        //Handle TextView and display string from your list
        TextView campoHistoryListItem =(TextView)view.findViewById(R.id.bibHistoryListItem);
        campoHistoryListItem.setText(fillList.get(position));

        Button deleteCampoHistoryListItem= (Button)view.findViewById(R.id.deleteBibHistoryListItem);
        deleteCampoHistoryListItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String reservationType = userObjects.get(position).getReservationType();
                final String reservationDate = userObjects.get(position).getReservationDate();
                String reservationStartTime = userObjects.get(position).getReservationStartTime();
                String reservationEndTime = userObjects.get(position).getReservationEndTime();

                // delete reservation from user and from the list in Firebase
                mDatabase.child("library").child(reservationType).child(reservationDate).orderByKey().startAt(reservationStartTime).endAt(reservationEndTime).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            mDatabase.child("library").child(reservationType).child(reservationDate).child(child.getKey()).child("isBooked").setValue("false");
                            mDatabase.child("library").child(reservationType).child(reservationDate).child(child.getKey()).child("isBookedBy").setValue("null");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                mDatabase.child("users").child(firebaseAuth.getUid()).child("reservationLibrary").child(listOfIds.get(position)).removeValue();

                userObjects.remove(position);
                listOfIds.remove(position);
                if (listOfIds.isEmpty()) {
                    System.out.println("The list of Ids is empty");
                }
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
