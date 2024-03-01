package com.example.study;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.study.DB.AcademicInformationModel;

public class Register extends AppCompatActivity {
    private ImageView addd;
    private EditText addid,addname,dept,acdy;
    TextView tst;
    private Spinner addcat;
    private Button addbutton;
    private final int eq=1;
    private Bitmap bitmap=null;
    private String categ;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        i = new Intent(this, MainActivity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addd = findViewById(R.id.addd);
        addid = findViewById(R.id.addid);
        addname = findViewById(R.id.addname);
        dept = findViewById(R.id.dept);
        acdy = findViewById(R.id.acdy);
        addcat = findViewById(R.id.addcat);
        addbutton = findViewById(R.id.addbutton);
        tst=findViewById(R.id.tst);
        AcademicInformationModel acadamicdb = new AcademicInformationModel(this);

        String[] data = new String[]{"Select level", "First", "Second", "Third", "Fourth"};

        //System.out.println(addcat);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        addcat.setAdapter(adapter);
        addcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categ = (String) parent.getItemAtPosition(position);
                // Do something with the selected item
               // tst.setText(categ);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do something when no item is selected
            }
        });
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int setcat=0;
                String id =addid.getText().toString();
                String name =addname.getText().toString();
                String depart =dept.getText().toString();
                String acadmic =acdy.getText().toString();
                int acad=0;
                if(id.length()==0 || name.length()==0 || depart.length()==0||acadmic.length()==0 || categ.equals("Select level")){
                    Toast.makeText(Register.this,"pleas complete your registeration",Toast.LENGTH_SHORT);

                }else {
                    if(categ.equals("First"))
                        setcat=1;
                    else if(categ.equals("Second"))
                        setcat=2;
                    else if(categ.equals("Third"))
                        setcat=3;
                    else if(categ.equals("Fourth"))
                        setcat=4;
                    try{
                        acad=Integer.parseInt(acadmic);
                    }catch (Exception e){
                        Toast.makeText(Register.this,"acadmic year must be numeric",Toast.LENGTH_SHORT);
                    }
                    Boolean check= acadamicdb.inset(id,name,depart,acad, setcat);
                    if(check==true) {
                        Toast.makeText(Register.this,"Donee",Toast.LENGTH_SHORT);
                        startActivity(i);
                    }
                    else
                        Toast.makeText(Register.this,"Feild",Toast.LENGTH_SHORT);
                }
            }
        });
    }


}