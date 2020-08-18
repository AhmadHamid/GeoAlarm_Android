package dk.sdu.ahmadmikkel.geoalarm;

import android.Manifest;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.google.android.gms.location.*;
import com.google.android.gms.tasks.OnSuccessListener;

import java.time.LocalTime;
import java.util.Calendar;

public class Scheduler {
    private static Scheduler instance = null;
    private static final String CHANNEL_ID = "GEOALARM_NOTIFICATION_CHANNEL_ID";

    Alarms alarms;
    AlarmManager alarmManager;
    FusedLocationProviderClient fusedLocationProviderClient;

    LocationRequest locationRequest;
    LocationCallback locationCallback;

    public Scheduler(Context context, NotificationManager manager) {
        alarms = Alarms.getInstance();
        createNotificationChannel(manager);

        //setNotification(context);

        createAlarmManager(context);

        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    for (Alarm alarm : alarms.getAlarmList()) {
                        Location alarmLocation = new Location("");
                        alarmLocation.setLatitude(alarm.getLatitude());
                        alarmLocation.setLongitude(alarm.getLongitude());

                        LocalTime time = LocalTime.of(alarm.getHour(), alarm.getMinute());

                        Intent intent = new Intent(context, NotificationReceiver.class);
                        intent.putExtra("alarmTest", "Hej");
                        intent.putExtra("alarmHour", alarm.getHour());
                        intent.putExtra("alarmMinute", alarm.getMinute());
                        intent.putExtra("alarmLabel", alarm.getLabel());
//                        intent.putExtra("alarm", alarm);

/*                        Log.d("MUGGEL_BROADCAST_SCH", "Checking extra");
                        Log.d("MUGGEL_BROADCAST_SCH", intent.getStringExtra("alarmTest"));
                        Log.d("MUGGEL_BROADCAST_SCH", intent.getStringExtra("alarmLabel"));*/

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        if (isInRadius(location, alarmLocation)) {
                            if (!alarm.isSchedulerSet()) {
                                Toast.makeText(context, "Alarm" + alarm.getHour() + ":" + alarm.getMinute() + " is in radius", Toast.LENGTH_LONG).show();

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
                                calendar.set(Calendar.MINUTE, alarm.getMinute());

                                //alarmManager.set(AlarmManager.RTC_WAKEUP,  calendar.getTimeInMillis(), pendingIntent);
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
                                alarm.setSchedulerSet(true);
                                alarms.updateAlarm(alarm);
                            }
                        } else {
                            if (alarm.isSchedulerSet()) {
                                Toast.makeText(context, "Alarm " + alarm.getHour() + ":" + alarm.getMinute() + " is NOT in radius", Toast.LENGTH_LONG).show();

                                alarmManager.cancel(pendingIntent);
                                alarm.setSchedulerSet(false);
                                alarms.updateAlarm(alarm);
                            }

                        }
                    }
                }
            }
        };

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        getLocation(context);
    }

    public static Scheduler getInstance(Context context, NotificationManager manager) {
        if (instance == null) {
            instance = new Scheduler(context, manager);
        }
        return instance;
    }

    private void createAlarmManager(Context context) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        /*Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_NO_CREATE);

        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }*/
    }

    private void createNotificationChannel(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "geoAlarmChannel";
            String description = "Channel for GeoAlarm notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = manager;
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void getLocation(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Log.d("MUGGEL_SCHEDULER_LOCAT", String.valueOf(location.getLatitude()) + "; " + String.valueOf(location.getLongitude()));
                } else {
                    Log.d("MUGGEL_SCHEDULER_LOCAT", "No location");
                }
            }
        });

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private boolean isInRadius(Location location1, Location location2) {
        if (location1.distanceTo(location2) < 25.0) {
            return true;
        }
        return false;
    }

    public void setNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.settings_icon)
                .setContentTitle("Title")
                .setContentText("Text")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(28365343, builder.build());
    }
}
