package com.example.lab3_sinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtmssv, edthoten, edtlop;
    Button btninsert, btndelete, btnupdate, btnquery;
    // khai báo ListView
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtmssv = findViewById(R.id.edtmssv);
        edthoten = findViewById(R.id.edthoten);
        edtlop = findViewById(R.id.edtlop);
        btninsert = findViewById(R.id.btninsert);
        btndelete = findViewById(R.id.btndelete);
        btnupdate = findViewById(R.id.btnupdate);
        btnquery = findViewById(R.id.btnquery);
// Tạo ListView
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);
// Tạo và mở Cơ sở dữ liệu Sqlite
        mydatabase = openOrCreateDatabase("qlsinhvien.db", MODE_PRIVATE, null);
// Tạo Table để chứa dữ liệu
        try {
            String sql = "CREATE TABLE tbllop(mssv TEXT primary key, hoten TEXT, malop TEXT)";
            mydatabase.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", "Table đã tồn tại");
        }
        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mssv = edtmssv.getText().toString();
                String hoten = edthoten.getText().toString();
                String malop = edtlop.getText().toString();
                ContentValues myvalue = new ContentValues();
                myvalue.put("mssv", mssv);
                myvalue.put("hoten", hoten);
                myvalue.put("malop", malop);
                String msg = "";
                if (mydatabase.insert("tbllop", null, myvalue) == -1) {
                    msg = "Fail to Insert Record!";
                } else {
                    msg = "Insert record Sucessfully";
                }
                Toast.makeText(MainActivity.this, msg,
                        Toast.LENGTH_SHORT).show();
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mssv = edtmssv.getText().toString();
                int n = mydatabase.delete("tbllop", "mssv = ?", new
                        String[]{mssv});
                String msg = "";
                if (n == 0) {
                    msg = "No record to Delete";
                } else {
                    msg = n + " record is deleted";
                }
                Toast.makeText(MainActivity.this, msg,
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = edthoten.getText().toString();
                String mssv = edtmssv.getText().toString();
                ContentValues myvalue = new ContentValues();
                myvalue.put("hoten", hoten);
                int n = mydatabase.update("tbllop", myvalue, "mssv = ?", new
                        String[]{mssv});
                String msg = "";
                if (n == 0) {
                    msg = "No record to Update";
                } else {
                    msg = n + " record is updated";
                }
                Toast.makeText(MainActivity.this, msg,
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylist.clear();
                Cursor c = mydatabase.query("tbllop", null, null, null, null, null, null);
                c.moveToNext();
                String data = "";
                while (c.isAfterLast() == false) {
                    data = c.getString(0) + " - " + c.getString(1) + " - " + c.getString(2);
                    c.moveToNext();
                    mylist.add(data);
                }
                c.close();
                myadapter.notifyDataSetChanged();
            }
        });
    }
}