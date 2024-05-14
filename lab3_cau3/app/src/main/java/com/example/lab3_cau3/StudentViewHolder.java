package com.example.lab3_cau3;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class StudentViewHolder extends RecyclerView.ViewHolder {
    private final TextView textViewName;
    private final TextView textViewAge;
    private final TextView textViewKhoa;

    public StudentViewHolder(View itemView){
        super(itemView);
        textViewName = itemView.findViewById(R.id.textViewName);
        textViewAge = itemView.findViewById(R.id.textViewAge);
        textViewKhoa = itemView.findViewById(R.id.textViewKhoa);
    }

    public void bind(final Student student, final OnItemClickListener listener) {
        textViewName.setText(student.getName());
        textViewAge.setText("Tuổi: " + String.valueOf(student.getAge()) + " ");
        textViewKhoa.setText("Khoa: " + String.valueOf(student.getKhoa()));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(student);
            }
        });
    }
}
