package dk.sdu.ahmadmikkel.geoalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.time.LocalTime;

public class NotificationReceiver extends BroadcastReceiver {
    final String CHANNEL_ID = "GEOALARM_NOTIFICATION_CHANNEL_ID";

    //Alarm alarm;
    LocalTime time;
    String label;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // throw new UnsupportedOperationException("Not yet implemented");

/*        Log.d("MUGGEL_BROADCAST_REC", "Checking extra");
        Log.d("MUGGEL_BROADCAST_REC", intent.getStringExtra("alarmTest"));
        Log.d("MUGGEL_BROADCAST_REC", intent.getStringExtra("alarmLabel"));*/


        if (intent.hasExtra("alarmHour") && intent.hasExtra("alarmMinute") && intent.hasExtra("alarmLabel")) {
            Log.d("MUGGEL_BROADCAST", "Has extra");
            //alarm = intent.getParcelableExtra("alarm");
            time = LocalTime.of(intent.getIntExtra("alarmHour", 0), intent.getIntExtra("alarmMinute", 0));
            label = intent.getStringExtra("alarmLabel");
        } else {
            Log.d("MUGGEL_BROADCAST", "No extra");
            time = LocalTime.now();
            label = "Muggel";
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.settings_icon)
                .setContentTitle(time.toString())
                .setContentText(label)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                //.setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(101, builder.build());
    }
}
