package dk.sdu.ahmadmikkel.geoalarm;

import android.Manifest;
import android.app.AlarmManager;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import org.jetbrains.annotations.NotNull;

public class SchedulerActivity extends AppCompatActivity {
    private static SchedulerActivity instance = null;
    private static final String CHANNEL_ID = "GEOALARM_NOTIFICATION_CHANNEL_ID";

    Alarms alarms;
    AlarmManager alarmManager;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alarms = Alarms.getInstance();
        // TODO: Call createNotificationChannel
        // TODO: Call setNotification
        // TODO: Call createAlarmManager

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLocationPermission();
    }

    public static SchedulerActivity getInstance() {
        if (instance == null) {
            instance = new SchedulerActivity();
        }
        return instance;
    }

    private void createAlarmManager() {

    }

    private void createNotificationChannel() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }

    private void getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        getLocation();
    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Log.d("MUGGEL_SCHEDULER_LOCAT", String.valueOf(location.getLatitude()));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d("MUGGEL_SCHEDULER_LOCAT", e.toString());
            }
        });
    }

    private void setNotification() {

    }
}