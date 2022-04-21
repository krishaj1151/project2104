package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.domain.Course;
import com.example.demo.domain.CourseOffering;
import com.example.demo.domain.Schedule;
import com.example.demo.repositories.CourseOfferingRepo;

import java.util.List;


@Service
public class CourseOfferingService {
    private final CourseOfferingRepo offeringRepo;
    private final ScheduleService scheduleService;


    @Autowired
    public CourseOfferingService(CourseOfferingRepo offeringRepo, ScheduleService scheduleService) {
        this.offeringRepo = offeringRepo;
        this.scheduleService = scheduleService;
    }

    public CourseOffering findOne(CourseOffering offering) {
        return offeringRepo.getOne(offering.getOfferingId());
    }

    public List<CourseOffering> findByCourse(Course course) {
        return offeringRepo.findAllByCourse(course);
    }

    public List<CourseOffering> findOfferingsInCurrentSemester() {
        Schedule current = scheduleService.findCurrentSchedule();
        return offeringRepo.findCourseOfferingsByYearAndSemester(current.getYear(), current.getSemester());
    }

    public CourseOffering addCourseOffering(CourseOffering offering) {
        if (offering == null) {
            throw new NullPointerException("CourseOffering is null");
        }
        List<CourseOffering> offeringschk =
                offeringRepo.findCourseOfferingsByCourseAndYearAndSemester(offering.getCourse(), offering.getYear(), offering.getSemester());
        if (offeringRepo.existsById(offering.getOfferingId()) || (offeringschk != null && !offeringschk.isEmpty())) {
            throw new IllegalArgumentException("This offering has existed.");
        }
        return offeringRepo.save(offering);
    }

    public CourseOffering save(CourseOffering offering) {
        List<CourseOffering> offerings = offeringRepo.findCourseOfferingsByCourseAndYearAndSemester(offering.getCourse(), offering.getYear(), offering.getSemester());
        if (offerings != null && !offerings.isEmpty()) {
            offering.setOfferingId(offerings.get(0).getOfferingId());
        }
        return offeringRepo.saveAndFlush(offering);
    }

    public void delete(CourseOffering offering) {
        offeringRepo.delete(offering);
    }

    public boolean deleteIfExist(CourseOffering offering) {
        if (offeringRepo.existsById(offering.getOfferingId())) {
            offeringRepo.delete(offering);
            return true;
        }
        return false;
    }

    public List<CourseOffering> findAll() {
        return offeringRepo.findAll();
    }
}


