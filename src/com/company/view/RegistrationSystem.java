package com.company.view;

import com.company.model.Course;
import com.company.model.Student;
import com.company.model.Teacher;
import com.company.repository.CourseRepository;
import com.company.repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.List;

public class RegistrationSystem extends InMemoryRepository<Course> {
    private CourseRepository courses;

    public RegistrationSystem(CourseRepository courses)
    {
        this.courses = courses;
    }

    public boolean deleteCourse(Teacher teacher, Course course) {
        if (courses.findOne(course) != null) {
            if (teacher.getTaughtCourses().contains(course)) {
                //delete the course from the teacher's list
                List<Course> newTaughtCourses = teacher.getTaughtCourses();
                newTaughtCourses.remove(course);
                teacher.setTaughtCourses(newTaughtCourses);
                //delete course from the student's enrolled courses
                for(Student student : course.getStudentList())
                {
                    student.getEnrolledCourses().remove(course);
                    student.setTotalCredits(student.getTotalCredits()-course.getCredits());
                }
                //delete course from the list of courses
                courses.delete(course);

            }
            return false;
        } else
            return false;
    }

    public boolean register(Student student, Course course) {
        if (courses.findOne(course) != null) {//verify if the course exists
            if (course.getNumberStudentsMax() > course.getStudentList().size()) {//verify if there are free places
                if (course.getCredits() + student.getTotalCredits() <= 30) {//verify if the number of credits doesn t exceed the limit
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
            System.out.println("There is no such course at this university. Choose from : " + courses);
            return false;
        }

    }

    public List<Course> retrieveCoursesWithFreePlaces() {
        List<Course> coursesWithFreePlaces = new ArrayList<>();
        for (Course course : courses.findAll())
            if (course.getNumberStudentsMax() < course.getStudentList().size())
                coursesWithFreePlaces.add(course);
        return coursesWithFreePlaces;
    }

    public List<Student> retrieveStudentsFromCourse(Course course) {
        if (courses.findOne(course) != null) {
            return course.getStudentList();
        }

        System.out.println("There are no studentsa");
        return new ArrayList<>();

    }

    public List<Course> getAllCourses() {
        List<Course> allCourses = new ArrayList<>();
        for (Course course : courses.findAll())
            allCourses.add(course);
        return allCourses;
    }
}
