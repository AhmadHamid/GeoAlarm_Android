package dk.sdu.ahmadmikkel.geoalarm;

import android.os.Parcel;
import android.os.Parcelable;
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
        alarmList.add(new Alarm("11:30", "Hack"));

        loadFromFirestore();
    }

    public static Alarms getInstance() {
        if (instance == null) {
            instance = new Alarms();
        }
        return instance;
    }

    public void createAlarm(Map<String, String> alarmMap) {

        Alarm alarm = new Alarm("11:00", "Test");

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

    public void loadFromFirestore() {
        // Hack fordi recyclerview ikke opdateres ved nye entries.
        /*if (alarmList.size() > 1) {
            alarmList = new ArrayList<>();
        }*/

        db.collection("alarmTest").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        alarmList.add(document.toObject(Alarm.class));
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