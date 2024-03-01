package com.example.study.DB;

import static com.example.study.DB.University.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UniversityDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "University.db";
    private static final int DB_VERSION = 1;

    public UniversityDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_AcademicInformation_TABLE = " CREATE TABLE " + AcademicInformation.TABLE_NAME + "(" +
                AcademicInformation._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                AcademicInformation.ACADEMIC_ID + " TEXT NOT NULL UNIQUE," +
                AcademicInformation.NAME + " TEXT ," +
                AcademicInformation.DEPT + " TEXT NOT NULL, " +
                AcademicInformation.ACADEMIC_YEAR + " INTEGER NOT NULL DEFAULT 0, " +
                AcademicInformation.LEVEL + " INTEGER NOT NULL CHECK(" + AcademicInformation.LEVEL + " IN (1, 2, 3, 4)) DEFAULT 1);";

        String CREATE_COURSES_TABLE = "CREATE TABLE " + StudentCourses.TABLE_NAME + "(" +
                StudentCourses._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                StudentCourses.COURSE_ID + " TEXT NOT NULL UNIQUE, " +
                StudentCourses.NAME + " TEXT NOT NULL, " +
                StudentCourses.CREDIT_HOURS + " INTEGER NOT NULL, " +
                StudentCourses.COURSE_GRADE + " FLOAT NOT NULL, "+
                StudentCourses.COURSE_SEMESTER + " TEXT NOT NULL CHECK(" + StudentCourses.COURSE_SEMESTER +" IN ('FALL', 'SPRING', 'SUMMER')) ," +
                StudentCourses.COURSE_YEAR + " INTEGER NOT NULL);";

        db.execSQL(CREATE_AcademicInformation_TABLE);
        db.execSQL(CREATE_COURSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AcademicInformation.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + StudentCourses.TABLE_NAME);
    }




}
