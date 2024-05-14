package com.example.demoapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    private EditText numberA, numberB, task;
    private Button btnSum, addTask;
    private TextView textViewResult;
    private static final String BASE_URL = "http://172.17.18.83:3001/test/sum";
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberA = findViewById(R.id.numberA);
        numberB = findViewById(R.id.numberB);
        btnSum = findViewById(R.id.btnSum);
        textViewResult = findViewById(R.id.textViewResult);


        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSum();
            }
        });

        addTask = findViewById(R.id.addTask);
        task = findViewById(R.id.task);

        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Tham chiếu đến "tasks" node trong cơ sở dữ liệu
        myRef = firebaseDatabase.getReference("tasks");

//        myRef.setValue("cong viec 1");
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskText = task.getText().toString().trim();
                if (!TextUtils.isEmpty(taskText)) {
                   myRef.setValue(taskText);
                   Toast.makeText(MainActivity.this, "Task added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a task name", Toast.LENGTH_SHORT).show();
                }
//                addTaskToFirebase();
            }
        });

    }

    private void calculateSum() {
        int num1 = Integer.parseInt(numberA.getText().toString());
        int num2 = Integer.parseInt(numberB.getText().toString());

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("num1", num1);
            requestBody.put("num2", num2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(requestBody);


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        System.out.println(requestQueue);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int result = response.getInt("result");
                            numberA.setText("");
                            numberB.setText((""));
                            textViewResult.setText("Result: " + result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            textViewResult.setText("Error occurred!");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textViewResult.setText(error.toString());
                System.out.println(error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public class Task {
        private String id;
        private String name;

        public Task() {
            // Empty constructor needed for Firebase
        }

        public Task(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private void addTaskToFirebase() {
        String taskText = task.getText().toString().trim();
        if (!TextUtils.isEmpty(taskText)) {
            // Tạo một key ngẫu nhiên cho task
            String taskId = myRef.push().getKey();
            Task taskValue = new Task(taskId, taskText);

            myRef.child(taskId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    myRef.setValue(taskValue);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
        } else {
            Toast.makeText(this, "Please enter a task name", Toast.LENGTH_SHORT).show();
        }

    }
}