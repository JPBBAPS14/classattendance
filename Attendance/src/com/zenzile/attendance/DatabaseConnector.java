package com.zenzile.attendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseConnector {

	   private static final String DATABASE_NAME = "AttendanceRegister";
	   private SQLiteDatabase database; 
	   private DatabaseOpenHelper databaseOpenHelper; 
	   
	   //
	   public DatabaseConnector(Context context) 
	   {
	      databaseOpenHelper = 
	         new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
	   }
	   //
	   public void open() throws SQLException 
	   {
	      database = databaseOpenHelper.getWritableDatabase();
	   }
	   
	   public void close() 
	   {
	      if (database != null)
	         database.close();
	   }
	   
	   
	   //CRUD
	   public void insertStudent(String stno)
	   {
		   ContentValues newStudent = new ContentValues();
		      newStudent.put("student_no", stno);
		      open();
		      database.insert(DATABASE_NAME, null, newStudent);
		      database.insert("students", null, newStudent);
		      close();
	   }
	   
	   public void updateStudent(int id, String stud) 
		{
			      ContentValues editStudent = new ContentValues();
			      editStudent.put("student_no", stud);
			      
			      open();
			      database.update(DATABASE_NAME, editStudent, "_id=" + id, null);
			      database.update("students", editStudent, "_id=" + id, null);
			      close();
		}
	   public Cursor getAllContacts() 
	   {
	      return database.query("students", new String[] {"_id", "student_no"}, 
	         null, null, null, null, "student_no");
	   }
	   
	   public Cursor getOneContact(long id) 
	   {
	      return database.query(
	         "students", null, "_id=" + id, null, null, null, null);
	   }
	   
	   public void deleteContact(int id) 
	   {
	      open();
	      database.delete("students", "_id=" + id, null);
	      close();
	   }
	   
	   private class DatabaseOpenHelper extends SQLiteOpenHelper 
	   {
	      public DatabaseOpenHelper(Context context, String name,
	         CursorFactory factory, int version) 
	      {
	         super(context, name, factory, version);
	      } 
	      
	      @Override
	      public void onCreate(SQLiteDatabase db) 
	      {
	         String createQuery = "CREATE TABLE contacts" +
	            "(_id integer primary key autoincrement," +
	            "student_no TEXT," + ");";
	                  
	         db.execSQL(createQuery);
	      }

	      @Override
	      public void onUpgrade(SQLiteDatabase db, int oldVersion, 
	          int newVersion) 
	      {
	      } 
	   }
}
