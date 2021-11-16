package com.company.controller;

import com.company.model.Course;
import com.company.model.Student;
import com.company.model.Teacher;
import com.company.repository.CourseRepository;
import com.company.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RegistrationSystem {
    private final CourseRepository courseRepository;

    public RegistrationSystem(CourseRepository courseRepository1) {
        this.courseRepository = courseRepository1;

    }

    /**
     * @param student is an object type Student
     * @param course is an object type Course
     * @return true if the student is successfully enrolled in course
     */
    public boolean register(Student student, Course course) {
        if (courseRepository.findOne(course) != null) {//verify if the course exists
            if (course.getNumberStudentsMax() > course.getStudentList().size()) {//verify if there are free places
                if (course.getCredits() + student.getTotalCredits() <= 30) {//verify if the number of credits doesn't exceed the limit
                    List<Course> newCourseList = student.getEnrolledCourses();
                    newCourseList.add(course);
                    student.setEnrolledCourses(newCourseList);//register student into course

                    student.setTotalCredits(student.getTotalCredits() + course.getCredits());//update credits

                    List<Student> newStudentList = course.getStudentList();
                    newStudentList.add(student);
                    course.setStudentList(newStudentList);//add to course's list of students
                    return true;

                } else {
                    System.out.println("If you choose this course, your number of credits will exceed the limit.Choose another course.");
                    return false;
                }
            } else {
                System.out.println("There are no free places.Choose from: " + retrieveCoursesWithFreePlaces());
                return false;
            }

        } else {
            System.out.println("There is no such course at this university. Choose from : " + courseRepository);
            return false;
        }

    }

    /**
     * @return the list of courses with free places
     */
    public List<Course> retrieveCoursesWithFreePlaces() {
        List<Course> coursesWithFreePlaces = new ArrayList<>();
        for (Course course : courseRepository.findAll())
            if (course.getNumberStudentsMax() > course.getStudentList().size())
                coursesWithFreePlaces.add(course);
        return coursesWithFreePlaces;
    }

    /**
     * @param course is a course
     * @return the list of students attending the specified course
     */
    public List<Student> retrieveStudentsFromCourse(Course course) {
        return (ArrayList<Student>) courseRepository.findOne(course).getStudentList();

    }

    /**
     * @return a list of all courses
     */
    public List<Course> getAllCourses() {
        return (ArrayList<Course>) courseRepository.findAll();
    }

    /**
     * updates the credits of a course
     * updates the number of credits of student enrolled in that course
     * @param course is a course
     * @param credits is the new number of credits
     */
    public void updateCredits(Course course, int credits) {
        int difference = course.getCredits() - credits;
        courseRepository.findOne(course).setCredits(credits);
        for (Student s: course.getStudentList()) {
            s.setTotalCredits(s.getTotalCredits() - difference);
        }
    }

    /**
     * Deletes the course
     * @param course is the course deleted
     * @param teacher is the teacher deleting the course
     */
    public void deleteCourse (Teacher teacher,Course course) {
        teacher.getTaughtCourses().remove(course);
        for (Student s: course.getStudentList())
        {
            ArrayList<Course> newList = (ArrayList<Course>) s.getEnrolledCourses();
            newList.remove(course);
            s.setEnrolledCourses(newList);
        }
        courseRepository.delete(course);

    }
    public void sortCoursesByName() {
        Collections.sort((ArrayList<Course>) courseRepository.findAll(), new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return o1.getCourseName().compareTo(o2.getCourseName());
            }
        });

    }
}
