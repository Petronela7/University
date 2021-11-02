package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {
    private long teacherID;
    private String firstName;
    private String lastName;
    private List<Course> taughtCourses;

    public Teacher( long teacherID,String firstName, String lastName) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.teacherID = teacherID;
        this.taughtCourses = new ArrayList<>();

    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(long teacherID) {
        this.teacherID = teacherID;
    }

    public List<Course> getTaughtCourses() {
        return taughtCourses;
    }

    public void setTaughtCourses(List<Course> taughtCourses) {
        this.taughtCourses = taughtCourses;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherID=" + teacherID +
                ", taughtCourses=" + taughtCourses +
                '}';
    }
}
