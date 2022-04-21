package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "coordinator")
public class Coordinator extends User {
    public Coordinator(String userId, String pwd) {
        super(userId, pwd);
    }

    public Coordinator() {

    }
}