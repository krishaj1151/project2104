package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.domain.CourseOffering;
import com.example.demo.domain.Enrolment;
import com.example.demo.domain.Student;

import java.util.List;
interface EnrolmentRepo extends JpaRepository<Enrolment, Integer> {

    List<Enrolment> findAllByResult(String result);

    Enrolment findEnrolmentByStudentAndOffering(Student student, CourseOffering offering);

    List<Enrolment> findAllByOffering(CourseOffering offering);

    List<Enrolment> findAllByStudent(Student student);

    List<Enrolment> findAllByOfferingAndStudent(CourseOffering offering, Student student);
}