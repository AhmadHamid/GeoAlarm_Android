package dk.sdu.ahmadmikkel.geoalarm;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.multidex.MultiDex;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {
    Alarms alarms = Alarms.getInstance();
    Scheduler scheduler;

    RecyclerView recyclerView;
    RecyclerAdapter adapter;

    private static final int REQUEST_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();

        scheduler = Scheduler.getInstance(getApplicationContext(), getSystemService(NotificationManager.class));

        alarms.addObserver(this);

        recyclerView = findViewById(R.id.recyclerView);

        adapter = new RecyclerAdapter(this, alarms.getAlarmList());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION}, REQUEST_CODE);
            return;
        }
    }

    public void alarmSettings(View v) {
        Intent intent = new Intent(this, AddAlarmActivity.class);
        startActivity(intent);
    }

    public void schedulerTest() {
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        //PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), require)
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.getClass() == Alarms.class) {
            adapter.notifyDataSetChanged();
        }
    }

    //test for at se om git ssh virker

    @Override
    protected void onResume() {
        super.onResume();
        //adapter.notifyDataSetChanged();
        //alarms.resetList();
        //alarms.loadFromFirestore();
        //recyclerView.getAdapter().notifyDataSetChanged();
    }
}
