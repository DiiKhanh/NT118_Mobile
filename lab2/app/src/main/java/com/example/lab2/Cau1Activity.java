package com.example.lab2;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Cau1Activity extends AppCompatActivity {
    private ListView lvPerson;
    private TextView tvSelection;
    private final String arr[] = {"Teo", "Ty", "Bin", "Bo"};

    private void initUi()
    {
        lvPerson = (ListView)findViewById(R.id.lv_person);
        tvSelection = (TextView)findViewById(R.id.tv_selection);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cau1);
        initUi();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);

        lvPerson.setAdapter(adapter);

        lvPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?>arg0, View arg1, int arg2, long arg3) {
                //đối số arg2 là vị trí phần tử trong Data Source (arr)
                tvSelection.setText("position :" + arg2 + "; value =" + arr[arg2]);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Quay lại MainActivity khi nút back được nhấn
        super.onBackPressed();
    }

}
