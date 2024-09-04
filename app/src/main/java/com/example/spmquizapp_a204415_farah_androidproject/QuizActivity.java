package com.example.spmquizapp_a204415_farah_androidproject;

import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.example.spmquizapp_a204415_farah_androidproject.databinding.ActivityQuizBinding;
import com.example.spmquizapp_a204415_farah_androidproject.databinding.ScoringBinding;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    public static List<QuestionModel> questionModelList;
    public static String time = "";

    private ActivityQuizBinding binding;
    private List<QuestionModel> incorrectQuestions;

    private int currentQuestionIndex = 0;
    private String selectedAnswer = "";
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        incorrectQuestions = new ArrayList<>();

        binding.btn0.setOnClickListener(this);
        binding.btn1.setOnClickListener(this);
        binding.btn2.setOnClickListener(this);
        binding.btn3.setOnClickListener(this);
        binding.nextBtn.setOnClickListener(this);

        loadQuestions();
        startTimer();
    }

    private void startTimer() {
        long totalTimeInMillis = Integer.parseInt(time) * 60 * 1000L;
        new CountDownTimer(totalTimeInMillis, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long remainingSeconds = seconds % 60;
                binding.timerIndicatorTextview.setText(String.format("%02d:%02d", minutes, remainingSeconds));
            }

            @Override
            public void onFinish() {
                finishQuiz();
            }
        }.start();
    }

    private void loadQuestions() {
        selectedAnswer = "";
        if (currentQuestionIndex == questionModelList.size()) {
            finishQuiz();
            return;
        }

        binding.questionIndicatorTextview.setText("Question " + (currentQuestionIndex + 1) + "/ " + questionModelList.size());
        binding.questionProgressIndicator.setProgress((int) ((currentQuestionIndex / (float) questionModelList.size()) * 100));
        binding.questionTextview.setText(questionModelList.get(currentQuestionIndex).getQuestion());
        binding.btn0.setText(questionModelList.get(currentQuestionIndex).getOptions().get(0));
        binding.btn1.setText(questionModelList.get(currentQuestionIndex).getOptions().get(1));
        binding.btn2.setText(questionModelList.get(currentQuestionIndex).getOptions().get(2));
        binding.btn3.setText(questionModelList.get(currentQuestionIndex).getOptions().get(3));

        binding.btn0.setBackgroundTintList(getResources().getColorStateList(R.color.choose1));
        binding.btn1.setBackgroundTintList(getResources().getColorStateList(R.color.choose2));
        binding.btn2.setBackgroundTintList(getResources().getColorStateList(R.color.choose3));
        binding.btn3.setBackgroundTintList(getResources().getColorStateList(R.color.choose4));
    }


    @Override
    public void onClick(View view) {
        resetButtonColors();

        Button clickedBtn = (Button) view;
        if (clickedBtn.getId() == R.id.next_btn) {

            if (selectedAnswer.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please select an answer to continue", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedAnswer.equals(questionModelList.get(currentQuestionIndex).getCorrect())) {
                score++;
            } else {
                incorrectQuestions.add(questionModelList.get(currentQuestionIndex));
            }
            currentQuestionIndex++;
            loadQuestions();
        } else {
            binding.btn0.setBackgroundTintList(getResources().getColorStateList(R.color.grey));
            binding.btn1.setBackgroundTintList(getResources().getColorStateList(R.color.grey));
            binding.btn2.setBackgroundTintList(getResources().getColorStateList(R.color.grey));
            binding.btn3.setBackgroundTintList(getResources().getColorStateList(R.color.grey));

            selectedAnswer = clickedBtn.getText().toString();

            if (view.getId() == R.id.btn0) {
                clickedBtn.setBackgroundTintList(getResources().getColorStateList(R.color.choose1));
            } else if (view.getId() == R.id.btn1) {
                clickedBtn.setBackgroundTintList(getResources().getColorStateList(R.color.choose2));
            } else if (view.getId() == R.id.btn2) {
                clickedBtn.setBackgroundTintList(getResources().getColorStateList(R.color.choose3));
            } else if (view.getId() == R.id.btn3) {
                clickedBtn.setBackgroundTintList(getResources().getColorStateList(R.color.choose4));
            }
        }
    }

    private void resetButtonColors() {
        binding.btn0.setBackgroundTintList(getResources().getColorStateList(R.color.grey));
        binding.btn1.setBackgroundTintList(getResources().getColorStateList(R.color.grey));
        binding.btn2.setBackgroundTintList(getResources().getColorStateList(R.color.grey));
        binding.btn3.setBackgroundTintList(getResources().getColorStateList(R.color.grey));
    }

    private void finishQuiz() {
        int totalQuestions = questionModelList.size();
        int percentage = (int) ((score / (float) totalQuestions) * 100);

        saveLastScore(percentage);

        ScoringBinding dialogBinding = ScoringBinding.inflate(getLayoutInflater());
        dialogBinding.scoreProgressIndicator.setProgress(percentage);
        dialogBinding.scoreProgressText.setText(percentage + " %");

        if (percentage > 60) {
            dialogBinding.scoreTitle.setText("Congratulations!");
            dialogBinding.scoreTitle.setTextColor(Color.WHITE);
        } else {
            dialogBinding.scoreTitle.setText("Sorry! You have failed");
            dialogBinding.scoreTitle.setTextColor(Color.RED);
        }

        dialogBinding.scoreSubtitle.setText(score + " out of " + totalQuestions + " are correct");

        dialogBinding.finishBtn.setOnClickListener(v -> finish());
        dialogBinding.showIncorrectBtn.setVisibility(View.VISIBLE);
        dialogBinding.showIncorrectBtn.setOnClickListener(v -> showIncorrectQuestions());

        new AlertDialog.Builder(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen)
                .setView(dialogBinding.getRoot())
                .setCancelable(false)
                .show();
    }

    private void saveLastScore(int score) {

        String subjectTitle = getIntent().getStringExtra("SUBJECT_TITLE");
        SharedPreferences sharedPreferences = getSharedPreferences("QuizScores", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(subjectTitle, score);
        editor.apply();

    }

    private void showIncorrectQuestions() {

        View dialogView = getLayoutInflater().inflate(R.layout.incorrect_questions, null);
        TextView incorrectQuestionsSummary = dialogView.findViewById(R.id.incorrect_questions_summary);
        Button okButton = dialogView.findViewById(R.id.ok_button);


        StringBuilder incorrectSummary = new StringBuilder();
        for (QuestionModel question : incorrectQuestions) {
            incorrectSummary.append("Question: ").append(question.getQuestion()).append("\n");
            incorrectSummary.append("Correct Answer: ").append(question.getCorrect()).append("\n\n");
        }

        incorrectQuestionsSummary.setText(incorrectSummary.toString());

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        okButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();


    }

}