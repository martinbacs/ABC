package com.android.project.abcappen.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.project.abcappen.R;
import com.android.project.abcappen.data.ProfileDatabaseHelper;
import com.android.project.abcappen.services.BackgroundMusicService;
import com.android.project.abcappen.services.Sounds;
import com.android.project.abcappen.shared.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {
    private ProfileDatabaseHelper profileDatabaseHelper;
    private TextView tvName;
    private TextView tvWritingStats;
    private TextView tvReadingStats;
    private ImageView imageViewAnim;
    AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageViewAnim = findViewById(R.id.imageView);
        if (imageViewAnim==null)throw new AssertionError();
        imageViewAnim.setBackgroundResource(R.drawable.animation_abc);
        anim = (AnimationDrawable) imageViewAnim.getBackground();
        anim.start();

        profileDatabaseHelper = new ProfileDatabaseHelper(getApplicationContext());

        tvName = findViewById(R.id.tv_name);
        tvWritingStats = findViewById(R.id.tv_writing_stats);
        tvReadingStats = findViewById(R.id.tv_reading_stats);

        Intent intent = getIntent();
        tvName.setText(intent.getStringExtra("name"));

        String numCompletedLetters = String.valueOf(profileDatabaseHelper.getNumberOfCompletedLetters(SharedPrefManager.getInstance(getApplicationContext()).getId()));
        String[] letters = getResources().getStringArray(R.array.letters);
        String numCompletedWords = String.valueOf(profileDatabaseHelper.getNumberOfCompletedWords(SharedPrefManager.getInstance(getApplicationContext()).getId()));
        String[] words = getResources().getStringArray(R.array.words);
        tvWritingStats.setText(getString(R.string.writing_statistics) + " " + numCompletedLetters + "/" + letters.length);
        tvReadingStats.setText(getString(R.string.reading_statistics) + " " + numCompletedWords + "/" + words.length);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        tvName.setText(intent.getStringExtra("name"));

        String numCompletedLetters = String.valueOf(profileDatabaseHelper.getNumberOfCompletedLetters(SharedPrefManager.getInstance(getApplicationContext()).getId()));
        String[] letters = getResources().getStringArray(R.array.letters);
        String numCompletedWords = String.valueOf(profileDatabaseHelper.getNumberOfCompletedWords(SharedPrefManager.getInstance(getApplicationContext()).getId()));
        String[] words = getResources().getStringArray(R.array.words);
        tvWritingStats.setText(getString(R.string.writing_statistics) + " " + numCompletedLetters + "/" + letters.length);
        tvReadingStats.setText(getString(R.string.reading_statistics) + " " + numCompletedWords + "/" + words.length);
    }

    public void showDetailedWritingStatistics(View v) {
        Sounds sounds = new Sounds(this);
        sounds.playPopSound();
        startActivity(new Intent(getApplicationContext(), WritingStatisticsActivity.class));
    }

    public void showDetailedReadingStatistics(View v) {
        Sounds sounds = new Sounds(this);
        sounds.playPopSound();
        startActivity(new Intent(getApplicationContext(), ReadingStatisticsActivity.class));
    }

    public void playWritingGame(View v) {
        Sounds sounds = new Sounds(this);
        sounds.playPopSound();
        Intent backgroundMusic = new Intent(this,BackgroundMusicService.class);
        stopService(backgroundMusic);
        startActivity(new Intent(getApplicationContext(), GameActivity.class));
    }

    public void playSpeechGame(View v){
        Sounds sounds = new Sounds(this);
        sounds.playPopSound();
        Intent backgroundMusic = new Intent(this,BackgroundMusicService.class);
        stopService(backgroundMusic);
        startActivity(new Intent(getApplicationContext(), SpeechActivity.class));
    }
}
