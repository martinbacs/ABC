package com.android.project.abcappen.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.project.abcappen.R;
import com.android.project.abcappen.listeners.LetterFinishedListener;
import com.android.project.abcappen.services.Sounds;
import com.android.project.abcappen.views.PaintView;

public class GameActivity extends AppCompatActivity {

    private PaintView paintView;
    private Button nextButton;
    private ImageButton resetBtn;
    private Sounds sounds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        sounds = new Sounds(getApplicationContext());
        paintView = (PaintView) findViewById(R.id.paintView);
        nextButton = (Button) findViewById(R.id.nextBtn);
        resetBtn = (ImageButton) findViewById(R.id.resetBtn);

        nextButton.setVisibility(View.INVISIBLE);
        resetBtn.setVisibility(View.INVISIBLE);

        paintView.getLetterFinishedListener().setListener(new LetterFinishedListener.ChangeListener() {
            @Override
            public void onChange() {
                nextButton.setVisibility(View.VISIBLE);
                resetBtn.setVisibility(View.VISIBLE);
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintView.nextChar();
                paintView.clear();
                nextButton.setVisibility(View.INVISIBLE);
                resetBtn.setVisibility(View.INVISIBLE);
                sounds.playPopSound();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintView.clear();
                resetBtn.setVisibility(View.INVISIBLE);
                nextButton.setVisibility(View.INVISIBLE);
                sounds.playPopSound();
            }
        });
    }
}
