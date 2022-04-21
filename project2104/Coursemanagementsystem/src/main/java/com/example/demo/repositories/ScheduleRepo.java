package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.domain.Schedule;

interface ScheduleRepo extends JpaRepository<Schedule, Integer> {
}
