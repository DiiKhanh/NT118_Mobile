package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.example.lab2.modelcau3.Employee;
import com.example.lab2.modelcau3.EmployeeFulltime;
import com.example.lab2.modelcau3.EmployeeParttime;

import java.util.ArrayList;

public class Cau3Activity extends AppCompatActivity {

    private ListView lvPerson;
    private TextView tvSelection;
    private Button btnSubmit;
    private EditText etName;
    private EditText etId;
    private RadioGroup rgType;
    private ArrayList<Employee> employees;
    private RadioButton rd_chinhthuc;

    private void initUi()
    {
        lvPerson = (ListView)findViewById(R.id.lv_person);
        tvSelection = (TextView)findViewById(R.id.tv_selection);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        etName = (EditText)  findViewById(R.id.etEnterTenNV);
        etId = (EditText)  findViewById(R.id.etEnterMaNV);
        rgType = (RadioGroup)  findViewById(R.id.rgType);
        rd_chinhthuc = (RadioButton)  findViewById(R.id.rd_chinhthuc);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cau3);
        initUi();
        employees = new ArrayList<Employee>();
        ArrayAdapter<Employee> adapter = new ArrayAdapter<Employee>(this, android.R.layout.simple_list_item_1, employees);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Thêm dữ liệu mới vào arraylist
                addNewEmployee();
                //Cập nhật dữ liệu mới lên giao diên
                adapter.notifyDataSetChanged();
            }
        });

        //5. Xử lý sự kiện chọn một phần tử trong ListView
        lvPerson.setAdapter(adapter);
        lvPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?>arg0, View arg1, int arg2, long arg3) {
                //đối số arg2 là vị trí phần tử trong Data Source (arr)
                tvSelection.setText("position :" + arg2 + "; value =" + employees.get(arg2));
            }
        });
    }

    public void addNewEmployee() {
        //Lấy ra đúng id của Radio Button được checked
        Employee employee = new Employee();
        int radId = rgType.getCheckedRadioButtonId();
        String id = etId.getText().toString();
        String name = etName.getText().toString();
        if (radId == R.id.rd_chinhthuc) {
            //tạo instance là FullTime
            employee = new EmployeeFulltime();
        } else {
            //Tạo instance là Partime
            employee = new EmployeeParttime();
        }
        //FullTime hay Partime thì cũng là Employee nên có các hàm này là hiển nhiên
        employee.setId(id);
        employee.setName(name);
        //Đưa employee vào ArrayList
        employees.add(employee);
    }

    @Override
    public void onBackPressed() {
        // Quay lại MainActivity khi nút back được nhấn
        super.onBackPressed();
    }
}