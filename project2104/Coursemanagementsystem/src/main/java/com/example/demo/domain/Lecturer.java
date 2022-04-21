package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lecturer")
public class Lecturer extends User {
    public Lecturer(String userId, String pwd) {
        super(userId, pwd);
    }

    public Lecturer() {

    }
}
