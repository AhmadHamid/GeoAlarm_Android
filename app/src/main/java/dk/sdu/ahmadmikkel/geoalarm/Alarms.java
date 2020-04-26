package dk.sdu.ahmadmikkel.geoalarm;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Alarms {
    private static Alarms instance = null;

    private FirebaseFirestore db;
    private ArrayList<Alarm> alarmList;

    private Alarms() {
        alarmList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        updateAlarmList();
    }

    public static Alarms getInstance() {
        if (instance == null) {
            instance = new Alarms();
        }
        return instance;
    }

    public boolean createAlarm() {
        //TODO: Lav Alarm og sæt i alarmList
        return false;
    }

    public void updateAlarmList() {
        db.collection("test").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        alarmList.add(new Alarm((String) document.get("time"), (String) document.get("label")));
                    }
                } else {
                    Log.w("Alarms_Firestore", "Error getting documents from Firestore", task.getException());
                }
            }
        });
    }

    public ArrayList<Alarm> getAlarmList() {
        return alarmList;
    }
}