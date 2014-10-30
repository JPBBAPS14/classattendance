package com.zenzile.attendance;

import java.util.ArrayList;
import java.util.List;

import com.sihle.register.Student;
import com.sihle.register.repository.DatasourceDAO;
import com.zenzile.attendance.repository.DatasourceDAOImpl;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class View_attendance extends  Activity {

	final DatasourceDAO dao = new DatasourceDAOImpl((Context)(this));
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_records);		
		output();
	}
	
	private List<Student> getRecords()
	{
		List<Student> students = new ArrayList<Student>();
		students = dao.geStudentsList();
		return students;			
	}
	
	private void output()
	{
		List<Student> students = new ArrayList<Student>();
		//students = getRecords();
		Student student = new Student();
		List<String> studs = new ArrayList<String>();

		//get list
		//Geting the listView
		 ListView listView = (ListView)findViewById(R.id.lv_all_Students);
		
		
		 try
		 {			
			 
			 students =getRecords();
			 
			  for(int i = 0; i < students.size();i++)
			 {				 
				 student = (Student) students.get(i); 	
			     studs.add(student.getStudentNumber()+" | "+student.getInitials()+" | "+student.getSurname());//Add to string list
			 }
			 
		 }
		 catch(Exception ec)
		 {			 		 }
		 
		 listView.setAdapter(new ArrayAdapter<String>(View_attendance.this,android.R.layout.simple_list_item_1,studs));
   		
	}
	
	
	

}
