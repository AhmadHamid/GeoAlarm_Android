package dk.sdu.ahmadmikkel.geoalarm;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.multidex.MultiDex;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

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

        RecyclerAdapter adapter = new RecyclerAdapter(this, s1, s2, images);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
