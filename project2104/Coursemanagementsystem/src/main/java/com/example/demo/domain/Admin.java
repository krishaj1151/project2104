package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin extends User {

    public Admin(String userId, String pwd) {
        super(userId, pwd);
    }

    public Admin() {

    }
}
