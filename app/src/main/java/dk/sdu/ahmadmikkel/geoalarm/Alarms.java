package dk.sdu.ahmadmikkel.geoalarm;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class Alarms {
    private static Alarms instance = null;

    private FirebaseFirestore db;
    private ArrayList<Alarm> alarmList;

    private Alarms() {
        alarmList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        // Hack fordi recyclerview ikke opdateres ved nye entries.
        alarmList.add(createAlarm("11:30", "Hack"));

        updateAlarmList();
    }

    public static Alarms getInstance() {
        if (instance == null) {
            instance = new Alarms();
        }
        return instance;
    }

    public void createAlarm(Map<String, String> alarm) {
        db.collection("alarmTest").add(alarm).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("ALARM_ADDED", "ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("ALARM_ADDED_ERROR", "Error adding document", e);
            }
        });
    }

    private Alarm createAlarm(String time, String label) {
        return new Alarm(time, label);
    }

    public void updateAlarmList() {
        // Hack fordi recyclerview ikke opdateres ved nye entries.
        if (alarmList.size() > 1) {
            alarmList = new ArrayList<>();
        }

        db.collection("test").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //alarmList.add(new Alarm((String) document.get("time"), (String) document.get("label")));
                        alarmList.add(createAlarm((String) document.get("time"), (String) document.get("label")));
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