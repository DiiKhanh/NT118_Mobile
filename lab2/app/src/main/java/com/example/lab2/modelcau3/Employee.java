package com.example.lab2.modelcau3;

public class Employee {
    protected String id;
    protected String name;
    protected  boolean ismanager;

    public Employee() {
    }

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsmanager(boolean ismanager) {
        this.ismanager = ismanager;
    }

    public double tinhLuong(){
        return 0;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }

    public boolean isManager()
    {
        return ismanager;
    }
}