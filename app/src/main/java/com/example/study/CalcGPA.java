package com.example.study;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.study.DB.StudentCoursesModel;

public class CalcGPA extends AppCompatActivity {

    LinearLayout parent;
    Button addCourse, calcGpa;
    TextView gpaView, totalGpaView, creditHoursView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_gpa);
        parent = findViewById(R.id.parent_layout);
        addCourse = findViewById(R.id.add_course);
        calcGpa = findViewById(R.id.calc_gpa);
        totalGpaView = findViewById(R.id.totalGpa);
        gpaView = findViewById(R.id.gpa);
        creditHoursView = findViewById(R.id.credit_hours);

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get a reference to the layout inflater
                LayoutInflater inflater = LayoutInflater.from(CalcGPA.this);
                // Inflate the layout from a file
                View childView = inflater.inflate(R.layout.course_form, parent, false);

                // Add the inflated layout as a child to the parent view
                parent.addView(childView);

                Button remove = childView.findViewById(R.id.remove_box);

                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        parent.removeView(childView);
                    }
                });
            }
        });

        double totalGpa = getIntent().getDoubleExtra("GPA" , 0);

        totalGpaView.setText(String.format("%.3f", totalGpa));
        calcGpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcGpa();
            }
        });

    }

    void calcGpa(){
        double gpa = 0;
        int cretidHours = 0;
        StudentCoursesModel sc = new StudentCoursesModel(CalcGPA.this);
        int totalCH = sc.getTotalCreditHoures();
        //if no child
        if(parent.getChildCount() == 0) {
            Toast.makeText(CalcGPA.this, "Please add courses first", Toast.LENGTH_SHORT);
            return;
        }

        for(int i = 0; i < parent.getChildCount(); i++){
            View child = parent.getChildAt(i);
            Spinner ch = child.findViewById(R.id.spinner_credit_hours);
            Spinner grad = child.findViewById(R.id.spinner_course_grade);

            if(ch.getSelectedItem().toString().equals("Credit Hours") ||
                    grad.getSelectedItem().toString().equals("Course GPA")){
                Toast.makeText(CalcGPA.this, "Please complete all values", Toast.LENGTH_SHORT);
                return;
            }
            cretidHours += Integer.parseInt(ch.getSelectedItem().toString());
            gpa += Integer.parseInt(ch.getSelectedItem().toString()) * getGrade(grad.getSelectedItem().toString());


        }


        //set credit hours to the view
        creditHoursView.setText(""+cretidHours);

        //set gpa to the view
        gpaView.setText(String.format("%.3f", gpa/cretidHours));

        //set the total gpa to the view
        double totalGpa = Double.parseDouble(totalGpaView.getText().toString());
        totalGpa = (totalGpa * totalCH + gpa) / (totalCH + cretidHours);
        totalGpaView.setText(String.format("%.3f", totalGpa));

    }

    double getGrade(String x){
        if(x.equals("A")) return 4.0;
        else if(x.equals("A-")) return 3.67;
        else if(x.equals("B+")) return 3.33;
        else if(x.equals("B")) return 3.00;
        else if(x.equals("C+")) return 2.67;
        else if(x.equals("C")) return 2.33;
        else if(x.equals("D")) return 2;
        else return 0.0;
    }
}