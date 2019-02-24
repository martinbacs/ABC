package com.android.project.abcappen.data;

public class LetterProgress {
    private String letter;
    private String timesCompleted;
    private String completionTime;
    private String accuracy;

    public LetterProgress(String letter, String timesCompleted, String completionTime, String accuracy) {
        this.letter = letter;
        this.timesCompleted = timesCompleted;
        this.completionTime = completionTime;
        this.accuracy = accuracy;
    }

    public String getLetter() {
        return letter;
    }

    public String getTimesCompleted() {
        return timesCompleted;
    }

    public String getCompletionTime() {
        return completionTime;
    }

    public String getAccuracy() {
        return accuracy;
    }
}
