package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.domain.Course;
import com.example.demo.domain.CourseOffering;

import java.util.List;

public interface CourseOfferingRepo extends JpaRepository<CourseOffering, Integer> {
    List<CourseOffering> findCourseOfferingsByYearAndSemester(int year, int semester);

    List<CourseOffering> findCourseOfferingsByCourseAndYearAndSemester(Course course, int year, int semester);

    List<CourseOffering> findAllByCourse(Course course);
}
