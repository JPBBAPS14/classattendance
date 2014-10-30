package com.zenzile.attendance.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sihle.register.Student;
import com.sihle.register.repository.DatasourceDAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 211282278 on 10/2/2014.
 */
public class DatasourceDAOImpl implements DatasourceDAO{

    private SQLiteDatabase database;
    private DBAdapter dbHelper;

    public DatasourceDAOImpl(Context context) {
        dbHelper = new DBAdapter(context);
    }

    public void open() throws SQLException {
    	
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

	@Override
	public void createStudent(Student student) {


        try
        {
            open();
            
            ContentValues values = new ContentValues();
            
            values.put(DBAdapter.COLUMN_STUDENT_NO, student.getStudentNumber());
            values.put(DBAdapter.COLUMN_INITIALS, student.getInitials());
            values.put(DBAdapter.COLUMN_SURNAME, student.getSurname());
            values.put(DBAdapter.COLUMN_TIME, student.getTime().toString());
            
            database.insert(DBAdapter.TABLE_STUDENT, null, values);
            close();
        }
        catch(Exception e){
            Log.d("" , "error: " + e.getMessage());
        }
        
	}

	@Override
	public void updateStudent(Student student) {
        try{
            open();
            ContentValues values = new ContentValues();
            
            values.put(DBAdapter.COLUMN_STUDENT_NO, student.getStudentNumber());
            values.put(DBAdapter.COLUMN_INITIALS, student.getInitials());
            values.put(DBAdapter.COLUMN_SURNAME, student.getSurname());
            values.put(DBAdapter.COLUMN_TIME, student.getTime().toString());

            database.update(DBAdapter.TABLE_STUDENT, values, DBAdapter.COLUMN_ID + " = ?", new String[]{String.valueOf(student.getId())});
            close();
        }
        catch(Exception e){
            Log.d("" , "error UPDATE: " + e.getMessage());
        }
	}

	@Override
	public void deleteStudent(Student student) {
        try{
            open();
            database.delete(DBAdapter.TABLE_STUDENT , DBAdapter.COLUMN_ID + " = ?" , new String[]{String.valueOf(student.getId())} );
            close();
        }
        catch(Exception e){
        	Log.d("" , "error DLETE: " + e.getMessage());
        }
	}

	@Override
	public int queryForId(Student student) {
        int columnID = 0;
        try{
            open();
            String stud = student.getStudentNumber();
            String sur = student.getSurname();

            //Query for Last name AND Phone number
            Cursor cursor = database.query(DBAdapter.TABLE_STUDENT, new String[]{DBAdapter.COLUMN_ID }, DBAdapter.COLUMN_STUDENT_NO + " = ?" + " and " + DBAdapter.COLUMN_SURNAME + " = ?" ,
            		new String[]{String.valueOf(stud),String.valueOf(sur) } , null , null , null, null  );

            if (cursor != null)
                cursor.moveToFirst();


            columnID = cursor.getInt(0);

            close();
        }
        catch(Exception e){
        	Log.d("" , "error Query For ID: " + e.getMessage());
        }

        return columnID;
	}

	@Override
	public Student getStudentById(int id) {
        Student student  = null;
        try{
            open();
            Cursor cursor = database.query(DBAdapter.TABLE_STUDENT , new String[]{DBAdapter.COLUMN_ID ,
                    DBAdapter.COLUMN_STUDENT_NO ,
                    DBAdapter.COLUMN_INITIALS ,
                    DBAdapter.COLUMN_SURNAME,
                    DBAdapter.COLUMN_TIME
                    } , DBAdapter.COLUMN_ID + " = ?" , new String[]{String.valueOf(id)} , null , null , null, null); //remove null here

            if (cursor != null)
                cursor.moveToFirst();

            student = new Student.Builder()
                    .id(cursor.getInt(0))
                    .studentnumber(cursor.getString(1))
                    .initials(cursor.getString(2))
                    .surname(cursor.getString(3))
                    .time(Timestamp.valueOf(cursor.getString(4)))
                    .builder();
            close();


        }
        catch(Exception e){
        	Log.d("" , "error DLETE: " + e.getMessage());
        }

        return student;
	}

	@Override
	public List<Student> geStudentsList() {
        String selectQuery = "SELECT * FROM " + DBAdapter.TABLE_STUDENT;
        List<Student> cs = new ArrayList<Student>();

        try{
            open();
            Cursor cursor  = database.rawQuery(selectQuery , null);

            if(cursor.moveToFirst()){
                do{
                    final Student student = new Student.Builder()
	                    .id(cursor.getInt(0))
	                    .studentnumber(cursor.getString(1))
	                    .initials(cursor.getString(2))
	                    .surname(cursor.getString(3))
	                    .time(Timestamp.valueOf(cursor.getString(4)))
	                    .builder();
                    cs.add(student);

                }while(cursor.moveToNext());
            }

            close();
        }
        catch(Exception e){

        }

        return cs;
	}

	@Override
	public Student getstudentByDetails(String studentno, String surname) {
        Student student = null;
        try{
            open();
            Cursor cursor = database.query(DBAdapter.TABLE_STUDENT, new String[]{DBAdapter.COLUMN_ID ,
                    DBAdapter.COLUMN_STUDENT_NO ,
                    DBAdapter.COLUMN_INITIALS ,
                    DBAdapter.COLUMN_SURNAME,
                    DBAdapter.COLUMN_TIME 
                    } , DBAdapter.COLUMN_STUDENT_NO + " = ?" + " and " + DBAdapter.COLUMN_SURNAME 
                    	+ " = ?" , new String[]{String.valueOf(studentno), String.valueOf(surname)} , null , null , null, null);

            if (cursor != null)
                cursor.moveToFirst();

           student = new Student.Builder()
           .id(cursor.getInt(0))
           .studentnumber(cursor.getString(1))
           .initials(cursor.getString(2))
           .surname(cursor.getString(3))
           .time(Timestamp.valueOf(cursor.getString(4)))
           .builder();

            close();
        }
        catch(Exception e){

        }

        return student;
	}
}
