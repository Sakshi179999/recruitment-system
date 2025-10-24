package com.example.recruitmentsystem.repository;

import com.example.recruitmentsystem.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
