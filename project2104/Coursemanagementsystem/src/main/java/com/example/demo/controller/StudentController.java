package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.example.demo.domain.Schedule;
import com.example.demo.domain.Student;
import com.example.demo.service.StudentService;

import java.util.Scanner;


@Controller
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    void init(Student user) {
        showCourseMenu(user);
    }

    public void showCourseMenu(Student user) {
        Scanner console = CourseController.scanner;
        String studentMenu[] = {"1. enrolCourse ",
                "2. viewCourseOffering ",
                "3. Log out"};

        int option;
        do {
            for (String aStudentMenu : studentMenu) {
                System.out.println(aStudentMenu);
            }
            option = console.nextInt();
            switch (option) {
                case 1:
                    if (Schedule.currentSchedule().getWeek() > 2) {
                        System.out.println("Enrolment have already closed");
                        break;
                    }
                    while (true) {
                        try {
                            String inputCourseCode;
                            System.out.println("Please enter the Course code you want enrol or input \"back\" to go back");
                            inputCourseCode = console.next();
                            if (inputCourseCode.equals("back"))
                                break;
                            studentService.enrolCourse(user, inputCourseCode);
                            break;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case 2:
                    studentService.viewCourseOffering(user);
                    break;
            }
        } while (option != 3);

    }
}