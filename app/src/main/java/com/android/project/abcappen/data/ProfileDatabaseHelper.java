package com.android.project.abcappen.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.android.project.abcappen.R;

import java.util.ArrayList;

public class ProfileDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;

    public ProfileDatabaseHelper(Context context) {
        super(context, ProfileContract.DATABASE_NAME, null, ProfileContract.DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProfileContract.Profiles.CREATE_TABLE);
        db.execSQL(ProfileContract.Letters.CREATE_TABLE);
        db.execSQL(ProfileContract.Words.CREATE_TABLE);
        db.execSQL(ProfileContract.ProfileWritingProgress.CREATE_TABLE);
        db.execSQL(ProfileContract.ProfileReadingProgress.CREATE_TABLE);
        addLetters(db);
        addWords(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ProfileContract.Profiles.DROP_TABLE);
        db.execSQL(ProfileContract.Letters.DROP_TABLE);
        db.execSQL(ProfileContract.Words.DROP_TABLE);
        db.execSQL(ProfileContract.ProfileWritingProgress.DROP_TABLE);
        db.execSQL(ProfileContract.ProfileReadingProgress.DROP_TABLE);
        onCreate(db);
    }

    public boolean addProfile(String profileName) {
        ContentValues profile = new ContentValues();
        profile.put(ProfileContract.Profiles.COL_NAME, profileName);

        this.getWritableDatabase().insert(ProfileContract.Profiles.TABLE_NAME, null, profile);

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {ProfileContract.Profiles.COL_PROFILE_ID};
        String sortOrder = ProfileContract.Profiles.COL_PROFILE_ID + " DESC";
        Cursor cursor = db.query(ProfileContract.Profiles.TABLE_NAME, projection,
                null, null, null, null, sortOrder, "1");
        int profileId = -1;
        if (cursor.moveToFirst()) {
            profileId = cursor.getInt(cursor.getColumnIndex(ProfileContract.Profiles.COL_PROFILE_ID));
        }

        if (profileId != -1) {
            addEmptyStatistics(profileId);
            return true;
        } else {
            return false;
        }
    }

    private void addEmptyStatistics(int profileId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> letters = getAllLetters();

        ContentValues values;
        for (String letter : letters) {
            values = new ContentValues();
            values.put(ProfileContract.ProfileWritingProgress.COL_TIMES_COMPLETED, 0);
            values.put(ProfileContract.ProfileWritingProgress.COL_COMPLETION_TIME, 0);
            values.put(ProfileContract.ProfileWritingProgress.COL_ACCURACY, 0);
            values.put(ProfileContract.ProfileWritingProgress.COL_FK_PROFILE_ID, profileId);
            values.put(ProfileContract.ProfileWritingProgress.COL_FK_LETTER, letter);
            db.insert(ProfileContract.ProfileWritingProgress.TABLE_NAME, null, values);
        }

        ArrayList<String> words = getAllWords();
        for (String word : words) {
            values = new ContentValues();
            values.put(ProfileContract.ProfileReadingProgress.COL_TIMES_COMPLETED, 0);
            values.put(ProfileContract.ProfileReadingProgress.COL_FK_PROFILE_ID, profileId);
            values.put(ProfileContract.ProfileReadingProgress.COL_FK_WORD, word);
            db.insert(ProfileContract.ProfileReadingProgress.TABLE_NAME, null, values);
        }
    }

    public void updateWritingProgress(String profileId, String letter, int timesCompleted, int completionTime, int accuracy) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProfileContract.ProfileWritingProgress.COL_TIMES_COMPLETED, timesCompleted);
        values.put(ProfileContract.ProfileWritingProgress.COL_COMPLETION_TIME, completionTime);
        values.put(ProfileContract.ProfileWritingProgress.COL_ACCURACY, accuracy);

        String selection = ProfileContract.ProfileWritingProgress.COL_FK_PROFILE_ID +
                "=?" +
                " AND " + ProfileContract.ProfileWritingProgress.COL_FK_LETTER +
                "=?";
        String[] selectionArgs = {String.valueOf(profileId), letter};

        db.update(ProfileContract.ProfileWritingProgress.TABLE_NAME, values, selection, selectionArgs);
    }

    public void updateReadingProgress(String profileId, String word, int timesCompleted) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProfileContract.ProfileReadingProgress.COL_TIMES_COMPLETED, timesCompleted);

        String selection = ProfileContract.ProfileReadingProgress.COL_FK_PROFILE_ID +
                "=?" +
                " AND " + ProfileContract.ProfileReadingProgress.COL_FK_WORD +
                "=?";
        String[] selectionArgs = {String.valueOf(profileId), word};

        db.update(ProfileContract.ProfileReadingProgress.TABLE_NAME, values, selection, selectionArgs);
    }

    public int getTimesCompleted(String profileId, String letter) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {ProfileContract.ProfileWritingProgress.COL_TIMES_COMPLETED};
        String selection = ProfileContract.ProfileWritingProgress.COL_FK_PROFILE_ID +
                "=?" +
                " AND " + ProfileContract.ProfileWritingProgress.COL_FK_LETTER +
                "=?";
        String[] selectionArgs = {String.valueOf(profileId), letter};

        String timesCompleted = "";
        Cursor cursor = db.query(ProfileContract.ProfileWritingProgress.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            timesCompleted = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileWritingProgress.COL_TIMES_COMPLETED));
        }
        return Integer.parseInt(timesCompleted);
    }

    public int getTimesCompletedWords(int profileId, String word) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {ProfileContract.ProfileReadingProgress.COL_TIMES_COMPLETED};
        String selection = ProfileContract.ProfileReadingProgress.COL_FK_PROFILE_ID +
                "=?" +
                " AND " + ProfileContract.ProfileReadingProgress.COL_FK_WORD +
                "=?";
        String[] selectionArgs = {String.valueOf(profileId), word};

        String timesCompleted = "";
        Cursor cursor = db.query(ProfileContract.ProfileReadingProgress.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            timesCompleted = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileReadingProgress.COL_TIMES_COMPLETED));
        }
        return Integer.parseInt(timesCompleted);
    }

    public int getCompletionTime(String profileId, String letter) {
        SQLiteDatabase db = this.getReadableDatabase();
        String projection[] = {ProfileContract.ProfileWritingProgress.COL_COMPLETION_TIME};
        String selection = ProfileContract.ProfileWritingProgress.COL_FK_PROFILE_ID +
                "=?" +
                " AND " + ProfileContract.ProfileWritingProgress.COL_FK_LETTER +
                "=?";
        String selectionArgs[] = {String.valueOf(profileId), letter};
        String completionTime = "";

        Cursor cursor = db.query(ProfileContract.ProfileWritingProgress.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null, null, null);

        if (cursor.moveToFirst()) {
            completionTime = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileWritingProgress.COL_COMPLETION_TIME));
        }
        return Integer.parseInt(completionTime);
    }

    public long getNumberOfCompletedLetters(String profileId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = ProfileContract.ProfileWritingProgress.COL_FK_PROFILE_ID +
                "=?" +
                " AND " + ProfileContract.ProfileWritingProgress.COL_TIMES_COMPLETED + " != 0";
        String[] selectionArgs = {profileId};
        long numCompletedLetters = DatabaseUtils.queryNumEntries(db, ProfileContract.ProfileWritingProgress.TABLE_NAME,
                selection, selectionArgs);
        return numCompletedLetters;
    }

    public long getNumberOfCompletedWords(String profileId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = ProfileContract.ProfileReadingProgress.COL_FK_PROFILE_ID +
                "=?" +
                " AND " + ProfileContract.ProfileReadingProgress.COL_TIMES_COMPLETED + " != 0";
        String[] selectionArgs = {profileId};
        long numCompletedWords = DatabaseUtils.queryNumEntries(db, ProfileContract.ProfileReadingProgress.TABLE_NAME,
                selection, selectionArgs);
        return numCompletedWords;
    }

    public ArrayList<LetterProgress> getWritingProgress(String profileId) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = ProfileContract.ProfileWritingProgress.COL_FK_PROFILE_ID + "=?";
        String[] selectionArgs = {profileId};

        Cursor cursor = db.query(ProfileContract.ProfileWritingProgress.TABLE_NAME, null,
                selection, selectionArgs, null, null, null);
        ArrayList<LetterProgress> writingProgress = new ArrayList<>();

        while (cursor.moveToNext()) {
            String letter = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileWritingProgress.COL_FK_LETTER));
            String timesCompleted = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileWritingProgress.COL_TIMES_COMPLETED));
            String completionTime = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileWritingProgress.COL_COMPLETION_TIME));
            String accuracy = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileWritingProgress.COL_ACCURACY));
            writingProgress.add(new LetterProgress(letter, timesCompleted, completionTime, accuracy));
        }
        return writingProgress;
    }

    public ArrayList<WordProgress> getReadingProgress(String profileId) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = ProfileContract.ProfileReadingProgress.COL_FK_PROFILE_ID + "=?";
        String[] selectionArgs = {profileId};

        Cursor cursor = db.query(ProfileContract.ProfileReadingProgress.TABLE_NAME, null,
                selection, selectionArgs, null, null, null);
        ArrayList<WordProgress> readingProgress = new ArrayList<>();

        while (cursor.moveToNext()) {
            String letter = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileReadingProgress.COL_FK_WORD));
            String timesCompleted = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileReadingProgress.COL_TIMES_COMPLETED));
            readingProgress.add(new WordProgress(letter, timesCompleted));
        }
        return readingProgress;
    }


    public String getProfile(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {ProfileContract.Profiles.COL_NAME};
        String selection = ProfileContract.Profiles.COL_PROFILE_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor cursor = db.query(ProfileContract.Profiles.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,null,null, "1");

        String name = "";
        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex(ProfileContract.Profiles.COL_NAME));
        }
        return name;
    }

    public ArrayList<String> getAllProfiles() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {ProfileContract.Profiles.COL_NAME};
        Cursor cursor = db.query(ProfileContract.Profiles.TABLE_NAME, projection,
                null, null, null, null, null);

        ArrayList<String> profiles = new ArrayList<>();
        while (cursor.moveToNext()) {
            profiles.add(cursor.getString(cursor.getColumnIndex(ProfileContract.Profiles.COL_NAME)));
        }
        cursor.close();
        return profiles;
    }

    private void addLetters(SQLiteDatabase db) {
        ContentValues values;
        String[] letters = mContext.getResources().getStringArray(R.array.letters);

        for (String letter : letters) {
            values = new ContentValues();
            values.put(ProfileContract.Letters.COL_LETTER, letter);

            db.insert(ProfileContract.Letters.TABLE_NAME, null, values);
        }
    }

    public ArrayList<String> getAllLetters() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {ProfileContract.Letters.COL_LETTER};
        Cursor cursor = db.query(ProfileContract.Letters.TABLE_NAME, projection,
                null, null, null, null, null);

        ArrayList<String> letters = new ArrayList<>();
        while (cursor.moveToNext()) {
            letters.add(cursor.getString(cursor.getColumnIndex(ProfileContract.Letters.COL_LETTER)));
        }
        cursor.close();
        return letters;
    }

    private void addWords(SQLiteDatabase db) {
        ContentValues values;
        String[] words = mContext.getResources().getStringArray(R.array.words);

        for (String word : words) {
            values = new ContentValues();
            values.put(ProfileContract.Words.COL_WORD, word);

            db.insert(ProfileContract.Words.TABLE_NAME, null, values);
        }
    }

    public ArrayList<String> getAllWords() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {ProfileContract.Words.COL_WORD};
        Cursor cursor = db.query(ProfileContract.Words.TABLE_NAME, projection,
                null, null, null, null, null);

        ArrayList<String> words = new ArrayList<>();
        while (cursor.moveToNext()) {
            words.add(cursor.getString(cursor.getColumnIndex(ProfileContract.Words.COL_WORD)));
        }
        cursor.close();
        return words;
    }
}
