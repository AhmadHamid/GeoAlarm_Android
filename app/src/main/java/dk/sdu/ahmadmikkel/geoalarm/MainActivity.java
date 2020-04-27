package dk.sdu.ahmadmikkel.geoalarm;

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

public class MainActivity extends AppCompatActivity {
    Alarms alarms = Alarms.getInstance();

    RecyclerView recyclerView;
    RecyclerAdapter adapter;

    String[] s1;
    String[] s2;
    int[] images = {R.drawable.c_language,
            R.drawable.golang_language,
            R.drawable.php_language,
            R.drawable.python_language,
            R.drawable.ruby_language,
            R.drawable.travis_language};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s1 = getResources().getStringArray(R.array.test_array);
        s2 = getResources().getStringArray(R.array.test_array2);

        recyclerView = findViewById(R.id.recyclerView);

        adapter = new RecyclerAdapter(this, s1, s2, images);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void alarmSettings(View v) {
        adapter.notifyDataSetChanged();
        Intent intent = new Intent(this, AddAlarmActivity.class);
        startActivity(intent);
    }
}
