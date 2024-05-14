package com.example.lab3_cau3;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextKhoa;
    private StudentAdapter studentAdapter;
    private SQLiteDatabase database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextKhoa = findViewById(R.id.editKhoa);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        RecyclerView recyclerViewStudents = findViewById(R.id.recyclerViewStudents);

        studentAdapter = new StudentAdapter();
        studentAdapter.setOnItemClickListener(this);

        // Check if recyclerViewStudents is successfully initialized
        if (recyclerViewStudents != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerViewStudents.setLayoutManager(layoutManager);
            recyclerViewStudents.setAdapter(studentAdapter);
        } else {
            Log.e("MainActivity", "RecyclerView is null");
        }

        DBHelper dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        loadStudentsFromDatabase();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                int age = Integer.parseInt(editTextAge.getText().toString());
                String khoa = editTextKhoa.getText().toString();
                ContentValues values = new ContentValues();

                values.put("name", name);
                values.put("age", age);
                values.put("khoa", khoa);

                long id = database.insert("Student", null, values);
                Student student = new Student((int) id, name, age, khoa);
                studentAdapter.addStudent(student);
                editTextName.setText("");
                editTextAge.setText("");
                editTextKhoa.setText("");
            }
        });
    }

    private void loadStudentsFromDatabase() {
        Cursor cursor = database.rawQuery("SELECT * FROM Student", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") int age = cursor.getInt(cursor.getColumnIndex("age"));
                @SuppressLint("Range") String khoa = cursor.getString(cursor.getColumnIndex("khoa"));
                Student student = new Student(id, name, age, khoa);
                studentAdapter.addStudent(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onItemClick(final Student student) {
        // Capture the initial values
        final String initialName = student.getName();
        final int initialAge = student.getAge();
        final String initialKhoa = student.getKhoa();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Student");
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_student, null);
        final EditText editTextName = view.findViewById(R.id.edit1TextName);
        final EditText editTextAge = view.findViewById(R.id.edit1TextAge);
        final EditText editTextKhoa = view.findViewById(R.id.edit1TextKhoa);
        editTextName.setText(initialName);
        editTextAge.setText(String.valueOf(initialAge));
        editTextKhoa.setText(initialKhoa);
        builder.setView(view);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the current values
                String name = editTextName.getText().toString();
                int age = Integer.parseInt(editTextAge.getText().toString());
                String khoa = editTextKhoa.getText().toString();

                // Compare with the initial values
                if (!name.equals(initialName) || age != initialAge || !khoa.equals(initialKhoa)) {
                    // Values have changed, update the database
                    ContentValues values = new ContentValues();
                    values.put("name", name);
                    values.put("age", age);
                    values.put("khoa", khoa);
                    int rowsAffected = database.update("Student", values, "id = ?", new String[]{String.valueOf(student.getId())});
                    if (rowsAffected > 0) {
                        // Update the data in the adapter
                        studentAdapter.updateStudent(new Student(student.getId(), name, age, khoa));

                        // Notify the adapter that the data set has changed
                        studentAdapter.notifyDataSetChanged();

                        Toast.makeText(MainActivity.this, "Student updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to update student", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Values have not changed
                    Toast.makeText(MainActivity.this, "No changes made", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int rowsAffected = database.delete("Student", "id = ?", new String[]{String.valueOf(student.getId())});
                if (rowsAffected > 0) {
                    studentAdapter.deleteStudent(student);
                    studentAdapter.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this, "Student deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to delete student", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.show();
    }
}
