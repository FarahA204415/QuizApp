package com.example.spmquizapp_a204415_farah_androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Subject extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private List<QuizModel> quizModelList;
    private QuizListAdapter adapter;
    private ImageButton logoutButton;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subject);

        mFirebaseAuth = FirebaseAuth.getInstance();

        logoutButton = findViewById(R.id.action_logout);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler_view);

        quizModelList = new ArrayList<>();
        setupRecyclerView();
        getDataFromFirebase();


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseAuth.signOut();
                Intent intent = new Intent(Subject.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
    protected void onResume() {
        super.onResume();
        getDataFromFirebase();
    }

    private void setupRecyclerView() {
        adapter = new QuizListAdapter(quizModelList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void getDataFromFirebase() {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.get().addOnSuccessListener(dataSnapshot -> {
            quizModelList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    QuizModel quizModel = snapshot.getValue(QuizModel.class);
                    if (quizModel != null) {
                        quizModelList.add(quizModel);
                    }
                }
            }
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }).addOnFailureListener(e -> {
            progressBar.setVisibility(View.GONE);
        });
    }

}