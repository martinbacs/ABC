package com.android.project.abcappen.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.project.abcappen.R;
import com.android.project.abcappen.data.LetterProgress;
import com.android.project.abcappen.data.ProfileDatabaseHelper;
import com.android.project.abcappen.services.BackgroundMusicService;
import com.android.project.abcappen.services.Sounds;
import com.android.project.abcappen.shared.SharedPrefManager;

import java.util.ArrayList;

public class WritingStatisticsActivity extends AppCompatActivity {
    private ProfileDatabaseHelper profileDatabaseHelper;
    private ArrayList<LetterProgress> writingProgress;
    private String[] letters;
    private int lettersIdx;
    private LetterProgress letterProgress;
    private Sounds sounds;


    private TextView tvName, tvLetter, tvTimesCompleted, tvCompletionTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Intent backgroundMusic = new Intent(this,BackgroundMusicService.class);
        startService(backgroundMusic);

        profileDatabaseHelper = new ProfileDatabaseHelper(getApplicationContext());
        String profileId = SharedPrefManager.getInstance(getApplicationContext()).getId();
        writingProgress = profileDatabaseHelper.getWritingProgress(profileId);
        sounds = new Sounds(getApplicationContext());

        tvName = findViewById(R.id.tv_name);
        tvName.setText(profileDatabaseHelper.getProfile(Integer.parseInt(profileId)));

        tvLetter = findViewById(R.id.tv_letter);
        tvTimesCompleted = findViewById(R.id.tv_times_completed);
        tvCompletionTime = findViewById(R.id.tv_completion_time);

        lettersIdx = 0;
        letterProgress = writingProgress.get(lettersIdx);
        tvLetter.setText(letterProgress.getLetter());

        String timesCompleted = !letterProgress.getTimesCompleted().equals("0") ?
                letterProgress.getTimesCompleted() : "?";
        String completionTime = !letterProgress.getCompletionTime().equals("0") ?
                String.valueOf(Double.parseDouble(letterProgress.getCompletionTime())/1000) : "?";
        String accuracy = !letterProgress.getAccuracy().equals("0") ?
                letterProgress.getAccuracy() : "";
        tvTimesCompleted.setText(timesCompleted);
        tvCompletionTime.setText(completionTime);
    }

    public void nextLetterStats(View v) {
        sounds.playPopSound();

        lettersIdx++;
        if (lettersIdx == writingProgress.size()) {
            lettersIdx = 0;
        }
        letterProgress = writingProgress.get(lettersIdx);
        String timesCompleted = !letterProgress.getTimesCompleted().equals("0") ?
                letterProgress.getTimesCompleted() : "?";
        String completionTime = !letterProgress.getCompletionTime().equals("0") ?
                String.valueOf(Double.parseDouble(letterProgress.getCompletionTime())/1000) : "?";
        String accuracy = !letterProgress.getAccuracy().equals("0") ?
                letterProgress.getAccuracy() : "";
        tvLetter.setText(letterProgress.getLetter());
        tvTimesCompleted.setText(timesCompleted);
        tvCompletionTime.setText(completionTime);
    }

    public void previousLetterStats(View v) {
        sounds.playPopSound();

        lettersIdx--;
        if (lettersIdx < 0) {
            lettersIdx = 0;
            return;
        }
        letterProgress = writingProgress.get(lettersIdx);
        String timesCompleted = !letterProgress.getTimesCompleted().equals("0") ?
                letterProgress.getTimesCompleted() : "?";
        String completionTime = !letterProgress.getCompletionTime().equals("0") ?
                String.valueOf(Double.parseDouble(letterProgress.getCompletionTime())/1000) : "?";
        String accuracy = !letterProgress.getAccuracy().equals("0") ?
                letterProgress.getAccuracy() : "";
        tvLetter.setText(letterProgress.getLetter());
        tvTimesCompleted.setText(timesCompleted);
        tvCompletionTime.setText(completionTime);
    }
}
