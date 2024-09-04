package com.example.spmquizapp_a204415_farah_androidproject;

import java.util.List;
import java.util.Objects;

public class QuizModel {

    private String id;
    private String title;
    private String subtitle;
    private String time;
    private List<QuestionModel> questionList;

    public QuizModel(String id, String title, String subtitle, String time, List<QuestionModel> questionList) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.time = time;
        this.questionList = questionList;
    }

    public QuizModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<QuestionModel> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionModel> questionList) {
        this.questionList = questionList;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizModel quizModel = (QuizModel) o;
        return Objects.equals(id, quizModel.id) &&
                Objects.equals(title, quizModel.title) &&
                Objects.equals(subtitle, quizModel.subtitle) &&
                Objects.equals(time, quizModel.time) &&
                Objects.equals(questionList, quizModel.questionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, subtitle, time, questionList);
    }
}
