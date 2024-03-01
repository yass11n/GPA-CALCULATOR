package com.example.study;

import android.annotation.SuppressLint;
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
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.study.DB.StudentCoursesModel;

import org.w3c.dom.Text;

public class StatusFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false);
    }

    Spinner syear;
    StudentCoursesModel s;

    TextView semesterYear;
    LinearLayout parent;
    Button semester1, semester2, summer;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        syear = view.findViewById(R.id.StatsYear);
        s = new StudentCoursesModel(view.getContext());

        Integer[] data = s.getYearCourses();

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item, data);
        syear.setAdapter(adapter);

        semester1 = view.findViewById(R.id.semester1);
        semester2 = view.findViewById(R.id.semester2);
        summer = view.findViewById(R.id.summerCourse);

        semesterYear = view.findViewById(R.id.semester_year);

        semester1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer y = (Integer) syear.getSelectedItem();
                appendCourse(view , y, "FALL");
                semester1.setVisibility(View.GONE);
                semester2.setVisibility(View.GONE);
                summer.setVisibility(View.GONE);
                syear.setVisibility(View.GONE);
                parent.setVisibility(View.VISIBLE);

                semesterYear.setText("FALL | " + y);

            }
        });

        semester2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer y = (Integer) syear.getSelectedItem();
                appendCourse(view , y, "SPRING");
                semester1.setVisibility(View.GONE);
                semester2.setVisibility(View.GONE);
                summer.setVisibility(View.GONE);
                syear.setVisibility(View.GONE);

                parent.setVisibility(View.VISIBLE);

                semesterYear.setText("SPRING | " + y);
            }
        });

        summer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer y = (Integer) syear.getSelectedItem();
                appendCourse(view , y, "SUMMER");
                semester1.setVisibility(View.GONE);
                semester2.setVisibility(View.GONE);
                summer.setVisibility(View.GONE);
                syear.setVisibility(View.GONE);

                parent.setVisibility(View.VISIBLE);
                semesterYear.setText("SUMMER | " + y);
            }
        });


    }

    void appendCourse(View v , int year, String semester){

        Cursor courses = s.getSemesterCourses(year, semester);

        parent = v.findViewById(R.id.courses_layout);
        LayoutInflater inflater = getLayoutInflater();


        while (courses.moveToNext()){
            View childLayout = inflater.inflate(R.layout.course_info, parent, false);

            Button remove = childLayout.findViewById(R.id.remove_box);

            String code = courses.getString(1);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    s.deleteCourse(code);
                    parent.removeView(childLayout);
                }
            });

            //course code
            //@SuppressLint({"MissingInflatedId", "LocalSuppress"})
            TextView courseCode = childLayout.findViewById(R.id.course_code);
            courseCode.setId(View.generateViewId());
            courseCode.setText(code);

            //course name
            //@SuppressLint({"MissingInflatedId", "LocalSuppress"})
            TextView courseName = childLayout.findViewById(R.id.course_name);
            courseName.setId(View.generateViewId());
            courseName.setText(courses.getString(2));

            //course hours
           // @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            TextView creaditHours = childLayout.findViewById(R.id.course_hours);
            creaditHours.setId(View.generateViewId());
            creaditHours.setText("" + courses.getInt(3));

            // course grade
           // @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            TextView grade = childLayout.findViewById(R.id.course_grade);
            grade.setId(View.generateViewId());
            grade.setText("" + courses.getFloat(4));



            parent.addView(childLayout);
        }

    }
}