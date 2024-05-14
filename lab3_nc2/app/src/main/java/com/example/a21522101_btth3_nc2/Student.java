package com.example.a21522101_btth3_nc2;

public class Student {

    public Student(){
        MSSV = HoTen = Lop = "";
    }

    public Student(String mssv, String hoten, String lop){
        this.MSSV = mssv;
        this.HoTen = hoten;
        this.Lop = lop;
    }

    public String getMSSV() {
        return MSSV;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getLop() {
        return Lop;
    }

    public void setLop(String lop) {
        Lop = lop;
    }

    private String MSSV, HoTen, Lop;

}
