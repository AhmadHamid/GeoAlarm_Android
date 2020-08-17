package dk.sdu.ahmadmikkel.geoalarm;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Scheduler {
    private static Scheduler instance = null;
    private static final String CHANNEL_ID = "GEOALARM_NOTIFICATION_CHANNEL_ID";

    Alarms alarms;
    AlarmManager alarmManager;

    public Scheduler(Context context, NotificationManager manager) {
        alarms = Alarms.getInstance();
        //createNotificationChannel(manager);
        //setNotification(context);
        Log.d("MUGGEL_SCHEDULER_NOTI", "Notification created");
        createAlarmManager(context);
    }

    public static Scheduler getInstance(Context context, NotificationManager manager) {
        if (instance == null) {
            instance = new Scheduler(context, manager);
        }
        return instance;
    }

    private void createAlarmManager(Context context) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_NO_CREATE);

        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private void createNotificationChannel(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "WHAT";
            String description = "WHAT?";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = manager;
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void setNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

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
