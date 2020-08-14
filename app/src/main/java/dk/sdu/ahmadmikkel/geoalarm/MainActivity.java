package dk.sdu.ahmadmikkel.geoalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    RecyclerView recyclerView;
    RecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarms.addObserver(this);

        recyclerView = findViewById(R.id.recyclerView);

        adapter = new RecyclerAdapter(this, alarms.getAlarmList());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
}
