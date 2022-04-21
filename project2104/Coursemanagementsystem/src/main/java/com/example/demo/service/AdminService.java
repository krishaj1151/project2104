package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.domain.CourseOffering;
import com.example.demo.domain.Enrolment;
import com.example.demo.domain.Schedule;
import com.example.demo.domain.Admin;
import com.example.demo.domain.Lecturer;
import com.example.demo.domain.AdminRepo;

import java.util.List;

@Service
public class AdminService extends StaffService<Admin> {
    private final AdminRepo adminRepo;
    private final CourseOfferingService offeringService;
    private final ScheduleService scheduleService;
    private final EnrolmentService enrolmentService;

    @Autowired
    public AdminService(AdminRepo adminRepo,
                        CourseOfferingService offeringService,
                        ScheduleService scheduleService,
                        StudentService studentService, EnrolmentService enrolmentService) {
        super(studentService, adminRepo, enrolmentService);
        this.adminRepo = adminRepo;
        this.offeringService = offeringService;
        this.scheduleService = scheduleService;
        this.enrolmentService = enrolmentService;
    }


    public CourseOffering addCourseOffering(CourseOffering offering) {
        if (offering == null) {
            throw new NullPointerException("CourseOffering cannot be null");
        }
        if (offering.getSchedule().compareTo(Schedule.currentSchedule()) < 0) {
            throw new IllegalArgumentException("Cannot add the past Course offering.");
        }
        return offeringService.addCourseOffering(offering);
    }

    public CourseOffering assignLecturer(CourseOffering offering, Lecturer lecturer) {
        if (offering == null || lecturer == null) {
            throw new NullPointerException("Offering and lecturer cannot be null");
        }
        if (!offering.getSchedule().equals(Schedule.currentSchedule())) {
            throw new IllegalArgumentException("Cannot assign the lecturer to the offering in non-current semester");
        }
        offering.setLecturer(lecturer);
        return offeringService.save(offering);
    }

    public void advanceSystem() {
        scheduleService.advance();
    }

    public void markRNF() {
        List<Enrolment> enrolments = enrolmentService.findAllByResult("");
        for (Enrolment enrolment : enrolments) {
            enrolment.setResult("RNF");
        }
        enrolmentService.saveAll(enrolments);
    }

}


