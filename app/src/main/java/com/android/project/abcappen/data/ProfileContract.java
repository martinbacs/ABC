package com.android.project.abcappen.data;

import android.provider.BaseColumns;

public final class ProfileContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Profile.db";

    private ProfileContract() {
    }

    public static abstract class Profiles implements BaseColumns {
        public static final String TABLE_NAME = "profiles";
        public static final String COL_PROFILE_ID = "profile_id";
        public static final String COL_NAME = "name";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + Profiles.TABLE_NAME + "(" +
                        Profiles.COL_PROFILE_ID + " INTEGER PRIMARY KEY," +
                        Profiles.COL_NAME + " TEXT)";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + Profiles.TABLE_NAME;
    }

    public static abstract class Letters implements BaseColumns {
        public static final String TABLE_NAME = "letters";
        public static final String COL_LETTER = "letter";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + Letters.TABLE_NAME + "(" +
                        Letters.COL_LETTER + " TEXT PRIMARY KEY)";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + Letters.TABLE_NAME;
    }

    public static abstract class Words implements BaseColumns {
        public static final String TABLE_NAME = "words";
        public static final String COL_WORD = "word";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + Words.TABLE_NAME + "(" +
                        Words.COL_WORD + " TEXT PRIMARY KEY)";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + Words.TABLE_NAME;
    }

    public static abstract class ProfileWritingProgress implements BaseColumns {
        public static final String TABLE_NAME = "profile_writing_progress";
        public static final String COL_TIMES_COMPLETED = "times_completed";
        public static final String COL_COMPLETION_TIME = "completion_time";
        public static final String COL_ACCURACY = "accuracy";
        public static final String COL_FK_PROFILE_ID = "fk_profile";
        public static final String COL_FK_LETTER = "fk_letter";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + ProfileWritingProgress.TABLE_NAME + "(" +
                        ProfileWritingProgress.COL_TIMES_COMPLETED + " INTEGER, " +
                        ProfileWritingProgress.COL_COMPLETION_TIME + " INTEGER, " +
                        ProfileWritingProgress.COL_ACCURACY + " INTEGER, " +
                        ProfileWritingProgress.COL_FK_PROFILE_ID + " INTEGER, " +
                        ProfileWritingProgress.COL_FK_LETTER + " TEXT, " +
                        "FOREIGN KEY(" + ProfileWritingProgress.COL_FK_PROFILE_ID +
                        ") REFERENCES " + Profiles.TABLE_NAME + "(" + Profiles.COL_PROFILE_ID + ")," +
                        "FOREIGN KEY(" + ProfileWritingProgress.COL_FK_LETTER +
                        ") REFERENCES " + Letters.TABLE_NAME + "(" + Letters.COL_LETTER + "))";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + ProfileWritingProgress.TABLE_NAME;
    }


    public static abstract class ProfileReadingProgress implements BaseColumns {
        public static final String TABLE_NAME = "profile_reading_progress";
        public static final String COL_TIMES_COMPLETED = "times_completed";
        public static final String COL_FK_PROFILE_ID = "fk_profile_id";
        public static final String COL_FK_WORD = "fk_word";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + ProfileReadingProgress.TABLE_NAME + "(" +
                        ProfileReadingProgress.COL_TIMES_COMPLETED + " INTEGER, " +
                        ProfileReadingProgress.COL_FK_PROFILE_ID + " INTEGER, " +
                        ProfileReadingProgress.COL_FK_WORD + " TEXT, " +
                        "FOREIGN KEY(" + ProfileReadingProgress.COL_FK_PROFILE_ID +
                        ") REFERENCES " + Profiles.TABLE_NAME + "(" + Profiles.COL_PROFILE_ID + "), " +
                        "FOREIGN KEY(" + ProfileReadingProgress.COL_FK_WORD +
                        ") REFERENCES " + Words.TABLE_NAME + "(" + Words.COL_WORD + "))";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + ProfileReadingProgress.TABLE_NAME;
    }

}