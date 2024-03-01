package com.example.study.DB;
import static com.example.study.DB.University.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class AcademicInformationModel extends UniversityDBHelper {
    public AcademicInformationModel(@Nullable Context context) {
        super(context);
    }

    //insert academic information function
    public Boolean inset(String AID, String name, String dept, int AY, int level) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues AI = new ContentValues();
        AI.put(AcademicInformation.ACADEMIC_ID, AID);
        AI.put(AcademicInformation.NAME , name);
        AI.put(AcademicInformation.DEPT, dept);
        AI.put(AcademicInformation.ACADEMIC_YEAR, AY);
        AI.put(AcademicInformation.LEVEL, level);

        long res = DB.insert(AcademicInformation.TABLE_NAME, null, AI);
        if(res == -1) return false;
        return true;
    }

    //update academic information function
    public Boolean update(String AID, String name, String dept, int AY, int level) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues AI = new ContentValues();
        AI.put(AcademicInformation.ACADEMIC_ID, AID);
        AI.put(AcademicInformation.NAME , name);
        AI.put(AcademicInformation.DEPT, dept);
        AI.put(AcademicInformation.ACADEMIC_YEAR, AY);
        AI.put(AcademicInformation.LEVEL, level);

        Cursor cursor = DB.rawQuery("select * from " + AcademicInformation.TABLE_NAME +" where " + AcademicInformation.ACADEMIC_ID + " =?", new String[]{AID} );

        if(cursor.getCount() > 0){
            long res = DB.update(AcademicInformation.TABLE_NAME, AI, AcademicInformation.ACADEMIC_ID+ "=?", new String[]{AID});
            if(res == -1) return false;
            return true;
        }else{
            return false;
        }
    }

    public Cursor getAll() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from " + AcademicInformation.TABLE_NAME , null);
        return cursor;
    }

}
