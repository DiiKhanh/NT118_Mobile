package com.example.lab3_nangcao;


import android.content.Context;

import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;
import android.widget.TextView;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Student> students;
    private EditText mssv, hoten, lop;
    public StudentAdapter(Context context, ArrayList<Student> students, EditText mssv, EditText hoten, EditText lop) {
        this.context = context;
        this.students = students;
        this.mssv = mssv;
        this.hoten = hoten;
        this.lop = lop;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View studentView = LayoutInflater.from(context).inflate(R.layout.item_student, null, false);
        ViewHolder viewHolder = new ViewHolder(studentView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Student student = students.get(position);

        TextView tvStudent = (TextView) holder.tvStudent;
        tvStudent.setText(student.getMSSV() + " - " + student.getHoTen() + " - " + student.getLop());
    }

    @Override
    public int getItemCount(){
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStudent;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvStudent = itemView.findViewById(R.id.tv_student);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] component = tvStudent.getText().toString().split(" - ");
                    mssv.setText(component[0]);
                    hoten.setText(component[1]);
                    lop.setText(component[2]);
                }
            });
        }

    }

}
