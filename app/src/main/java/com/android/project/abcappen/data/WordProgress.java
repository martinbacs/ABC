package com.android.project.abcappen.data;

public class WordProgress {
    private String word;
    private String timesCompleted;
    private String completionTime;
    private String accuracy;

    public WordProgress(String letter, String timesCompleted) {
        this.word = letter;
        this.timesCompleted = timesCompleted;
    }

    public String getWord() {
        return word;
    }

    public String getTimesCompleted() {
        return timesCompleted;
    }
}
