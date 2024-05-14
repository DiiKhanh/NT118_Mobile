package com.example.lab3_cau3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Student.db";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_STUDENT = "Student";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_KHOA = "khoa";

    private static final String SQL_CREATE_TABLE_STUDENT =
            "CREATE TABLE " + TABLE_STUDENT + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_AGE + " INTEGER," +
                    COLUMN_KHOA + " TEXT)";

    private static final String SQL_DELETE_TABLE_STUDENT =
            "DROP TABLE IF EXISTS " + TABLE_STUDENT;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_STUDENT);
        onCreate(db);
    }

    // Thực hiện thêm sinh viên
    public void addStudent(String name, int age, String khoa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_KHOA, khoa);
        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }

    // Thực hiện đọc tất cả sinh viên
    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_STUDENT, null, null, null, null, null, null);
    }

    // Thực hiện đọc thông tin một sinh viên dựa trên ID
    public Cursor getStudentById(int studentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ID, COLUMN_NAME, COLUMN_AGE, COLUMN_KHOA};
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(studentId)};
        return db.query(TABLE_STUDENT, projection, selection, selectionArgs, null, null, null);
    }

    // Thực hiện cập nhật thông tin sinh viên
    public void updateStudent(int studentId, String name, int age, String khoa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_KHOA, khoa);
        String whereClause = COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(studentId)};
        db.update(TABLE_STUDENT, values, whereClause, whereArgs);
        db.close();
    }

    // Thực hiện xóa một sinh viên dựa trên ID
    public void deleteStudent(int studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_NAME + "=?";
        String[] whereArgs = {String.valueOf(studentId)};
        db.delete(TABLE_STUDENT, whereClause, whereArgs);
        db.close();
    }
}
