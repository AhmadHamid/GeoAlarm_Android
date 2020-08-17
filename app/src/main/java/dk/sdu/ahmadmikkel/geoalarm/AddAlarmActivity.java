package dk.sdu.ahmadmikkel.geoalarm;

import android.content.Intent;
import android.location.Location;
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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AddAlarmActivity extends AppCompatActivity {
    Alarms alarms = Alarms.getInstance();

    TextView timeText;
    EditText labelText;
    String time, label;
    Location location;
    MapView map;

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

    public void setTime(int hour, int minute) {
        TextView timeLabel = findViewById(R.id.addEditTime);
        timeLabel.setText(hour + ":" + minute);
    }

    public void addAlarm(View view) {
        //TODO: Lav alarm via. Alarms.createAlarm.
        alarms.createAlarm(timeText.getText().toString(), labelText.getText().toString(), location);

        Toast.makeText(this, "Alarm added", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent.hasExtra("alarm")) {
            Alarm alarm = intent.getParcelableExtra("alarm");
            time = alarm.getTime();
            label = alarm.getLabel();
            location = alarm.getLocation();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
            time = formatter.format(Instant.now());
            label = "Label";
            //Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        timeText.setText(time);
        labelText.setText(label);
    }
}
