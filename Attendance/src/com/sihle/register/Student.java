package com.sihle.register;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 211282278 on 10/2/2014.
 */
public class Student implements Parcelable{

    private int id;
    private String studentNumber;
    private String initials;
    private String surname;
    private int listPostion = 0;
    private Date time;
    

    public Student()
    {

    }

	public Student(int id, String studentNumber, String initials,
			String surname, Date time, int listPos) {
		this.id = id;
		this.studentNumber = studentNumber;
		this.initials = initials;
		this.surname = surname;
		this.time = time;
		this.listPostion = listPos;
	}
	
	public Student(Builder bs) {
		id = bs.id;
		studentNumber = bs.studentNumber;
		initials = bs.initials;
		surname = bs.surname;
		time = bs.time;
		listPostion = bs.listPostion;

	}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    
	public int getListPostion() {
		return listPostion;
	}

	public void setListPostion(int listPostion) {
		this.listPostion = listPostion;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
    
	public static class Builder
	{
	    private int id;
	    private String studentNumber;
	    private String initials;
	    private String surname;
	    private Date time;
	    private int listPostion = 0;
	    
		public Builder(int id, String studentNumber, String initials,
				String surname, Date time, int listPos) {
			super();
			this.id = id;
			this.studentNumber = studentNumber;
			this.initials = initials;
			this.surname = surname;
			this.time = time;
			this.listPostion = listPos;
		}
		
		public Builder()
		{
			
		}
		
        public Builder id(int i){
            id = i;
            return this;
        }
        public Builder studentnumber(String stu){
        	studentNumber = stu;
            return this;
        }
        public Builder initials(String stu){
        	initials = stu;
            return this;
        }
        public Builder surname(String stu){
        	surname = stu;
            return this;
        }
        public Builder time(Date stu){
        	time = stu;
            return this;
        }
        public Builder listposition(int stu){
        	listPostion = stu;
            return this;
        }
        
        public Builder contact(Student s){
            id = s.getId();
            initials = s.getInitials();
            surname = s.getSurname();
            time = s.getTime();
            listPostion = s.getListPostion();
            return this;
        }
        
        public Student builder()
        {
        	return new Student(this);
        }
	}
    public int describeContents(){
        return 0;
    }

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
        dest.writeInt(this.id);
        dest.writeString(this.studentNumber);
        dest.writeString(this.initials);
        dest.writeString(this.surname);
        dest.writeLong(this.time.getTime());
        dest.writeInt(this.listPostion);
	}
    public static final Creator<Student> CREATOR = new Creator<Student>(){
        public Student createFromParcel(Parcel in){
            return new Student(in);
        }

        public Student[] newArray(int size){
            return new Student[size];
        }
    };
    private Student(Parcel in){
    	this.id = in.readInt();
        this.studentNumber = in.readString();
        this.initials = in.readString();
        this.surname = in.readString();
        this.time = new Date(in.readLong());
        this.listPostion = in.readInt();
        
    }
}
