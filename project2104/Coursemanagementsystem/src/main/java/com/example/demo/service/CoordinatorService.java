package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.domain.Course;
import com.example.demo.domain.CourseOffering;
import com.example.demo.domain.Enrolment;
import com.example.demo.domain.Schedule;
import com.example.demo.domain.Coordinator;
import com.example.demo.domain.Student;
import com.example.demo.repositories.CoordinatorRepo;
import com.example.demo.repositories.EnrolmentRepo;
import om.example.demo.repositories.StudentRepo;


@Service
public class CoordinatorService extends UserService<Coordinator> {

    private final CourseService courseService;
    private final StudentService studentService;
    private final CoordinatorRepo coordinatorRepo;
    private final EnrolmentService enrolmentService;
    private final StudentRepo studentRepo;
    private final EnrolmentRepo enrolmentRepo;
    private final CourseOfferingService courseOfferingService;


    @Autowired
    public CoordinatorService(CourseService courseService, StudentService studentService, CoordinatorRepo coordinatorRepo, EnrolmentService enrolmentService, StudentRepo studentRepo, EnrolmentRepo enrolmentRepo, CourseOfferingService courseOfferingService) {
        super(coordinatorRepo);
        this.courseService = courseService;
        this.studentService = studentService;
        this.coordinatorRepo = coordinatorRepo;
        this.enrolmentService = enrolmentService;
        this.studentRepo = studentRepo;
        this.enrolmentRepo = enrolmentRepo;
        this.courseOfferingService = courseOfferingService;
    }

    public Course addCourse(Course course) {
        return courseService.addCourse(course);
    }

    public void grantPermission(Student student, int maxLoad) {
        Student stu = studentService.findOne(student);
        if (stu == null) {
            throw new IllegalArgumentException("There is no such student.");
        }
        stu.setMaxLoad(maxLoad);
        studentService.save(stu);
    }

    public void grantExemption(Student student, Course course) {
        CourseOffering offering = new CourseOffering();
        offering.setCourse(course);
        offering.setSemester(Schedule.currentSchedule().getSemester());
        offering.setYear(Schedule.currentSchedule().getYear());
        courseOfferingService.save(offering);
        Enrolment enrolment = new Enrolment();
        enrolment.setOffering(offering);
        enrolment.setStudent(student);
        enrolmentService.save(enrolment);
        studentService.findOne(student).addEnrolments(enrolment);
        studentService.save(student);
    }
}


