package com.example.study.DB;
import static com.example.study.DB.University.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class StudentCoursesModel extends UniversityDBHelper {
    public StudentCoursesModel(@Nullable Context context) {
        super(context);
    }

    //insert course data function
    public Boolean insetCourse(String CID, String Cname, int CH, float CG, String CS, int CY) {
        SQLiteDatabase DB = this.getWritableDatabase();

        ContentValues course = new ContentValues();
        course.put(StudentCourses.COURSE_ID, CID);
        course.put(StudentCourses.NAME, Cname);
        course.put(StudentCourses.CREDIT_HOURS, CH);
        course.put(StudentCourses.COURSE_GRADE, CG);
        course.put(StudentCourses.COURSE_SEMESTER, CS);
        course.put(StudentCourses.COURSE_YEAR, CY);

        long res = DB.insert(StudentCourses.TABLE_NAME, null, course);
        if(res == -1) return false;
        return true;
    }

    //update course data function
    public Boolean updateCourse(String CID , String Cname, int CH, float CG, String CS, int CY) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues course = new ContentValues();

        course.put(StudentCourses.NAME , Cname);
        course.put(StudentCourses.CREDIT_HOURS, CH);
        course.put(StudentCourses.COURSE_GRADE, CG);
        course.put(StudentCourses.COURSE_SEMESTER, CS);
        course.put(StudentCourses.COURSE_YEAR, CY);

        Cursor cursor = DB.rawQuery("select * from " + StudentCourses.TABLE_NAME +" where " + StudentCourses.COURSE_ID + " =?", new String[]{CID} );

        if(cursor.getCount() > 0){
            long res = DB.update(StudentCourses.TABLE_NAME, course, StudentCourses.COURSE_ID+ "=?", new String[]{CID});
            if(res == -1) return false;
            return true;
        }else {
            return false;
        }

    }

    //delete course data function
    public Boolean deleteCourse(String CID) {
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("select * from " + StudentCourses.TABLE_NAME +" where " + StudentCourses.COURSE_ID + " =?", new String[]{CID} );
        if(cursor.getCount() > 0){
            long res = DB.delete(StudentCourses.TABLE_NAME, StudentCourses.COURSE_ID+ "=?", new String[]{CID});
            if(res == -1) return false;
            return true;
        }else {
            return false;
        }

    }

    public double getGpa(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from " + StudentCourses.TABLE_NAME , null );

        double gpa = 0;
        int houres = 0;

        while(cursor.moveToNext()){
            float courseGrade = cursor.getFloat(4);
            int courseHoures = cursor.getInt(3);

            gpa += courseHoures * courseGrade;
            houres += courseHoures;
        }

        if(houres != 0){
            gpa = gpa/houres;
        }else {
            gpa = -1;
        }


        return gpa;
    }

    public int getTotalCreditHoures(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from " + StudentCourses.TABLE_NAME , null );

        int houres = 0;

        while(cursor.moveToNext()){
            int courseHoures = cursor.getInt(3);

            houres += courseHoures;
        }

        return houres;
    }

    public Integer[] getYearCourses(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select DISTINCT " + StudentCourses.COURSE_YEAR + " from " + StudentCourses.TABLE_NAME, null );
        Integer []years = new Integer[cursor.getCount()];
        int i = 0;
        while(cursor.moveToNext()){
            years[i] = cursor.getInt(0);
            i++;
        }

        return years;
    }

    public Cursor getSemesterCourses(int year , String semester ){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from " + StudentCourses.TABLE_NAME + " where " +
                StudentCourses.COURSE_YEAR + " = ? and " + StudentCourses.COURSE_SEMESTER + " =?",
                new String[]{""+year, semester} );

        return cursor;
    }

    public  Cursor getLastSemesterCourse(){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM " + StudentCourses.TABLE_NAME +" WHERE " +
                StudentCourses.COURSE_YEAR + " = ( select  MAX(" + StudentCourses.COURSE_YEAR + ") from "+ StudentCourses.TABLE_NAME +
                " ) ORDER BY " + StudentCourses.COURSE_SEMESTER + " DESC LIMIT 1", null );


        return cursor;
    }
}
