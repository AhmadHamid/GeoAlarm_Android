package dk.sdu.ahmadmikkel.geoalarm;

import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

public class AddAlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }

    public void setTime(int hour, int minute) {
        TextView timeLabel = findViewById(R.id.addEditTime);

        timeLabel.setText(hour + ":" + minute);
    }
}
