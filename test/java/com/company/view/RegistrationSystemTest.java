package com.company.view;

import com.company.controller.RegistrationSystem;
import com.company.model.Course;
import com.company.model.Student;
import com.company.model.Teacher;
import com.company.repository.CourseRepository;
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
    Teacher t2 = new Teacher(1001, "Dan", "Irimescu");
    Teacher t3 = new Teacher(1002, "Elsa", "Irin");

    Course c1 = new Course("Databases", t1, 1, 12);
    Course c2 = new Course("Algebra", t1, 28, 6);
    Course c3 = new Course("Logic", t2, 30, 15);
    Course c4 = new Course("English", t3, 25, 5);
    Course c5 = new Course("German", t3, 28, 20);
    Course c6 = new Course("German2", t3, 28, 20);

    List<Course> coursesT1 = new ArrayList<>();
    List<Course> coursesT2 = new ArrayList<>();
    List<Course> coursesT3 = new ArrayList<>();
    CourseRepository courses = new CourseRepository();
    RegistrationSystem registrationSystem;

    @BeforeEach
    public void createObjects() {

        coursesT1.add(c1);
        coursesT1.add(c2);
        t1.setTaughtCourses(coursesT1);


        coursesT2.add(c3);
        t2.setTaughtCourses(coursesT2);


        coursesT3.add(c4);
        coursesT3.add(c5);
        t3.setTaughtCourses(coursesT3);


        courses.save(c1);
        courses.save(c2);
        courses.save(c3);
        courses.save(c4);
        courses.save(c5);
        registrationSystem = new RegistrationSystem(courses);


    }

    @Test
    void register() {
        assertTrue(registrationSystem.register(s1, c1));
        assertFalse(registrationSystem.register(s1, c5));//the number of credits exceeds the limit
        assertTrue(registrationSystem.register(s2, c2));
        assertFalse(registrationSystem.register(s3, c6));//testing with a course that doesn't exist

    }

    @Test
    void retrieveCoursesWithFreePlaces() {
        registrationSystem.register(s1, c1);
        registrationSystem.register(s3, c5);
        assertEquals(registrationSystem.retrieveCoursesWithFreePlaces().get(0).getCourseName(), "Algebra");
        assertEquals(registrationSystem.retrieveCoursesWithFreePlaces().get(1).getCourseName(), "Logic");
        assertEquals(registrationSystem.retrieveCoursesWithFreePlaces().get(2).getCourseName(), "English");
        assertEquals(registrationSystem.retrieveCoursesWithFreePlaces().get(3).getCourseName(), "German");
        assertEquals(registrationSystem.retrieveCoursesWithFreePlaces().size(), 4);
    }

    @Test
    void retrieveStudentsFromCourse() {
        registrationSystem.register(s1, c2);
        registrationSystem.register(s3, c5);
        registrationSystem.register(s3, c2);
        registrationSystem.register(s2, c4);
        assertEquals(registrationSystem.retrieveStudentsFromCourse(c1).size(), 0);
        assertEquals(registrationSystem.retrieveStudentsFromCourse(c2).size(), 2);
        assertEquals(registrationSystem.retrieveStudentsFromCourse(c3).size(), 0);
        assertEquals(registrationSystem.retrieveStudentsFromCourse(c4).size(), 1);
        assertEquals(registrationSystem.retrieveStudentsFromCourse(c5).size(), 1);

    }

    @Test
    void getAllCourses() {
        assertEquals(registrationSystem.getAllCourses().size(),5);
        assertEquals(registrationSystem.getAllCourses().get(0).getCourseName(),"Databases");
    }

    @Test
    void updateCredits() {
        registrationSystem.register(s1, c2);
        registrationSystem.register(s3, c5);
        registrationSystem.register(s3, c2);
        registrationSystem.register(s2, c4);
        registrationSystem.updateCredits(c4,6);
        registrationSystem.updateCredits(c2, 4);
        assertEquals(s2.getTotalCredits(),6);
        assertEquals(s3.getTotalCredits(),24);
        assertEquals(s1.getTotalCredits(),4);

    }

    @Test
    void deleteCourse() {
        registrationSystem.register(s1, c2);
        registrationSystem.register(s3, c5);
        registrationSystem.register(s3, c2);
        registrationSystem.register(s2, c4);
        registrationSystem.register(s2, c2);

        registrationSystem.deleteCourse(t1,c2);
        assertEquals(s3.getEnrolledCourses().size(), 1);
        assertEquals(s3.getEnrolledCourses().get(0).getCourseName(),"German");
        assertEquals(s1.getEnrolledCourses().size(),0);
        assertEquals(s2.getEnrolledCourses().size(),1);

        registrationSystem.deleteCourse(t3,c4);
        assertEquals(s2.getEnrolledCourses().size(),0);

    }
}