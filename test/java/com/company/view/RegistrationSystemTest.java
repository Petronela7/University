package com.company.view;

import com.company.model.Course;
import com.company.model.Student;
import com.company.model.Teacher;
import com.company.repository.CourseRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationSystemTest {
    Student s1 = new Student(100, "Petronela", "Halip");
    Student s2 = new Student(101, "Ana", "Pop");
    Student s3 = new Student(102, "Cristi", "Popescu");

    Teacher t1 = new Teacher(1000, "Anca", "Bobu");
    Teacher t2 = new Teacher(1001, "Dan", "Iriemscu");
    Teacher t3 = new Teacher(1002, "Elsa", "Irin");

    Course c1 = new Course("Databases", t1, 30, 6);
    Course c2 = new Course("Algebra", t1, 28, 6);
    Course c3 = new Course("Logic", t2, 30, 4);
    Course c4 = new Course("English", t3, 25, 5);
    Course c5 = new Course("German", t3, 28, 3);


    @BeforeEach
    public void createObjects() {

        List<Course> coursesT1 = new ArrayList<>();
        coursesT1.add(c1);
        coursesT1.add(c2);
        t1.setTaughtCourses(coursesT1);

        List<Course> coursesT2 = new ArrayList<>();
        coursesT2.add(c3);
        t2.setTaughtCourses(coursesT2);

        List<Course> coursesT3 = new ArrayList<>();
        coursesT3.add(c4);
        coursesT3.add(c5);
        t3.setTaughtCourses(coursesT3);

        List<Course> coursesStudent1 = new ArrayList<>();
        coursesStudent1.add(c1);
        c1.getStudentList().add(s1);
        coursesStudent1.add(c2);
        c2.getStudentList().add(s1);
        coursesStudent1.add(c3);
        c2.getStudentList().add(s1);
        s1.setEnrolledCourses(coursesStudent1);

        List<Course> coursesStudent2 = new ArrayList<>();
        coursesStudent2.add(c3);
        c2.getStudentList().add(s2);
        coursesStudent2.add(c4);
        c4.getStudentList().add(s2);
        coursesStudent2.add(c1);
        c1.getStudentList().add(s2);
        s2.setEnrolledCourses(coursesStudent2);

        List<Course> coursesStudent3 = new ArrayList<>();
        coursesStudent3.add(c1);
        c1.getStudentList().add(s3);
        s3.setEnrolledCourses(coursesStudent3);

        CourseRepository courses = new CourseRepository();
        courses.save(c1);
        courses.save(c2);
        courses.save(c3);
        courses.save(c4);
        courses.save(c5);
        RegistrationSystem registrationSystem = new RegistrationSystem(courses);


    }

    @Test
    void register() {
        

    }

    @Test
    void retrieveCoursesWithFreePlaces() {
    }

    @Test
    void retrieveStudentsFromCourse() {
    }

    @Test
    void getAllCourses() {
    }
}