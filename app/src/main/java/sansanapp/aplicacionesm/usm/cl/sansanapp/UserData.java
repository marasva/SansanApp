package sansanapp.aplicacionesm.usm.cl.sansanapp;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserData {
    private String reservationStartTime;
    private String reservationEndTime;
    private String reservationDate;
    private String reservationType;


    public UserData(String reservationStartTime,String reservationEndTime,String reservationDate,String reservationType){
        this.reservationStartTime = reservationStartTime;
        this.reservationEndTime = reservationEndTime;
        this.reservationDate = reservationDate;
        this.reservationType = reservationType;
    }
    public UserData() {
        super();

    }

    public String getReservationEndTime() {
        return reservationEndTime;
    }

    public void setReservationEndTime(String reservationEndTime) {
        this.reservationEndTime = reservationEndTime;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationType() {
        return reservationType;
    }

    public void setReservationType(String reservationType) {
        this.reservationType = reservationType;
    }

    public String getReservationStartTime() {
        return reservationStartTime;
    }

    public void setReservationStartTime(String reservationStartTime) {
        this.reservationStartTime = reservationStartTime;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("reservationStartTime", reservationStartTime);
        result.put("reservationEndTime", reservationEndTime);
        result.put("reservationDate", reservationDate);
        result.put("reservationType", reservationType);

        return result;
    }

}

