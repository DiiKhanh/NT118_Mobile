package com.example.lab3_bt3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.lab3_bt3.student;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;
    String DATABASE_NAME="qlsv.db";
    //Khai báo ListView
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;
    Button insertButton, updateButton, deleteButton;
    EditText inputId, inputName, inputClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertButton = findViewById(R.id.insertButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        inputId = findViewById(R.id.editTextMSSV);
        inputName = findViewById(R.id.editTextName);
        inputClass = findViewById(R.id.editTextClass);

        //Ham Copy CSDL từ assets vào thư mục Databases
        processCopy();
        //Mo CSDL trong ung dung len
        database = openOrCreateDatabase("qlsv.db",MODE_PRIVATE, null);
        // Tạo ListView
        lv = findViewById(R.id.studentList);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);
        // Truy vấn CSDL và cập nhật hiển thị lên Listview
        Cursor c = database.query("qlsv",null,null,null,null,null,null);
        c.moveToFirst();
        ArrayList<student> students = new ArrayList<>();
        while (c.isAfterLast() == false)
        {
            String id = c.getString(0);
            String name = c.getString(1);
            String className = c.getString(2);
            students.add(new student(id, name, className));
            c.moveToNext();
        }
        c.close();
        ArrayAdapter<student> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, students);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            student student = students.get(position);
            Intent intent = new Intent(MainActivity.this, StudentDetailActivity.class);
            intent.putExtra("student", student);
            startActivity(intent);
        });
        //Xữ lý thao các các button
        insertButton.setOnClickListener(v -> {
            // Get the input from EditText fields
            String id = inputId.getText().toString();
            String name = inputName.getText().toString();
            String className = inputClass.getText().toString();

            // Insert the new student into the database
            database.execSQL("INSERT INTO qlsv (MSSV, HOTEN, LOP) VALUES (?, ?, ?)",
                    new String[]{id, name, className});

            // Refresh the ListView
            refreshListView();
        });

        updateButton.setOnClickListener(v -> {
            // Get the input from EditText fields
            String id = inputId.getText().toString();
            String name = inputName.getText().toString();
            String className = inputClass.getText().toString();

            // Update the student in the database
            database.execSQL("UPDATE qlsv SET HOTEN = ?,LOP = ? WHERE MSSV = ?",
                    new String[]{name, className, id});

            // Refresh the ListView
            refreshListView();
        });

        deleteButton.setOnClickListener(v -> {
            // Get the input from EditText field
            String id = inputId.getText().toString();

            // Delete the student from the database
            database.execSQL("DELETE FROM qlsv WHERE MSSV = ?",
                    new String[]{id});

            // Refresh the ListView
            refreshListView();
        });
    }
    private void processCopy() {
        //private app
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Toast.makeText(this, e.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+
                DATABASE_NAME;
    }
    // Ham copy file DB tu thu muc Asset vao file DB moi tao ra trong ung dung
    public void CopyDataBaseFromAsset() {
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            // Kiem tra neu duong dan khong co, thi tao moi file
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists()) f.mkdir();
            // Mo empty db su dung output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            // Sao chep du lieu bytes tu input toi ouput
            int size = myInput.available();
            byte[] buffer = new byte[size];
            myInput.read(buffer);
            myOutput.write(buffer);
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void refreshListView() {
        // Clear the current list
        mylist.clear();

        // Query the database again
        Cursor c = database.query("qlsv",null,null,null,null,null,null);
        c.moveToFirst();
        ArrayList<student> students = new ArrayList<>();
        while (c.isAfterLast() == false)
        {
            String id = c.getString(0);
            String name = c.getString(1);
            String className = c.getString(2);
            students.add(new student(id, name, className));
            c.moveToNext();
        }
        c.close();

        // Update the ListView with the new data
        ArrayAdapter<student> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, students);
        lv.setAdapter(adapter);
    }
}
