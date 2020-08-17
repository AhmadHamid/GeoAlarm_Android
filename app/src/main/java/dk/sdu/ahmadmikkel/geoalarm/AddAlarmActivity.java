package dk.sdu.ahmadmikkel.geoalarm;

import android.content.Intent;
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

    public void setTime(LocalTime time) {
        TextView timelabel = findViewById(R.id.addEditTime);
        this.time = time;
        timelabel.setText(time.toString());
    }

    public void addAlarm(View view) {
        //TODO: Lav alarm via. Alarms.createAlarm.
        alarms.createAlarm(time, label);
        //alarms.createAlarm(timeText.getText().toString(), labelText.getText().toString());

        Toast.makeText(this, "Alarm added", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent.hasExtra("alarm")) {
            Alarm alarm = intent.getParcelableExtra("alarm");
            time = LocalTime.of(alarm.getHour(), alarm.getMinute());
            label = alarm.getLabel();
        } else {
            time = LocalTime.now();
            label = "Label";
            //Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        timeText.setText(time.toString());
        labelText.setText(label);
    }
}
