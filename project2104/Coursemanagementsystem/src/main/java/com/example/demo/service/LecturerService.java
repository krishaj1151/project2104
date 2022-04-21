package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.domain.CourseOffering;
import com.example.demo.domain.Enrolment;
import com.example.demo.domain.Schedule;
import com.example.demo.domain.Lecturer;
import com.example.demo.domain.Student;
import com.example.demo.repositories.LecturerRepo;

import java.util.List;


@Service
public class LecturerService extends StaffService<Lecturer> {

    private final StudentService studentService;
    private final ScheduleService scheduleService;
    private final LecturerRepo lecturerRepo;
    private final EnrolmentService enrolmentService;

    @Autowired
    public LecturerService(StudentService studentService,
                           ScheduleService scheduleService,
                           LecturerRepo lecturerRepo,
                           EnrolmentService enrolmentService, EnrolmentService enrolmentService1) {
        super(studentService, lecturerRepo, enrolmentService);
        this.studentService = studentService;
        this.scheduleService = scheduleService;
        this.lecturerRepo = lecturerRepo;
        this.enrolmentService = enrolmentService1;
    }

    public boolean uploadResult(Lecturer lecturer, Student student, String result) throws NullPointerException {
        Schedule currentSchedule = scheduleService.findCurrentSchedule();
        if (currentSchedule.getWeek() != 12) {
            throw new IllegalArgumentException("Cannot upload result until the end of the semester.");
        }
        Student stu = studentService.findOne(student);
        List<Enrolment> enrolments = stu.getPerformance();
        Enrolment enrolment = null;
        for (Enrolment e : enrolments) {
            CourseOffering offering = e.getOffering();
            if (offering.getLecturer().equals(lecturer) && offering.getSchedule().equals(currentSchedule)) {
                enrolment = e;
                break;
            }
        }
        if (enrolment == null) {
            throw new NullPointerException("There is no such student in the lecturer's course");
        }
        enrolment.setResult(result);
        studentService.save(student);
        return true;
    }

    public void uploadResult(Enrolment enrolment, String result) {
        enrolment.setResult(result);
        enrolmentService.save(enrolment);
    }

}