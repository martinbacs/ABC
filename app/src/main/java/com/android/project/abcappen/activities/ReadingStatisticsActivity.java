package com.android.project.abcappen.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.project.abcappen.R;
import com.android.project.abcappen.data.ProfileDatabaseHelper;
import com.android.project.abcappen.data.WordProgress;
import com.android.project.abcappen.services.BackgroundMusicService;
import com.android.project.abcappen.services.Sounds;
import com.android.project.abcappen.shared.SharedPrefManager;

import java.util.ArrayList;

public class ReadingStatisticsActivity extends AppCompatActivity {
    private ProfileDatabaseHelper profileDatabaseHelper;
    private Sounds sounds;
    private TextView tvName, tvWord, tvTimesCompleted;
    private ArrayList<WordProgress> readingProgress;
    private WordProgress wordProgress;
    private int wordsIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_statistics);

        Intent backgroundMusic = new Intent(this,BackgroundMusicService.class);
        startService(backgroundMusic);

        profileDatabaseHelper = new ProfileDatabaseHelper(getApplicationContext());
        String profileId = SharedPrefManager.getInstance(getApplicationContext()).getId();
        readingProgress =  profileDatabaseHelper.getReadingProgress(profileId);
        sounds = new Sounds(getApplicationContext());

        tvName = findViewById(R.id.tv_name2);
        tvName.setText(profileDatabaseHelper.getProfile(Integer.parseInt(profileId)));

        tvWord = findViewById(R.id.tv_word);
        tvTimesCompleted = findViewById(R.id.tv_times_completed2);

        wordsIdx = 0;

        wordProgress = readingProgress.get(wordsIdx);
        tvWord.setText(wordProgress.getWord());
        tvTimesCompleted.setText(wordProgress.getTimesCompleted());
    }

    public void nextLetterStats(View v) {
        sounds.playPopSound();

        wordsIdx++;
        if (wordsIdx == readingProgress.size()) {
            wordsIdx = 0;
        }
        wordProgress = readingProgress.get(wordsIdx);
        tvWord.setText(wordProgress.getWord());
        String timesCompleted = !wordProgress.getTimesCompleted().equals("0") ?
                wordProgress.getTimesCompleted() : "?";
        tvTimesCompleted.setText(timesCompleted);
    }

    public void previousLetterStats(View v) {
        sounds.playPopSound();

        wordsIdx--;
        if (wordsIdx < 0) {
            wordsIdx = 0;
            return;
        }
        wordProgress = readingProgress.get(wordsIdx);
        tvWord.setText(wordProgress.getWord());
        String timesCompleted = !wordProgress.getTimesCompleted().equals("0") ?
                wordProgress.getTimesCompleted() : "?";
        tvTimesCompleted.setText(timesCompleted);
    }
}
