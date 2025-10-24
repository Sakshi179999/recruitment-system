package com.example.recruitmentsystem.service;

import com.example.recruitmentsystem.dto.JobRequest;
import com.example.recruitmentsystem.model.Job;
import com.example.recruitmentsystem.model.User;
import com.example.recruitmentsystem.repository.JobRepository;
import com.example.recruitmentsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobService(JobRepository jobRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    public Job createJob(JobRequest request, String email) {
        User postedBy = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = new Job(request.getTitle(), request.getDescription(), request.getCompanyName(), postedBy);
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(Long jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public void applyForJob(Long jobId, String email) {
        Job job = getJobById(jobId);
        User applicant = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"Applicant".equals(applicant.getUserType())) {
            throw new RuntimeException("Only applicants can apply for jobs");
        }

        if (job.getApplicants().contains(applicant)) {
            throw new RuntimeException("Already applied for this job");
        }

        job.getApplicants().add(applicant);
        job.setTotalApplications(job.getTotalApplications() + 1);
        jobRepository.save(job);
    }
}
