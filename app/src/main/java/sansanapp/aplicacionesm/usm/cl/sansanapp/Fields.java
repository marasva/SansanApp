package sansanapp.aplicacionesm.usm.cl.sansanapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Fields {

    public String id;
    public String name;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Fields() {
    }

    public Fields(String id,String name) {
        this.id = id;
        this.name = name;
    }
}
