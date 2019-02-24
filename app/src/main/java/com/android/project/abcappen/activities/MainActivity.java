package com.android.project.abcappen.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.project.abcappen.data.ProfileContract;
import com.android.project.abcappen.data.ProfileDatabaseHelper;
import com.android.project.abcappen.services.BackgroundMusicService;
import com.android.project.abcappen.R;
import com.android.project.abcappen.services.Sounds;
import com.android.project.abcappen.shared.SharedPrefManager;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ProfileDatabaseHelper profileDatabaseHelper;
    ArrayAdapter<String>adapter;
    AnimationDrawable anim;
    private Sounds sounds;
    private Button addUserButton;
    private ListView userList;
    private ArrayList<String> userNameList;
    private ImageView imageViewAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewAnim = findViewById(R.id.imageView);
        if (imageViewAnim==null)throw new AssertionError();
        imageViewAnim.setBackgroundResource(R.drawable.animation_abc);
        anim = (AnimationDrawable) imageViewAnim.getBackground();
        anim.start();


        profileDatabaseHelper = new ProfileDatabaseHelper(getApplicationContext());
        sounds = new Sounds(this);

        Intent backgroundMusic = new Intent(this,BackgroundMusicService.class);
        startService(backgroundMusic);


        userList = findViewById(R.id.userListView);
        addUserButton = findViewById(R.id.addUserTestBtn);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sounds.playPopSound();
                addUserDialog();

            }
        });


        userNameList = profileDatabaseHelper.getAllProfiles();
        adapter = new ArrayAdapter<String>(this,R.layout.list_rows,R.id.textView5,userNameList);
        userList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String name = profileDatabaseHelper.getProfile(position+1);
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("name", name);
                SharedPrefManager.getInstance(getApplicationContext()).storeId(String.valueOf(position+1));
                startActivity(intent);
            }
        });

    }

    private void addUserDialog(){
        final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.user_list_dialog_layout);
                dialog.setTitle("Add user");

                Button dialogButton = (Button) dialog.findViewById(R.id.addUserBtn);
                final EditText addUserText = (EditText) dialog.findViewById(R.id.editText_addUser);

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sounds.playPopSound();

                        String username = addUserText.getText().toString();
                        if(TextUtils.isEmpty(username)) {
                            addUserText.setError("Var god fyll i namn");

                        }else {
                            profileDatabaseHelper.addProfile(username);
                            userNameList = profileDatabaseHelper.getAllProfiles();
                            adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_rows, R.id.textView5, userNameList);
                            userList.setAdapter(adapter);
                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent backgroundMusic = new Intent(this,BackgroundMusicService.class);
        stopService(backgroundMusic);
    }
}
