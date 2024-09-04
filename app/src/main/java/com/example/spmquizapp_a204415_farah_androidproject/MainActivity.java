package com.example.spmquizapp_a204415_farah_androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageButton nextButton;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        nextButton = findViewById(R.id.nextButton);

        ArrayList<PageContent> contentList = new ArrayList<>();
        contentList.add(new PageContent("Practice SPM questions and boost your confidence!", ""));
        contentList.add(new PageContent("Challenge yourself with quizzes in Mathematics, Science, and more!", ""));
        contentList.add(new PageContent("From Algebra to English Grammar, test your knowledge with us.", ""));

        ViewPagerAdapter adapter = new ViewPagerAdapter(this, contentList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager, true);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Subject.class);
                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        super.onResume();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            mFirebaseAuth.getCurrentUser().reload().addOnCompleteListener(task -> {
                if (!task.isSuccessful() || mFirebaseAuth.getCurrentUser() == null) {
                    mFirebaseAuth.signOut();
                    navigateToLogin();
                }
            });
        }
    }

    private void navigateToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}