package dk.sdu.ahmadmikkel.geoalarm;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.multidex.MultiDex;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addEditAlarm(View view) {
        Intent intent = new Intent(this, AddAlarmActivity.class);

        startActivity(intent);
    }
}
