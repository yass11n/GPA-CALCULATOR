package com.example.study;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.study.DB.StudentCoursesModel;

public class CalcFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView progressText;
    private Button calc;
    int i = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        progressText = view.findViewById(R.id.progress_text);
        final Handler handler = new Handler();

        // نجيب كل الكورسات من الداتا بيز و نحسب الgpa
        StudentCoursesModel sc = new StudentCoursesModel(view.getContext());

        double finalGpa = sc.getGpa();

        int index = 0;
        if(finalGpa  < 2) index = 0;
        else if(finalGpa >=2 && finalGpa < 2.33) index = 60;
        else if(finalGpa >= 2.33 && finalGpa < 2.67) index = 65;
        else if(finalGpa >= 2.67 && finalGpa < 3) index = 70;
        else if(finalGpa >= 3 && finalGpa < 3.33) index = 75;
        else if(finalGpa >= 3.33 && finalGpa < 3.67) index = 80;
        else if(finalGpa >= 3.67 && finalGpa < 4) index = 85;
        else if(finalGpa == 4 ) index = 100;

        int finalIndex = index;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(i<=100){
                    if(finalGpa == -1) progressText.setText( "No courses");

                    else progressText.setText( String.format("%.3f", finalGpa));


                    if( i <= finalIndex)
                        progressBar.setProgress(i);

                    if(i == finalIndex)
                        handler.removeCallbacks(this);

                    i++;
                    handler.postDelayed(this,50);
                }
                else{
                    handler.removeCallbacks(this);
                }
            }
        },50);

        calc = view.findViewById(R.id.calcGpa);

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), CalcGPA.class);
                i.putExtra("GPA", finalGpa);
                startActivity(i);
            }
        });

    }
}