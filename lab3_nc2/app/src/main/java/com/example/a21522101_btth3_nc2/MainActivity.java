package com.example.a21522101_btth3_nc2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    EditText mssvET, lopET, hotenET;
    Button insert, update, delete, query;
    RecyclerView rvStudent;
    ArrayList<Student> mylist;
    StudentAdapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processCopy();

        database = openOrCreateDatabase("qlsv.db",MODE_PRIVATE, null);
        mssvET = findViewById(R.id.mssvfield);
        lopET = findViewById(R.id.lopfield);
        hotenET = findViewById(R.id.hotenfield);
        insert = findViewById(R.id.insert);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        query = findViewById(R.id.query);

        rvStudent = findViewById(R.id.rv_student);
        mylist = new ArrayList<Student>();

        // Truy vấn CSDL và cập nhật hiển thị lên Recycleview
        Cursor c = database.query("qlsv",new String[]{"MSSV", "Lop", "Hoten"},null,null,null,null,null);
        while (c.moveToNext())
        {
            mylist.add(new Student(c.getString(0), c.getString(2), c.getString(1)));
        }
        c.close();
        myadapter = new StudentAdapter(this, mylist, mssvET, hotenET, lopET);
        rvStudent.setAdapter(myadapter);
        rvStudent.setLayoutManager(new LinearLayoutManager(this));


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                removeAllDataListView();
                queryAllData();
                clearFillIn();
                myadapter.notifyDataSetChanged();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
                removeAllDataListView();
                queryAllData();
                clearFillIn();
                myadapter.notifyDataSetChanged();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
                removeAllDataListView();
                queryAllData();
                clearFillIn();
                myadapter.notifyDataSetChanged();
            }
        });

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryData();
                clearFillIn();
                myadapter.notifyDataSetChanged();
            }
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
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    // Ham copy file DB tu thu muc Asset vao file DB moi tao ra trong ung dung
    public void CopyDataBaseFromAsset() {
        try {
            InputStream myInput = getAssets().open(DATABASE_NAME);
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


    public void insertData(){
        if (!mssvET.getText().toString().isEmpty()) {
            ContentValues values = new ContentValues();
            values.put("MSSV", mssvET.getText().toString());
            values.put("Lop", lopET.getText().toString());
            values.put("HoTen", hotenET.getText().toString());
            database.insert("qlsv", null, values);
        }
    }

    public void updateData(){
        if (!mssvET.getText().toString().isEmpty()) {
            ContentValues values = new ContentValues();
            values.put("Lop", lopET.getText().toString());
            values.put("HoTen", hotenET.getText().toString());
            database.update("qlsv", values, "MSSV=" + mssvET.getText().toString(), null);
        }
    }

    public void deleteData(){
        String whereClause = "";
        if (!mssvET.getText().toString().equals("")){
            whereClause = "MSSV='" + mssvET.getText().toString() + "'";
        }
        if (!lopET.getText().toString().equals("")){
            if(!whereClause.isEmpty())
                whereClause += " AND ";
            whereClause += "Lop='" + lopET.getText().toString() + "'";
        }
        if (!hotenET.getText().toString().equals("")){
            if(!whereClause.isEmpty())
                whereClause += " AND ";
            whereClause += "HoTen='" + hotenET.getText().toString() + "'";
        }
        if (whereClause.isEmpty()){
            whereClause = null;
        }
        database.delete("qlsv", whereClause, null);
    }

    public void queryData(){
        String whereClause = "";
        if (!mssvET.getText().toString().equals("")){
            whereClause = "MSSV='" + mssvET.getText().toString() + "'";
        }
        if (!lopET.getText().toString().equals("")){
            if(!whereClause.isEmpty())
                whereClause += " AND ";
            whereClause += "Lop='" + lopET.getText().toString() + "'";
        }
        if (!hotenET.getText().toString().equals("")){
            if(!whereClause.isEmpty())
                whereClause += " AND ";
            whereClause += "HoTen='" + hotenET.getText().toString() + "'";
        }
        if (whereClause.isEmpty()){
            whereClause = null;
        }

        removeAllDataListView();

        Cursor c = database.query("qlsv",new String[]{"MSSV", "Lop", "HoTen"},whereClause,null,null,null,null);
        while (c.moveToNext())
        {
            mylist.add(new Student(c.getString(0), c.getString(2), c.getString(1)));
        }
        c.close();
    }

    public void queryAllData(){
        Cursor c = database.query("qlsv",new String[]{"MSSV", "Lop", "HoTen"},null,null,null,null,null);
        while (c.moveToNext())
        {
            mylist.add(new Student(c.getString(0), c.getString(2), c.getString(1)));
        }
        c.close();
    }

    public void clearFillIn(){
        mssvET.setText("");
        lopET.setText("");
        hotenET.setText("");
    }

    public void removeAllDataListView (){
        mylist.clear();
    }
}