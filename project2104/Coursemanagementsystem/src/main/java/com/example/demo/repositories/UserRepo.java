package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.domain.User;


//@Deprecated
public interface UserRepo<T extends User> extends JpaRepository<T, String> {

}