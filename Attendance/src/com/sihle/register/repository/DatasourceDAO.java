package com.sihle.register.repository;

import com.sihle.register.Student;

import java.util.List;

/**
 * Created by 211282278 on 10/2/2014.
 */
public interface DatasourceDAO {

    public void createStudent(Student student);
    public void updateStudent(Student student);
    public void deleteStudent(Student student);
    public int queryForId(Student student);
    public Student getStudentById(int id);
    public List<Student> geStudentsList();
    public Student getstudentByDetails(String studentno , String surname);
}
