package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.domain.Enrolment;
import com.example.demo.domain.Schedule;
import com.example.demo.domain.Student;
import com.example.demo.domain.User;
import com.example.demo.repositories.UserRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffService<T extends User> extends UserService<T> {
    private final StudentService studentService;
    private final EnrolmentService enrolmentService;

    @Autowired
    public StaffService(StudentService studentService,
                        UserRepo<T> userRepo,
                        EnrolmentService enrolmentService) {
        super(userRepo);
        this.studentService = studentService;
        this.enrolmentService = enrolmentService;
    }

    private List<Enrolment> viewPastPerformance(Student stu) {
        List<Enrolment> enrolments = enrolmentService.findAllByStudent(stu);
        List<Enrolment> past = new ArrayList<>();
        for (Enrolment enrolment : enrolments) {
            if (enrolment.getOffering().getSchedule().compareTo(Schedule.currentSchedule()) < 0) {
                past.add(enrolment);
            }
        }
        return past;
    }

    public List<Enrolment> viewPastPerformance(String userId) {
        Student stu = studentService.findOne(userId);
        if (stu == null) {
            throw new IllegalArgumentException("There is no such student.");
        }
        return viewPastPerformance(stu);
    }
}

