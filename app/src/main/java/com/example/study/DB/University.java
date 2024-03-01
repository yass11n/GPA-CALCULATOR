package com.example.study.DB;

import android.provider.BaseColumns;

public class University {
    public University(){}

    public static final class AcademicInformation implements BaseColumns {
        public final static String TABLE_NAME = "AcademicInformation";
        public final static String _ID = BaseColumns._ID;
        public final static String ACADEMIC_ID = "Academic_Id";
        public final static String NAME = "Student_Name";
        public final static String DEPT = "Student_Department";
        public final static String ACADEMIC_YEAR = "Academic_Year";
        public final static String LEVEL = "Student_Level";
    }

    public static final class StudentCourses implements BaseColumns {
        public final static String TABLE_NAME = "StudentCourses";
        public final static String _ID = BaseColumns._ID;
        public final static String COURSE_ID = "Course_id";
        public final static String NAME = "Course_Name";
        public final static String CREDIT_HOURS = "Credit_Hours";
        public final static String COURSE_GRADE = "Course_Grade";
        public final static String COURSE_YEAR = "Course_year";
        public final static String COURSE_SEMESTER = "Course_Semester";

    }
}
