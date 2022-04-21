package com.example.demo.domain;
import com.example.demo.domain.Student;

import javax.persistence.*;

@Entity
@Table(name = "enrolment")
public class Enrolment {
    @Id
    @Column(name = "enrolId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int enrolId;
    @Column(name = "result")
    private String result = "";
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "userId")
    private Student student;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "OfferingId")
    private CourseOffering offering;

    public Enrolment() {
        student = new Student();
        offering = new CourseOffering();
    }

    public Enrolment(Student student, CourseOffering offering) {
        this.student = student;
        this.offering = offering;
    }

    public int getEnrolId() {
        return enrolId;
    }

    public void setEnrolId(int enrolId) {
        this.enrolId = enrolId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public CourseOffering getOffering() {
        return offering;
    }

    public void setOffering(CourseOffering offering) {
        this.offering = offering;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return String.format("CourseOffering: %s Student: %s ", offering, student.getName());
    }
}
