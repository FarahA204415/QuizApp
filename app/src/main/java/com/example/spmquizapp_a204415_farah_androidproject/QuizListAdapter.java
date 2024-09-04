package com.example.spmquizapp_a204415_farah_androidproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.MyViewHolder> {
    private final List<QuizModel> quizModelList;
    private Context context;

    public QuizListAdapter(List<QuizModel> quizModelList, Context context) {
        this.quizModelList = quizModelList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView quizTitleText, quizSubtitleText, quizTimeText, lastScoreText;

        public MyViewHolder(View itemView) {
            super(itemView);
            quizTitleText = itemView.findViewById(R.id.quiz_title_text);
            quizSubtitleText = itemView.findViewById(R.id.quiz_subtitle_text);
            quizTimeText = itemView.findViewById(R.id.quiz_time_text);
            lastScoreText = itemView.findViewById(R.id.last_score_text);
        }

        public void bind(final QuizModel model, final Context context) {
            quizTitleText.setText(model.getTitle());
            quizSubtitleText.setText(model.getSubtitle());
            quizTimeText.setText(model.getTime() + " min");

            int lastScore = getLastScore(context, model.getTitle());
            lastScoreText.setText("Last Score: " + lastScore + "%");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, QuizActivity.class);
                    intent.putExtra("SUBJECT_TITLE", model.getTitle());
                    QuizActivity.questionModelList = model.getQuestionList();
                    QuizActivity.time = model.getTime();
                    context.startActivity(intent);
                }
            });
        }

        private int getLastScore(Context context, String subjectTitle) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("QuizScores", Context.MODE_PRIVATE);
            return sharedPreferences.getInt(subjectTitle, 0);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item_recycler, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(quizModelList.get(position), context);
    }

    @Override
    public int getItemCount() {
        return quizModelList.size();
    }
}
