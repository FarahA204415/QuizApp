package com.example.spmquizapp_a204415_farah_androidproject;

import java.util.List;
import java.util.Objects;

public class QuestionModel {

    private String question;
    private List<String> options;
    private String correct;

    public QuestionModel(String question, List<String> options, String correct) {
        this.question = question;
        this.options = options;
        this.correct = correct;
    }

    public QuestionModel() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionModel that = (QuestionModel) o;
        return Objects.equals(question, that.question) &&
                Objects.equals(options, that.options) &&
                Objects.equals(correct, that.correct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, options, correct);
    }
}
