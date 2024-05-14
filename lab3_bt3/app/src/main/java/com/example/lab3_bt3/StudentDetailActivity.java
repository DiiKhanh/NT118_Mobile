package com.example.lab3_bt3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        // Get student data from intent
        Intent intent = getIntent();
        student student = (student) intent.getSerializableExtra("student");

        // Set student data to views
        TextView textViewId = findViewById(R.id.textViewId);
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewClass = findViewById(R.id.textViewClass);

        textViewId.setText("MSSV: "+student.getId());
        textViewName.setText("Họ tên: "+student.getName());
        textViewClass.setText("Lớp: "+student.getClassName());
    }
}