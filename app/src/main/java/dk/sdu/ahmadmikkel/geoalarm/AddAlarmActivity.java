package dk.sdu.ahmadmikkel.geoalarm;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.multidex.MultiDex;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAlarmActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView timeText, labelText;
    String time, label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        timeText = findViewById(R.id.addEditTime);
        labelText = findViewById(R.id.addEditLabelLabel);

        getData();
        setData();
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
        Snackbar.make(view, "Snackbar", Snackbar.LENGTH_LONG).setAction("Alarm set", null).show();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent.hasExtra("alarm")) {
            Log.d("PARCELABLE_INTENT", intent.getExtras().toString());
            Alarm alarm = (Alarm) intent.getParcelableExtra("alarm");
            time = alarm.getTime();
            label = alarm.getLabel();
        } else {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        timeText.setText(time);
        labelText.setText(label);
    }
}
