package com.monk.storagetype.sqlite.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.monk.storagetype.SQLiteDbHelper;
import com.monk.storagetype.sqlite.bean.Student;

import java.util.Random;

/**
 * @author monk
 * @date 2019-06-01
 */
public final class SQLiteDao {

    private final SQLiteOpenHelper openHelper;

    public SQLiteDao(SQLiteOpenHelper openHelper) {
        this.openHelper=openHelper;
    }

    public void insertStudents() {
        // 这个 API 说明，这个方法 upgrade 要很长时间？？？建议不要在主线程开启
        SQLiteDatabase sdb = openHelper.getWritableDatabase();
        for (int i = 0; i < 5; i++) {
            ContentValues values = studentToContentValues(mockStudent(i));
            sdb.insert(SQLiteDbHelper.TABLE_STUDENT, null, values);
        }
        sdb.close();
    }

    private Student mockStudent(int i) {
        Student student = new Student();
        student.id = i;
        student.name = "user-" + i;
        student.tel_no = String.valueOf(new Random().nextInt(200000));
        student.cls_id = new Random().nextInt(5);
        return student;
    }

    private ContentValues studentToContentValues(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", student.id);
        contentValues.put("name", student.name);
        contentValues.put("tel_no", student.tel_no);
        contentValues.put("cls_id", student.cls_id);
        return contentValues;
    }
    private void queryStudents() {
        SQLiteDatabase sdb = openHelper.getReadableDatabase();
        // 相当于 select * from students 语句
        Cursor cursor = sdb.query(SQLiteDbHelper.TABLE_STUDENT, null,
                "cls_id > ? and id >= 1", new String[]{"3"},
                null, null, null, null);

        // 不断移动光标获取值
        while (cursor.moveToNext()) {
            // 直接通过索引获取字段值
            int stuId = cursor.getInt(0);

            // 先获取 name 的索引值，然后再通过索引获取字段值
            String stuName = cursor.getString(cursor.getColumnIndex("name"));
            Log.e("", "id: " + stuId + " name: " + stuName);
        }
        // 关闭光标
        cursor.close();
        sdb.close();
    }

    private void deleteStudents() {
        SQLiteDatabase sdb = openHelper.getReadableDatabase();
        sdb.delete(SQLiteDbHelper.TABLE_STUDENT,
                "cls = ?", new String[]{"3"});
        sdb.close();
    }
}
