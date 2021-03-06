package com.example.demo.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {
    private static final Logger logger = LoggerFactory.getLogger(Course.class);
    @Id
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "mainTopic")
    private String mainTopic;
    @Column(name = "isElective")
    private boolean isElective;
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Course> prerequisites = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainTopic() {
        return mainTopic;
    }

    public void setMainTopic(String mainTopic) {
        this.mainTopic = mainTopic;
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public void addPrerequisite(Course... courses) {
        for (Course course : courses) {
            if (course.equals(this)) {
                logger.error("Cannot set itself as prerequisite");
                continue;
            }
            if (course.prerequisites.contains(this)) {
                logger.error("Cannot set course as prerequisite for each other");
                continue;
            }
            prerequisites.add(course);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Course other = (Course) obj;
        return this.code.equals(other.code);
    }

    public boolean isElective() {
        return isElective;
    }

    public void setElective(boolean elective) {
        isElective = elective;
    }

    @Override
    public String toString() {
        return String.format("Code: %s Name: %s Main Topic: %s.",
                code, name, mainTopic);
    }
}