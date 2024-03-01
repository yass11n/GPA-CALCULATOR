package com.example.study;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.study.DB.AcademicInformationModel;
import com.example.study.DB.StudentCoursesModel;

public class ProfileFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    TextView name , id, dept, Ayear, level;
    AcademicInformationModel ac;
    Button btn;
    ImageView img5,img3,img4;
    WebView webview;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.text2);
        id = view.findViewById(R.id.text3);
        dept = view.findViewById(R.id.student_dept);
        Ayear = view.findViewById(R.id.student_acdemicYear);
        level = view.findViewById(R.id.student_level);
        btn= (Button)view.findViewById(R.id.button2);
        ac = new AcademicInformationModel(view.getContext());
        img5 = (ImageView) view.findViewById(R.id.img5);
        img3 = (ImageView) view.findViewById(R.id.img3);
        img4 = (ImageView) view.findViewById(R.id.img4);
        webview =(WebView) view.findViewById(R.id.webview);

        Cursor student = ac.getAll();

        while (student.moveToNext()){
            id.setText(student.getString(1));
            name.setText(student.getString(2));
            dept.setText(student.getString(3));
            Ayear.setText(""+student.getInt(4));
            level.setText(""+student.getInt(5));
        }
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(),PdfActivity.class);
                startActivity(intent);
            }
        });
        // ده عشان يرن علي حد
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01121258103"));
                startActivity(intent);
            }
        });
        //ده عشان يخش علي صفحه الاخبار بتاعت علوم
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity().getApplicationContext(),webView.class);
                intent.putExtra("LINK","https://science.asu.edu.eg/ar/news");
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String Name=name.getText().toString().trim();
                String ID=id.getText().toString().trim();
                String DEP=dept.getText().toString().trim();
                String ayear = Ayear.getText().toString().trim();
                String Lev=level.getText().toString().trim();
                Intent intent = new Intent(getActivity().getApplicationContext() , UpdateForm.class);
                intent.putExtra("key1",Name);
                intent.putExtra("key2",ID);
                intent.putExtra("key3",DEP);
                intent.putExtra("key4",ayear);
                intent.putExtra("key5",Lev);
                startActivity(intent);
            }
        });

    }


}