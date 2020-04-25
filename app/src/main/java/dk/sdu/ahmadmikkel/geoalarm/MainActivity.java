package dk.sdu.ahmadmikkel.geoalarm;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

    FirebaseFirestore db;

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

        s1 = new String[2];
        s2 = new String[2];

        db = FirebaseFirestore.getInstance();

        db.collection("test").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int i = 0;
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Log.d("FIRESTORE", documentSnapshot.getId() + ": " + documentSnapshot.get("time"));
                        s1[i] = (String) documentSnapshot.get("time");
                        s2[i] = (String) documentSnapshot.get("label");
                        i++;
                    }

                    recyclerView = findViewById(R.id.recyclerView);

                    RecyclerAdapter adapter = new RecyclerAdapter(getApplicationContext(), s1, s2, images);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                } else {
                    Log.w("FIRESTORE", "Error getting documents from Firestore", task.getException());
                }
            }
        });

        //s1 = getResources().getStringArray(R.array.test_array);
        //s2 = getResources().getStringArray(R.array.test_array2);

//        recyclerView = findViewById(R.id.recyclerView);
//
//        RecyclerAdapter adapter = new RecyclerAdapter(this, s1, s2, images);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
