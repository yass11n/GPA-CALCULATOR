package com.example.study;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.study.DB.AcademicInformationModel;

public class UpdateForm extends AppCompatActivity {
    TextView t1,t2,t3,t4;
    Button b;
    Spinner s;
    AcademicInformationModel ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_form);
        t1=findViewById(R.id.STname);
        t2=findViewById(R.id.idid);
        t3=findViewById(R.id.dep);
        t4=findViewById(R.id.acd);
        s =findViewById(R.id.Lev);
        b= findViewById(R.id.button1);

        String Name1=getIntent().getStringExtra("key1");
        String ID1=getIntent().getStringExtra("key2");
        String DEP1=getIntent().getStringExtra("key3");
        String AcademicLevel =getIntent().getStringExtra("key4");
        String Lev1=getIntent().getStringExtra("key5");

        t1.setText(Name1);
        t2.setText(ID1);
        t3.setText(DEP1);
        t4.setText(AcademicLevel);
        s.setSelection(Integer.parseInt(Lev1) - 1);


        ac = new AcademicInformationModel(this);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = t1.getText().toString();
                String id = t2.getText().toString();
                String dept = t3.getText().toString();
                int academicYear = Integer.parseInt(t4.getText().toString());
                int level = Integer.parseInt(s.getSelectedItem().toString());

                ac.update(id, name, dept, academicYear, level);

                Intent intent = new Intent(UpdateForm.this, MainActivity.class);
                intent.putExtra("FRAGMENT_TO_LOAD", "profile");
                startActivity(intent);
                finish();
            }
        });
    }

}

