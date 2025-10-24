package com.example.recruitmentsystem.controller;

import com.example.recruitmentsystem.dto.JobRequest;
import com.example.recruitmentsystem.model.Job;
import com.example.recruitmentsystem.model.User;
import com.example.recruitmentsystem.service.JobService;
import com.example.recruitmentsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final JobService jobService;
    private final UserService userService;

    public AdminController(JobService jobService, UserService userService) {
        this.jobService = jobService;
        this.userService = userService;
    }

    @PostMapping("/job")
    public ResponseEntity<Job> createJob(@RequestBody JobRequest request, Authentication authentication) {
        Job job = jobService.createJob(request, authentication.getName());
        return ResponseEntity.ok(job);
    }

    @GetMapping("/job/{job_id}")
    public ResponseEntity<Map<String, Object>> getJob(@PathVariable Long job_id) {
        Job job = jobService.getJobById(job_id);
        Map<String, Object> response = Map.of(
                "job", job,
                "applicants", job.getApplicants()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/applicants")
    public ResponseEntity<List<User>> getApplicants() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/applicant/{applicant_id}")
    public ResponseEntity<Map<String, Object>> getApplicant(@PathVariable Long applicant_id) {
        User user = userService.findById(applicant_id);
        Map<String, Object> response = Map.of(
                "user", user,
                "profile", user.getProfile()
        );
        return ResponseEntity.ok(response);
    }
}
