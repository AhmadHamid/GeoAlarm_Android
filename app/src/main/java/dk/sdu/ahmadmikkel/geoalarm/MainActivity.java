package dk.sdu.ahmadmikkel.geoalarm;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addEditAlarm(View view) {
        //Test commit text
        Intent intent = new Intent(this, AddAlarmActivity.class);

        startActivity(intent);
    }
}
