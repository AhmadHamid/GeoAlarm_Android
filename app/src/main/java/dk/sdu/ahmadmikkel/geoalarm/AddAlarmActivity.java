package dk.sdu.ahmadmikkel.geoalarm;

import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.multidex.MultiDex;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AddAlarmActivity extends AppCompatActivity {
    Alarms alarms = Alarms.getInstance();

    TextView timeText;
    EditText labelText;
    LocalTime time;
    String label;
    Location location;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        timeText = findViewById(R.id.addEditTime);
        labelText = findViewById(R.id.addEditLabelValue);

        getData();
        setData();


/*        if (getIntent().hasExtra("alarm")) {

        } else {

        }*/

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }

    public void setTime(LocalTime time) {
        TextView timelabel = findViewById(R.id.addEditTime);
        this.time = time;
        timelabel.setText(time.toString());
    }

    public void setLocation(Location l) {
        Log.d("muggel_location", "setLocation: " + l.toString());
        this.location = l;
    }

    public void addAlarm(View view) {
        //TODO: Lav alarm via. Alarms.createAlarm.
        Intent intent = getIntent();
        if (intent.hasExtra("alarm")) {
            Alarm alarm = intent.getParcelableExtra("alarm");
            alarm.setHour(time.getHour());
            alarm.setMinute(time.getMinute());
            alarm.setLabel(labelText.getText().toString());
            alarm.setLongitude(location.getLongitude());
            alarm.setLatitude(location.getLatitude());
            alarms.updateAlarm(alarm);
        } else {
            alarms.createAlarm(time, labelText.getText().toString(), location, id);
            //alarms.createAlarm(timeText.getText().toString(), labelText.getText().toString());
            Toast.makeText(this, "Alarm added", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent.hasExtra("alarm")) {
            Alarm alarm = intent.getParcelableExtra("alarm");
            time = LocalTime.of(alarm.getHour(), alarm.getMinute());
            label = alarm.getLabel();
            location = new Location("");
            location.setLongitude(alarm.getLongitude());
            location.setLatitude(alarm.getLatitude());
            id = alarm.getId();
        } else {
            time = LocalTime.now();
            //Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        timeText.setText(time.toString());
        labelText.setText(label);
    }
}
