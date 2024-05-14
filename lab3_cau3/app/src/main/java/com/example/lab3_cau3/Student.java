package com.example.lab3_cau3;

public class Student {
    private int id;
    private String name;
    private int age;

    private String khoa;

    public Student(int id, String name, int age, String khoa) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.khoa = khoa;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getKhoa() {return khoa;}
}
