package com.example.study;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.study.DB.StudentCoursesModel;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    StudentCoursesModel s;

    TextView semesterYear;
    LinearLayout parent;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        s = new StudentCoursesModel(view.getContext());



        semesterYear = view.findViewById(R.id.semester_year);

        Cursor courses = s.getLastSemesterCourse();

        parent = view.findViewById(R.id.courses_layout);
        LayoutInflater inflater = getLayoutInflater();

        while (courses.moveToNext()){
            View childLayout = inflater.inflate(R.layout.home_courses, parent, false);

            //course code
            //@SuppressLint({"MissingInflatedId", "LocalSuppress"})
            TextView courseCode = childLayout.findViewById(R.id.course_code);
            courseCode.setId(View.generateViewId());
            courseCode.setText(courses.getString(1));
            System.out.println(courseCode.toString());
            //course name
            TextView courseName = childLayout.findViewById(R.id.course_name);
            courseName.setId(View.generateViewId());
            courseName.setText(courses.getString(2));

            //course hours
            TextView creaditHours = childLayout.findViewById(R.id.course_hours);
            creaditHours.setId(View.generateViewId());
            creaditHours.setText("" + courses.getInt(3));

            // course grade
            TextView grade = childLayout.findViewById(R.id.course_grade);
            grade.setId(View.generateViewId());
            grade.setText("" + courses.getFloat(4));

            semesterYear.setText(courses.getString(5) + " | " + courses.getInt(6));

            parent.addView(childLayout);
        }

    }

}