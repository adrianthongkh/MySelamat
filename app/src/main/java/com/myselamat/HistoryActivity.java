package com.myselamat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.myselamat.model.TravelHistory;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView historyRv;
    FirebaseFirestore fStore;
    FirebaseUser user;
    FirebaseAuth fAuth;
    private Recycle_View_Travel_History hisadapter;
    private ArrayList<TravelHistory> historyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // configure main toolbar
        ImageView btn_back = findViewById(R.id.backBtn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        historyRv = findViewById(R.id.historylist);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        historyList= new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference his = (CollectionReference) db.collection("History");

        Task<QuerySnapshot> query = his.whereEqualTo("idd", ""+user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                historyList.clear();

                for (QueryDocumentSnapshot document : task.getResult()) {
                    TravelHistory th = new TravelHistory();
                    th.setLocation(""+document.get("Location"));
                    th.setCheckOut(""+document.get("CheckOut"));
                    th.setCheckIn(""+document.get("CheckIn"));
                    historyList.add(th);
                }
                LinearLayoutManager manager = new LinearLayoutManager(HistoryActivity.this);
                historyRv.setLayoutManager(manager);

                hisadapter= new Recycle_View_Travel_History(HistoryActivity.this,  historyList);
                historyRv.setAdapter(hisadapter);


            }
        });




    }
}