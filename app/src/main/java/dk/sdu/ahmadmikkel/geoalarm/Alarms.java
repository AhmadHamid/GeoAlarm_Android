package dk.sdu.ahmadmikkel.geoalarm;

import android.location.Location;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;

public class Alarms extends Observable {
    private static Alarms instance = null;

    private FirebaseFirestore db;
    private ArrayList<Alarm> alarmList;

    private Alarms() {
        alarmList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        loadFromFirestore();
    }

    public static Alarms getInstance() {
        if (instance == null) {
            instance = new Alarms();
        }
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Alarm addAlarmToAlarmList(LocalTime time, String label, double longitude, double latitude, String id) {
        Alarm alarm = new Alarm(time.getHour(), time.getMinute(), label, longitude, latitude, id);
        alarmList.add(alarm);
        setChanged();
        notifyObservers();

        return alarm;
    }

    private void addAlarmToAlarmList(Alarm alarm) {
        alarmList.add(alarm);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createAlarm(LocalTime time, String label, Location location, String id) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        Alarm alarm = addAlarmToAlarmList(time, label, longitude, latitude, id);

        db.collection("muggel").add(alarm).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("ALARM_ADDED", "ID: " + documentReference.getId());
                alarm.setId(documentReference.getId());
                updateId(documentReference);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("ALARM_ADDED_ERROR", "Error adding document", e);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateAlarm(Alarm alarm) {
        Log.d("UPDATE_ALARM", "updateAlarm: " + alarm.getLabel());
        Log.d("UPDATE_ALARM", "updateAlarm: " + alarm.getId());
        db.collection("muggel").document(alarm.getId()).set(alarm);

    }

    public void loadFromFirestore() {
        db.collection("muggel").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        addAlarmToAlarmList(document.toObject(Alarm.class));
                    }
                    setChanged();
                    notifyObservers();
                } else {
                    Log.w("Alarms_Firestore", "Error getting documents from Firestore", task.getException());
                }
            }
        });
    }

    public void updateId(DocumentReference docRef) {
        db.collection("muggel").document(docRef.getId()).update("id", docRef.getId());

    }

    public ArrayList<Alarm> getAlarmList() {
        return alarmList;
    }
}