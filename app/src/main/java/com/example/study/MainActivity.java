package com.example.study;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.study.DB.AcademicInformationModel;
import com.example.study.DB.StudentCoursesModel;
import com.example.study.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AcademicInformationModel DB = new AcademicInformationModel(this);
        Cursor c = DB.getAll();

        if(c.getCount() > 0) {
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("FRAGMENT_TO_LOAD")) {
                String fragmentToLoad = intent.getStringExtra("FRAGMENT_TO_LOAD");
                if (fragmentToLoad.equals("profile")) {
                    replaceFragment(new ProfileFragment());
                } else {
                    replaceFragment(new HomeFragment());
                }
            } else {
                replaceFragment(new HomeFragment());
            }

            binding.bottomNavigationView.setBackground(null);
            binding.bottomNavigationView.setOnItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.home:
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.profile:
                        replaceFragment(new ProfileFragment());
                        break;
                    case R.id.calc:
                        replaceFragment(new CalcFragment());
                        break;
                    case R.id.status:
                        replaceFragment(new StatusFragment());
                        break;
                }
                return true;
            });

            binding.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showBottomDialog();
                }
            });
        }else{
            Intent i = new Intent(this, Register.class);
            startActivity(i);
        }

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout);

        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        TextView msg = dialog.findViewById(R.id.message);
        TextView semester = dialog.findViewById(R.id.semester);
        EditText courseCode = dialog.findViewById(R.id.courseCode);
        EditText courseName = dialog.findViewById(R.id.courseName);
        EditText courseHoures = dialog.findViewById(R.id.courseHoures);
        EditText courseYear = dialog.findViewById(R.id.courseYear);
        Spinner courseGPA = dialog.findViewById(R.id.courseGPA);

        Button addCourse = dialog.findViewById(R.id.addCourse);
        Button updateCourse = dialog.findViewById(R.id.updateCourse);

         Button semester1, semester2, summerCourse;
         FrameLayout formLayout;

        semester1 = dialog.findViewById(R.id.semester1);
        semester2 = dialog.findViewById(R.id.semester2);
        summerCourse = dialog.findViewById(R.id.summerCourse);

        formLayout = dialog.findViewById(R.id.form_layout);


        semester1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semester.setText("FALL");

                if(formLayout.getVisibility() == View.GONE)
                    formLayout.setVisibility(View.VISIBLE);

                semester1.setVisibility(View.GONE);
                semester2.setVisibility(View.GONE);
                summerCourse.setVisibility(View.GONE);

            }
        });

        semester2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semester.setText("SPRING");

                if(formLayout.getVisibility() == View.GONE)
                    formLayout.setVisibility(View.VISIBLE);

                semester1.setVisibility(View.GONE);
                semester2.setVisibility(View.GONE);
                summerCourse.setVisibility(View.GONE);

            }
        });

        summerCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semester.setText("SUMMER");

                if(formLayout.getVisibility() == View.GONE)
                    formLayout.setVisibility(View.VISIBLE);

                semester1.setVisibility(View.GONE);
                semester2.setVisibility(View.GONE);
                summerCourse.setVisibility(View.GONE);
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        StudentCoursesModel sc = new StudentCoursesModel(MainActivity.this);

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = courseCode.getText().toString();
                String name = courseName.getText().toString();
                String houres = courseHoures.getText().toString();
                String year = courseYear.getText().toString();
                String gpa = courseGPA.getSelectedItem().toString();

                float courseGpa = -1.0F;

                if(gpa.equals("A")) courseGpa = 4.0F;
                else if(gpa.equals("A-")) courseGpa = 3.67F;
                else if(gpa.equals("B+")) courseGpa = 3.33F;
                else if(gpa.equals("B")) courseGpa = 3.0F;
                else if(gpa.equals("C+")) courseGpa = 2.67F;
                else if(gpa.equals("C")) courseGpa = 2.33F;
                else if(gpa.equals("D")) courseGpa = 2.0F;
                else if(gpa.equals("F")) courseGpa = 0.0F;

                if(code.length() == 0 || name.length() == 0 || houres.length() == 0 || year.length() == 0 || courseGpa == -1.0F){
                    msg.setTextColor(Color.RED);
                    msg.setText("Please Coumplete all info");
                }else{
                    Boolean flag = sc.insetCourse(code, name, Integer.parseInt(houres), courseGpa,semester.getText().toString(), Integer.parseInt(year));
                    if(flag){
                        msg.setTextColor(Color.GREEN);
                        msg.setText("course added");
                    }else{
                        msg.setTextColor(Color.RED);
                        msg.setText("course already exist");
                    }

                }

            }
        });

        updateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = courseCode.getText().toString();
                String name = courseName.getText().toString();
                String houres = courseHoures.getText().toString();
                String year = courseYear.getText().toString();
                String gpa = courseGPA.getSelectedItem().toString();
                float courseGpa = -1.0F;

                if(gpa.equals("A")) courseGpa = 4.0F;
                else if(gpa.equals("A-")) courseGpa = 3.67F;
                else if(gpa.equals("B+")) courseGpa = 3.33F;
                else if(gpa.equals("B")) courseGpa = 3.0F;
                else if(gpa.equals("C+")) courseGpa = 2.67F;
                else if(gpa.equals("C")) courseGpa = 2.33F;
                else if(gpa.equals("D")) courseGpa = 2.0F;
                else if(gpa.equals("F")) courseGpa = 0.0F;

                if(code.length() == 0 || name.length() == 0 || houres.length() == 0 || year.length() == 0 || courseGpa == -1.0F){
                    msg.setTextColor(Color.RED);
                    msg.setText("Please Coumplete all info");
                }else{
                    Boolean flag = sc.updateCourse(code, name, Integer.parseInt(houres), courseGpa,semester.getText().toString(), Integer.parseInt(year));
                    if(flag){
                        msg.setTextColor(Color.GREEN);
                        msg.setText("course updated");
                    }else{
                        msg.setTextColor(Color.RED);
                        msg.setText("course don't exist");
                    }

                }
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}